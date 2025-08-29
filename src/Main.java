import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        HomeUI home = new HomeUI();
        stage.setScene(home.createScene());
        stage.setTitle("Motel Rental System - Home");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}