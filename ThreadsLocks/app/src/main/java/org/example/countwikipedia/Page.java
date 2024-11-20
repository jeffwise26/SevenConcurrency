package org.example.countwikipedia;

import java.util.Arrays;

public class Page {
    private String text;
    private Boolean poisonPill;
    public Page(String text, Boolean poisonPill) {
       this.text = text;
        this.poisonPill = poisonPill;
    }

    /**
     * This has the obvious downside that we get
     * punctuation, newlines etc, but the point
     * here isn't to parse perfectly, but rather
     * to generate just enough processing to
     * notice which strategy is faster.
     */
    public Iterable<String> getWords() {
        return Arrays.asList(this.text.split(" "));
    }

    public Boolean isPoisonPill() {
        return poisonPill;
    }
}
