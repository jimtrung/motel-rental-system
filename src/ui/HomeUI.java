package ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

// Giao diện trang chủ (Home page)
public class HomeUI {
    public Scene createScene() {
        /* Top */
        Label title = new Label("Motel Rental System");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle(
            "-fx-alignment: center;" +
            "-fx-font-size: 30px;" +
            "-fx-text-alignment: center;" +
            "-fx-font-weight: bold;" +
            "-fx-padding: 50px 0 0 0;"
        );

        /* Center */
        Button viewRooms = new Button("Đăng nhập");
        Button bookRoom = new Button("Đăng ký");
        Button myBookings = new Button("Cài đặt");

        HBox centerBox = new HBox(15, viewRooms, bookRoom, myBookings);
        centerBox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        /* Root Layout */
        BorderPane root = new BorderPane();
        root.setTop(title);
        root.setCenter(centerBox);

        /* Scene */
        return new Scene(root, 600, 400);
    }
}