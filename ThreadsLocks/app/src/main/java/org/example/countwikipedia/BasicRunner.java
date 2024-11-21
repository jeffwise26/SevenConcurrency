package org.example.countwikipedia;

import java.util.HashMap;

public class BasicRunner {
    private static final HashMap<String, Integer> count = new HashMap<String, Integer>();

    public static void main(String[] args) {
        Iterable<Page> pages = Pages.getPages();
        for (Page page: pages) {
            Iterable<String> words = page.getWords();
            for (String word: words) {
                countWord(word);
            }
        }
        System.out.println(count);
    }

    public static void countWord(String word) {
        count.merge(word, 1, Integer::sum);
    }
}
