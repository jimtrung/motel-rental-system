import db.Database;
import javafx.application.Application;
import javafx.stage.Stage;
import repository.UserRepository;
import service.UserService;
import ui.HomeUI;

import java.sql.Connection;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
  @Override
  public void start(Stage stage) {
    Database.init();

    HomeUI home = new HomeUI();
    stage.setScene(home.createScene());
    stage.setTitle("Motel Rental System - Home");
    stage.show();
  }

  public static void main(String[] args) {
    Logger log =  Logger.getLogger(Main.class.getName());

    Connection conn = null;

    try {
      conn = Database.getConnection();
    } catch (Exception e) {
      log.log(Level.SEVERE, e.toString());
      System.exit(1);
    }
    launch();
  }
}