package org.example.countwikipedia;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;

public class Pages {

    /**
     * In lieu of tinkering wiki xml parsing/downloads
     * I've copied a few arbitrary wiki pages into the repo
     */
    public static Iterable<Page> getPages() {
        String directoryPath = "wikiPages";
        ArrayList<Page> pages = new ArrayList<>();
        try {
            File[] files = getResources(directoryPath);
            if (files != null) {
                for (File file : files) {
                    String fileContent = Files.readString(file.toPath());
                    pages.add(new Page(fileContent, false));
                }
            } else {
                System.out.println("No files found in the specified directory.");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return pages;
    }

    public static File[] getResources(String dir) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resource = classLoader.getResource(dir);
        if (resource == null) {
            return null;
        }
        return new File(resource.getFile()).listFiles();
    }
}
