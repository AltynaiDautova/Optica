package com.example.optica;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
public class Accessories extends Application {
    private List<AccessoryItem> allAccessoriesItems = new ArrayList<>();
    private TilePane accessoriesPane = new TilePane();
    @Override
    public void start(Stage primaryStage) {
        accessoriesPane.setPadding(new Insets(50));
        accessoriesPane.setHgap(50);
        accessoriesPane.setVgap(50);

        TextField searchField = new TextField();
        searchField.setPromptText("Search Accessories");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterAccessoriesItems(newValue));

        addAccessoriesItems();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(accessoriesPane);
        scrollPane.setFitToWidth(true);

        Button goToCartButton = new Button("Go to cart page");
        goToCartButton.setMinSize(200, 100);
        goToCartButton.setOnAction(event -> {
            CartPage cartPage = new CartPage();
            cartPage.show();
        });

        Button backButton = new Button("Back");
        backButton.setMinSize(200, 100);
        backButton.setOnAction(event -> primaryStage.close());

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All", "Lenses", "Cases");
        categoryComboBox.setValue("All");
        categoryComboBox.setOnAction(event -> filterAccessoriesItemsByCategory(categoryComboBox.getValue()));

        HBox buttonsLayout = new HBox(10, goToCartButton, backButton);
        VBox mainLayout = new VBox(categoryComboBox, searchField, scrollPane, buttonsLayout);
        Scene scene = new Scene(mainLayout, 900, 800);

        mainLayout.setStyle(
                "-fx-background-color: #f0f0f0;" +
                        "-fx-padding: 20px;" +
                        "-fx-spacing: 20px"
        );
        goToCartButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px"
        );
        backButton.setStyle(
                "-fx-background-color: #4CAF50;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 20px"
        );

        primaryStage.setScene(scene);
        primaryStage.setTitle("Accessories Catalog");
        primaryStage.show();
    }
    private AccessoryItem createAccessoriesItem(String name, String description, String imageUrl, String price, String category) {
        VBox item = new VBox();
        item.setSpacing(5);

        ImageView imageView = new ImageView(new Image(imageUrl));
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        item.getChildren().add(imageView);

        Label nameLabel = new Label(name);
        nameLabel.setWrapText(true);
        item.getChildren().add(nameLabel);

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);

        Label heartIcon = new Label("\uD83D\uDC95");
        heartIcon.setStyle("-fx-font-size: 27px; -fx-text-fill: lightblue;");

        IntegerProperty likeCount = new SimpleIntegerProperty(0);
        Label likeCountLabel = new Label();
        likeCountLabel.textProperty().bind(likeCount.asString());

        HBox likeBox = new HBox(5, heartIcon, likeCountLabel);
        descriptionLabel.setGraphic(likeBox);
        descriptionLabel.setContentDisplay(ContentDisplay.RIGHT);

        item.getChildren().add(descriptionLabel);

        if (!price.isEmpty()) {
            Label priceLabel = new Label(price);
            priceLabel.setStyle(
                    "-fx-background-color: #4CAF50;" +
                            "-fx-text-fill: white;" +
                            "-fx-padding: 5px"
            );
            item.getChildren().add(priceLabel);
        }

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(event -> {
            CartPage.addToCart(name, description, imageUrl, price);
        });

        item.getChildren().add(addToCartButton);

        heartIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                likeCount.set(likeCount.get() + 1);
            }
        });

        AccessoryItem accessoryItem = new AccessoryItem(item, category, likeCount);

        Button reviewButton = new Button("Leave a Review");
        reviewButton.setOnAction(event -> showReviewDialog(name, item));
        item.getChildren().add(reviewButton);

        Button showReviewsButton = new Button("Show Reviews");
        showReviewsButton.setOnAction(event -> showReviewsDialog(name, item));
        item.getChildren().add(showReviewsButton);

        return accessoryItem;
    }
    private void addAccessoriesItems() {
        allAccessoriesItems.add(createAccessoriesItem("Glasses and lenses case", "Convenience, simplicity, durability", "https://i.pinimg.com/564x/d9/a3/e1/d9a3e1727aaaf88b0ec38c135e72424d.jpg", "$20", "Cases"));
        allAccessoriesItems.add(createAccessoriesItem("Case Blue", "Easy to carry", "https://i.pinimg.com/564x/1f/47/e8/1f47e8899e003e39f12399172e21c2ae.jpg", "$30", "Cases"));
        allAccessoriesItems.add(createAccessoriesItem("Case Beige", "The novelty of the 2024 season", "https://i.pinimg.com/564x/e2/bc/ca/e2bccad0361b6ea844aac94b2862f360.jpg", "$25", "Cases"));
        allAccessoriesItems.add(createAccessoriesItem("Lenses Black", "Best choice for you", "https://i.pinimg.com/564x/0d/78/d2/0d78d2271bb971a823187b4b7e8ab04c.jpg", "$35", "Lenses"));
        allAccessoriesItems.add(createAccessoriesItem("Case Pink Triangle", "From the new collection of the french brand", "https://i.pinimg.com/564x/76/bd/11/76bd11138627cc0df2406ebd2c378f13.jpg", "$30", "Cases"));
        allAccessoriesItems.add(createAccessoriesItem("Lenses Blue", "New color!!", "https://i.pinimg.com/564x/11/5c/34/115c344d19c860262fb0e985b8883ed7.jpg", "$35", "Lenses"));
        allAccessoriesItems.add(createAccessoriesItem("Case Deep Blue", "Convenience, simplicity, durability", "https://i.pinimg.com/564x/f3/f7/da/f3f7da149a869100fdb76d002acef6ae.jpg", "$50", "Cases"));
        allAccessoriesItems.add(createAccessoriesItem("Lenses Brown", "New color!!", "https://i.pinimg.com/564x/9e/71/25/9e7125f7c568a27274a844fc0f7cc542.jpg", "$35", "Lenses"));
        allAccessoriesItems.add(createAccessoriesItem("Case White", "New model!!", "https://i.pinimg.com/564x/ff/99/39/ff9939f13ab14b9070cced4ca14a73b3.jpg", "$30", "Cases"));

        for (AccessoryItem item : allAccessoriesItems) {
            accessoriesPane.getChildren().add(item.getItem());
        }
    }
    private void filterAccessoriesItems(String filter) {
        accessoriesPane.getChildren().clear();
        for (AccessoryItem item : allAccessoriesItems) {
            Label nameLabel = (Label) item.getItem().getChildren().get(1);
            if (nameLabel.getText().toLowerCase().contains(filter.toLowerCase())) {
                accessoriesPane.getChildren().add(item.getItem());
            }
        }
    }
    private void filterAccessoriesItemsByCategory(String category) {
        accessoriesPane.getChildren().clear();
        for (AccessoryItem item : allAccessoriesItems) {
            if (category.equals("All") || item.getCategory().equals(category)) {
                accessoriesPane.getChildren().add(item.getItem());
            }
        }
    }
    private void showReviewDialog(String accessoryName, VBox accessoryItemVBox) {
        Stage reviewStage = new Stage();
        reviewStage.setTitle("Leave a Review for " + accessoryName);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea reviewTextArea = new TextArea();
        reviewTextArea.setPromptText("Write your review here...");

        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingComboBox.setValue(5);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String reviewText = reviewTextArea.getText();
            int rating = ratingComboBox.getValue();
            Review review = new Review(reviewText, rating);
            AccessoryItem accessoryItem = findAccessoryItemByName(accessoryName);
            if (accessoryItem != null) {
                accessoryItem.addReview(review);
            }
            reviewStage.close();
        });

        layout.getChildren().addAll(new Label("Rating:"), ratingComboBox, new Label("Review:"), reviewTextArea, submitButton);

        Scene scene = new Scene(layout, 300, 200);
        reviewStage.setScene(scene);
        reviewStage.show();
    }
    private void showReviewsDialog(String accessoryName, VBox accessoryItemVBox) {
        Stage reviewsStage = new Stage();
        reviewsStage.setTitle("Reviews for " + accessoryName);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        AccessoryItem accessoryItem = findAccessoryItemByName(accessoryName);
        if (accessoryItem != null) {
            for (Review review : accessoryItem.getReviews()) {
                Label reviewLabel = new Label(review.toString());
                layout.getChildren().add(reviewLabel);
            }
        }

        Scene scene = new Scene(layout, 300, 200);
        reviewsStage.setScene(scene);
        reviewsStage.show();
    }
    private AccessoryItem findAccessoryItemByName(String name) {
        for (AccessoryItem item : allAccessoriesItems) {
            Label nameLabel = (Label) item.getItem().getChildren().get(1);
            if (nameLabel.getText().equals(name)) {
                return item;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        launch(args);
    }
}






