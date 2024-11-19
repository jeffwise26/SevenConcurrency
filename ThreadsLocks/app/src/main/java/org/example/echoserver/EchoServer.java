
package org.example.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

class EchoServer {
    public static void main(String[] args) {

        class ClientHandler implements Runnable {
            private final Socket clientSocket;

            public ClientHandler(Socket socket) {
                this.clientSocket = socket;
            }

            @Override
            public void run() {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    StringBuilder request = new StringBuilder();
                    request.append(in.readLine()).append("\r\n");

                    HashMap<String, String> headers = new HashMap();
                    String headerLine;
                    while(!(headerLine = in.readLine()).isEmpty()) {
                        request.append(headerLine).append("\r\n");
                        String[] keyValue = headerLine.split(":");
                        headers.put(keyValue[0], keyValue[1].trim());
                    }

                    int contentLength = Integer.parseInt(headers.get("Content-Length"));
                    char[] buffer = new char[contentLength];
                    in.read(buffer);
                    request.append(buffer);

                    String httpResponse = "HTTP/1.1 200 OK\r\n" +
                            "Content-Type: text/plain\r\n" +
                            "Content-Length: " + request.length() + "\r\n" +
                            "\r\n" +
                            request;

                    out.print(httpResponse);
                    out.flush();

                } catch (IOException e) {
                    System.out.println("Error handling client: " + e.getMessage());
                } finally {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        System.out.println("Error closing client socket: " + e.getMessage());
                    }
                }
            }
        }
        int port = 8080;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Echo HTTP Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException e) {
            System.out.println("Error while starting server: " + e.getMessage());
        }
    }
}