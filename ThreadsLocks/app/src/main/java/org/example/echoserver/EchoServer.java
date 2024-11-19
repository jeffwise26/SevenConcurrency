package org.example.echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        class ConnectionHandler implements Runnable {
            Socket socket;

            ConnectionHandler(Socket socket) throws IOException {
                this.socket = socket;
            }

            @Override
            public void run() {
                try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
                    int n = 0;
                    byte[] buffer = new byte[1024];

                    while((n = in.read(buffer)) != -1) {
                        out.write(buffer, 0, n);
                        out.flush();
                    }
                    String s = "";

                } catch (IOException e) {
                    System.out.println(e);
                } finally {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }

        ServerSocket server = new ServerSocket(8080);
        int threadPollSize = Runtime.getRuntime().availableProcessors() * 2;
        ExecutorService executor = Executors.newFixedThreadPool(threadPollSize);
        while (true) {
            Socket socket = server.accept();
            executor.execute(new ConnectionHandler(socket));
        }
    }
}
