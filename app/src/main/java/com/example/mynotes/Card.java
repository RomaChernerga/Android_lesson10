package com.example.mynotes;

import androidx.annotation.IdRes;

public class Card {

    private final String title;
    @IdRes
    private final int image;
    private final boolean isLike;

    public Card(String title, int image, boolean isLike) {
        this.title = title;
        this.image = image;
        this.isLike = isLike;
    }

    public Card(String title, int image) {
        this(title, image, false);
    }

    public String getTitle() {
        return title;
    }


    public int getImage() {
        return image;
    }

    public boolean isLike() {
        return isLike;
    }
}
