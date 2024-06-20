package com.example.optica;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import java.util.ArrayList;
import java.util.List;
public class CartPage extends Stage {
    private static List<CartItem> cartItems = new ArrayList<>();
    public CartPage() {
        // Создание контейнера для отображения корзины
        VBox cartLayout = new VBox();
        cartLayout.setPadding(new Insets(20));
        cartLayout.setSpacing(10);

        // Заголовок окна корзины
        Label titleLabel = new Label("Cart");
        titleLabel.setStyle("-fx-font-size: 24px;");

        // Контейнер для карточек товаров
        TilePane itemsPane = new TilePane();
        itemsPane.setPadding(new Insets(10));
        itemsPane.setHgap(10);
        itemsPane.setVgap(10);

        // Добавление товаров в контейнер
        for (CartItem item : cartItems) {
            itemsPane.getChildren().add(createCartItemNode(item));
        }

        // Общая стоимость
        Label totalCostLabel = new Label("Total Cost: $" + calculateTotalCost());

        // Кнопка перехода к оплате
        Button checkoutButton = new Button("Go to Payment");
        checkoutButton.setOnAction(event -> {
            // Переход на страницу оплаты
            PaymentPage paymentPage = new PaymentPage();
            paymentPage.show();
            this.close(); // Закрытие окна корзины
        });

        cartLayout.getChildren().addAll(titleLabel, itemsPane, totalCostLabel, checkoutButton);

        // Создание сцены и установка ее для этого окна
        Scene scene = new Scene(cartLayout, 400, 600);
        setScene(scene);

        // Установка заголовка окна
        setTitle("Cart");

        // Установка модального режима для окна корзины (блокирование взаимодействия с основным окном)
        initModality(Modality.APPLICATION_MODAL);
    }
    private VBox createCartItemNode(CartItem item) {
        VBox itemBox = new VBox();
        itemBox.setSpacing(5);

        // Изображение товара
        ImageView imageView = new ImageView(new Image(item.getImageUrl()));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        itemBox.getChildren().add(imageView);

        // Название товара
        Label nameLabel = new Label(item.getName());
        itemBox.getChildren().add(nameLabel);

        // Описание товара
        Label descriptionLabel = new Label(item.getDescription());
        itemBox.getChildren().add(descriptionLabel);

        // Цена товара
        Label priceLabel = new Label(item.getPrice());
        itemBox.getChildren().add(priceLabel);

        // Кнопка удаления товара
        Button removeButton = new Button("Delete");
        removeButton.setOnAction(event -> {
            cartItems.remove(item);
            refreshCart();
        });
        itemBox.getChildren().add(removeButton);

        return itemBox;
    }
    private void refreshCart() {
        TilePane itemsPane = (TilePane) ((VBox) getScene().getRoot()).getChildren().get(1);
        itemsPane.getChildren().clear();
        for (CartItem item : cartItems) {
            itemsPane.getChildren().add(createCartItemNode(item));
        }
        Label totalCostLabel = (Label) ((VBox) getScene().getRoot()).getChildren().get(2);
        totalCostLabel.setText("Total Cost: $" + calculateTotalCost());
    }
    public static void addToCart(String name, String description, String imageUrl, String price) {
        cartItems.add(new CartItem(name, description, imageUrl, price));
    }
    private double calculateTotalCost() {
        double totalCost = 0.0;
        for (CartItem item : cartItems) {
            totalCost += Double.parseDouble(item.getPrice().substring(1));
        }
        return totalCost;
    }
    public static void clearCart() {
        cartItems.clear();
    }
}
