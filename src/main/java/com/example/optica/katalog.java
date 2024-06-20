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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
public class katalog extends Application {

    private FlowPane glassesPane = new FlowPane();
    private List<GlassesItem> allGlassesItems = new ArrayList<>();
    private TextField searchField;
    @Override
    public void start(Stage primaryStage) {
        glassesPane.setPadding(new Insets(50)); // Установка отступов
        glassesPane.setHgap(50);
        glassesPane.setVgap(50);

        addGlassesItems(true); // Добавляем товары с ценами

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(glassesPane);
        scrollPane.setFitToWidth(true);

        Button goToCartButton = new Button("Go to cart page");
        goToCartButton.setMinSize(200, 100); // Установка размеров кнопки
        goToCartButton.setOnAction(event -> openCartPage());

        searchField = new TextField();
        searchField.setPromptText("Search by name...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterGlassesItems(newValue));

        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("All", "Sunglasses", "Reading Glasses", "Sports Glasses");
        categoryComboBox.setValue("All");
        categoryComboBox.setOnAction(event -> filterGlassesItemsByCategory(categoryComboBox.getValue()));

        Button backButton = new Button("Back");
        backButton.setMinSize(200, 100); // Установка размеров кнопки
        backButton.setOnAction(event -> primaryStage.close());

        HBox buttonsLayout = new HBox(10, goToCartButton, backButton); // Контейнер для кнопок с пространством 10
        VBox mainLayout = new VBox(categoryComboBox, searchField, scrollPane, buttonsLayout); // Основной макет
        Scene scene = new Scene(mainLayout, 900, 800);

        mainLayout.setStyle(
                "-fx-background-color: #f0f0f0;" + // Цвет фона
                        "-fx-padding: 20px;" + // Отступы
                        "-fx-spacing: 20px;" // Пространство между элементами
        );
        goToCartButton.setStyle(
                "-fx-background-color: #4CAF50;" + // Цвет фона
                        "-fx-text-fill: white;" + // Цвет текста
                        "-fx-font-size: 20px;" // Размер шрифта
        );
        backButton.setStyle(
                "-fx-background-color: #4CAF50;" + // Цвет фона
                        "-fx-text-fill: white;" + // Цвет текста
                        "-fx-font-size: 20px;" // Размер шрифта
        );

        primaryStage.setScene(scene);
        primaryStage.setTitle("Glasses Catalog");
        primaryStage.show();
    }
    private GlassesItem createGlassesItem(String name, String description, String imageUrl, String category) {
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

        Label heartIcon = new Label("\uD83D\uDC95"); // Unicode для символа сердечка
        heartIcon.setStyle("-fx-font-size: 27px; -fx-text-fill: black;");

        IntegerProperty likeCount = new SimpleIntegerProperty(0);
        Label likeCountLabel = new Label();
        likeCountLabel.textProperty().bind(likeCount.asString());

        HBox likeBox = new HBox(5, heartIcon, likeCountLabel);
        descriptionLabel.setGraphic(likeBox);
        descriptionLabel.setContentDisplay(ContentDisplay.RIGHT);

        heartIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (likeCount.get() > 0) {
                    likeCount.set(likeCount.get() - 1);
                    heartIcon.setStyle("-fx-font-size: 27px; -fx-text-fill: black;");
                } else {
                    likeCount.set(likeCount.get() + 1);
                    heartIcon.setStyle("-fx-font-size: 27px; -fx-text-fill: lightblue;");
                }
            }
        });

        item.getChildren().add(descriptionLabel);

        Button reviewButton = new Button("Leave a Review");
        reviewButton.setOnAction(event -> showReviewDialog(name, item));
        item.getChildren().add(reviewButton);

        Button viewReviewsButton = new Button("View Reviews");
        viewReviewsButton.setOnAction(event -> showReviewsDialog(name, item));
        item.getChildren().add(viewReviewsButton);

        // Add average rating label
        Label averageRatingLabel = new Label("Average Rating: N/A");
        item.getChildren().add(averageRatingLabel);

        return new GlassesItem(item, category, likeCount, heartIcon, averageRatingLabel);
    }
    private GlassesItem createGlassesItemWithPrice(String name, String description, String imageUrl, String price, String category) {
        GlassesItem glassesItem = createGlassesItem(name, description, imageUrl, category); // Создаем базовый элемент
        VBox item = glassesItem.getItem();

        Label priceLabel = new Label(price);
        priceLabel.setStyle(
                "-fx-background-color: #4CAF50;" + // Цвет фона
                        "-fx-text-fill: white;" + // Цвет текста
                        "-fx-padding: 5px;" // Отступы
        );
        item.getChildren().add(priceLabel);

        return glassesItem;
    }
    private void addGlassesItems(boolean includePrices) {
        if (includePrices) {
            allGlassesItems.add(createGlassesItemWithButton("Reading glasses Model 1", "Sophisticated, oval lenses.", "https://i.pinimg.com/736x/39/e3/53/39e353f0ad4f7c9f35e8184a99376dbb.jpg", "$50", "Reading Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gentle Monster sunglasses", "Vintage charm, round frame.", "https://i.pinimg.com/564x/ad/36/b5/ad36b5ac59d24ac1f6ef59c38fe1db05.jpg", "$60", "Sunglasses"));
            allGlassesItems.add(createGlassesItemWithButton("Sport glasses Blue", "New choice for sport", "https://i.pinimg.com/564x/fe/d8/5c/fed85c4ed734d17d9c08a7ab5042aa45.jpg", "$70", "Sports Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gucci", "Trendy, cool", "https://i.pinimg.com/564x/5b/a0/bb/5ba0bbb594582f551410449f84070689.jpg", "$80", "Reading Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gentle monster Sport", "New collection", "https://i.pinimg.com/564x/95/bf/e0/95bfe0ac1b43542205de43fe383bee5a.jpg", "$55", "Reading Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Sport glasses Silver", "Minimalist, elegant, lightweight.", "https://i.pinimg.com/564x/7b/71/6e/7b716ea7da21b56b6546e1b1bb9b2423.jpg", "$70", "Sports Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Chanel sunglasses", "Elegant, trendy", "https://i.pinimg.com/564x/7b/da/1a/7bda1a65ac962c83c1c44a1578e16917.jpg", "$50", "Sunglasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gentle monster reading", "New collection of gentle monster", "https://i.pinimg.com/564x/05/3c/b4/053cb428153a24f9defaf5a29b4f59fe.jpg", "$80", "Reading Glasses"));

        } else {
            allGlassesItems.add(createGlassesItemWithButton("Reading glasses Model 1", "Sophisticated, oval lenses.", "https://i.pinimg.com/736x/39/e3/53/39e353f0ad4f7c9f35e8184a99376dbb.jpg", "", "Reading Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gentle Monster sunglasses", "Vintage charm, round frame.", "https://i.pinimg.com/564x/ad/36/b5/ad36b5ac59d24ac1f6ef59c38fe1db05.jpg", "", "Sunglasses"));
            allGlassesItems.add(createGlassesItemWithButton("Sport glasses Blue", "New choice for sport", "https://i.pinimg.com/564x/fe/d8/5c/fed85c4ed734d17d9c08a7ab5042aa45.jpg", "", "Sports Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gucci", "Trendy, cool", "https://i.pinimg.com/564x/5b/a0/bb/5ba0bbb594582f551410449f84070689.jpg", "", "Reading Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gentle monster Sport", "New collection", "https://i.pinimg.com/564x/95/bf/e0/95bfe0ac1b43542205de43fe383bee5a.jpg", "", "Reading Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Sport glasses Silver", "Minimalist, elegant, lightweight.", "https://i.pinimg.com/564x/7b/71/6e/7b716ea7da21b56b6546e1b1bb9b2423.jpg", "", "Sports Glasses"));
            allGlassesItems.add(createGlassesItemWithButton("Chanel sunglasses", "Elegant, trendy", "https://i.pinimg.com/564x/7b/da/1a/7bda1a65ac962c83c1c44a1578e16917.jpg", "", "Sunglasses"));
            allGlassesItems.add(createGlassesItemWithButton("Gentle monster reading", "New collection of gentle monster", "https://i.pinimg.com/564x/05/3c/b4/053cb428153a24f9defaf5a29b4f59fe.jpg", "", "Reading Glasses"));

        }

        for (GlassesItem item : allGlassesItems) {
            glassesPane.getChildren().add(item.getItem());
        }
    }
    private void filterGlassesItems(String filter) {
        glassesPane.getChildren().clear();
        for (GlassesItem item : allGlassesItems) {
            if (item.getItem().getChildren().toString().toLowerCase().contains(filter.toLowerCase())) {
                glassesPane.getChildren().add(item.getItem());
            }
        }
    }
    private void filterGlassesItemsByCategory(String category) {
        glassesPane.getChildren().clear();
        for (GlassesItem item : allGlassesItems) {
            if (category.equals("All") || item.getCategory().equals(category)) {
                glassesPane.getChildren().add(item.getItem());
            }
        }
    }
    private void openCartPage() {
        CartPage cartPage = new CartPage();
        cartPage.show();
    }
    private GlassesItem createGlassesItemWithButton(String name, String description, String imageUrl, String price, String category) {
        GlassesItem glassesItem = createGlassesItemWithPrice(name, description, imageUrl, price, category);
        VBox item = glassesItem.getItem();

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(event -> CartPage.addToCart(name, description, imageUrl, price));
        item.getChildren().add(addToCartButton);

        return glassesItem;
    }
    private void showReviewDialog(String productName, VBox item) {
        Stage dialog = new Stage();
        dialog.setTitle("Leave a Review for " + productName);

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        Label reviewLabel = new Label("Review:");
        TextArea reviewTextArea = new TextArea();
        reviewTextArea.setWrapText(true);

        Label ratingLabel = new Label("Rating:");
        ComboBox<Integer> ratingComboBox = new ComboBox<>();
        ratingComboBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingComboBox.setValue(5);

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            String review = reviewTextArea.getText();
            int rating = ratingComboBox.getValue();
            Review newReview = new Review(review, rating);
            ((GlassesItem) item.getUserData()).addReview(newReview);
            updateAverageRatingLabel((GlassesItem) item.getUserData());
            dialog.close();
        });

        dialogVBox.getChildren().addAll(reviewLabel, reviewTextArea, ratingLabel, ratingComboBox, submitButton);

        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    private void showReviewsDialog(String productName, VBox item) {
        Stage dialog = new Stage();
        dialog.setTitle("Reviews for " + productName);

        VBox dialogVBox = new VBox(10);
        dialogVBox.setPadding(new Insets(10));

        Label reviewsLabel = new Label("Reviews:");
        ListView<Review> reviewsListView = new ListView<>();
        reviewsListView.getItems().addAll(((GlassesItem) item.getUserData()).getReviews());
        reviewsListView.setPrefHeight(200);

        dialogVBox.getChildren().addAll(reviewsLabel, reviewsListView);

        Scene dialogScene = new Scene(dialogVBox, 300, 300);
        dialog.setScene(dialogScene);
        dialog.show();
    }
    private void updateAverageRatingLabel(GlassesItem glassesItem) {
        List<Review> reviews = glassesItem.getReviews();
        if (reviews.isEmpty()) {
            glassesItem.getAverageRatingLabel().setText("Average Rating: N/A");
        } else {
            double averageRating = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
            glassesItem.getAverageRatingLabel().setText(String.format("Average Rating: %.1f", averageRating));
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}

class GlassesItem {
    private VBox item;
    private String category;
    private IntegerProperty likeCount;
    private Label heartIcon;
    private List<Review> reviews;
    private Label averageRatingLabel;
    public GlassesItem(VBox item, String category, IntegerProperty likeCount, Label heartIcon, Label averageRatingLabel) {
        this.item = item;
        this.category = category;
        this.likeCount = likeCount;
        this.heartIcon = heartIcon;
        this.reviews = new ArrayList<>();
        this.averageRatingLabel = averageRatingLabel;
        this.item.setUserData(this);
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
    public Label getHeartIcon() {
        return heartIcon;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public void addReview(Review review) {
        this.reviews.add(review);
    }
    public Label getAverageRatingLabel() {
        return averageRatingLabel;
    }
}










