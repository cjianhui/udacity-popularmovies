package com.cjianhui.android.popularmovies.models;

/**
 * Represents image sizes used to obtain images from themoviedb.org.
 */

public enum ImageSize {
    /* Image sizes
        w92, w154, w185, w342, w500, w780, original
    */
    THUMBNAIL("w185"),
    POSTER("w342"),
    BACKDROP("w780");

    private final String size;

    ImageSize(String size) {
        this.size = size;
    }

    public String value() {
        return size;
    }
}
