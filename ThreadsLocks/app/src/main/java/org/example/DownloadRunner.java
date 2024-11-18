package org.example;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class DownloadRunner {
    public static void main(String[] args) throws IOException, URISyntaxException {
        URI uri = new URI("https://www.w3.org/cms-uploads/Hero-illustrations/groups.svg");
        StdOutProgressListener l1 = new StdOutProgressListener("l1");
        StdOutProgressListener l2 = new StdOutProgressListener("l2");
        Downloader downloader = new Downloader(uri.toURL(), "output.svgs");
        downloader.addListener(l1);
        downloader.addListener(l2);
        downloader.run();
    }
}
