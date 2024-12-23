package org.example.download;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Downloader extends Thread {
    private InputStream in;
    private OutputStream out;
    private ArrayList<ProgressListener> listeners;
    private int contentLength;

    public Downloader(URL url, String outputFileName) throws IOException {
        URLConnection connection = url.openConnection();
        contentLength = connection.getContentLength();
        in = connection.getInputStream();
        out = new FileOutputStream(outputFileName);
        listeners = new ArrayList<>();
    }

    public synchronized void addListener(ProgressListener listener) {
        listeners.add(listener);
    }

    public synchronized void removeListener(ProgressListener listener) {
        listeners.remove(listener);
    }

    private void updateProgress(int current) throws InterruptedException {
        ArrayList<ProgressListener> listenersCopy;
        synchronized(this) {
            listenersCopy = (ArrayList<ProgressListener>)listeners.clone();
        }
        for (ProgressListener listener: listenersCopy) {
            listener.onProgress((double) current, (double) contentLength);
        }
    }

    public void run() {
        int n  = 0;
        int total = 0;

        byte[] buffer = new byte[1024];

        try {
            while((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                total += n;
                updateProgress(total);
            }
            out.flush();
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
