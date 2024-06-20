package com.example.optica;

public class Review {
    private String reviewText;
    private int rating;

    public Review(String reviewText, int rating) {
        this.reviewText = reviewText;
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + "/5\nReview: " + reviewText;
    }
}

