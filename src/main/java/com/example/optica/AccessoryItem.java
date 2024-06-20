package com.example.optica;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class AccessoryItem {
    private VBox item;
    private String category;
    private IntegerProperty likeCount;
    private BooleanProperty liked = new SimpleBooleanProperty(false);
    private List<Review> reviews = new ArrayList<>();
    private Label averageRatingLabel = new Label("Average Rating: N/A");

    public AccessoryItem(VBox item, String category, IntegerProperty likeCount) {
        this.item = item;
        this.category = category;
        this.likeCount = likeCount;
        initLikeIcon();
        updateLikeIcon();
        item.getChildren().add(averageRatingLabel); // Добавляем метку среднего рейтинга
    }

    public VBox getItem() {
        return item;
    }

    public String getCategory() {
        return category;
    }

    public IntegerProperty getLikeCount() {
        return likeCount;
    }

    public void addReview(Review review) {
        reviews.add(review);
        updateAverageRatingLabel();
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Label getAverageRatingLabel() {
        return averageRatingLabel;
    }

    private void initLikeIcon() {
        Label descriptionLabel = (Label) item.getChildren().get(2);
        Label heartIcon = (Label) ((HBox) descriptionLabel.getGraphic()).getChildren().get(0);
        heartIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (liked.get()) {
                    liked.set(false);
                    likeCount.set(likeCount.get() - 1);
                } else {
                    liked.set(true);
                    likeCount.set(likeCount.get() + 1);
                }
                updateLikeIcon();
            }
        });
    }

    private void updateLikeIcon() {
        Label descriptionLabel = (Label) item.getChildren().get(2);
        Label heartIcon = (Label) ((HBox) descriptionLabel.getGraphic()).getChildren().get(0);
        if (liked.get()) {
            heartIcon.setStyle("-fx-font-size: 27px; -fx-text-fill: lightblue;");
        } else {
            heartIcon.setStyle("-fx-font-size: 27px; -fx-text-fill: black;");
        }
    }

    private void updateAverageRatingLabel() {
        if (reviews.isEmpty()) {
            averageRatingLabel.setText("Average Rating: N/A");
        } else {
            double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
            averageRatingLabel.setText(String.format("Average Rating: %.1f", averageRating));
        }
    }
}
