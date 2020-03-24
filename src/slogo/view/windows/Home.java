package slogo.view.windows;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import slogo.Manager;

public class Home extends Application {

  private static final int WINDOW_WIDTH = 450;
  private static final int WINDOW_HEIGHT = 275;

  private String myDefaultLang = "ENGLISH";
  private String myColorScheme;

  @FXML
  private Button LaunchSlogo;
  @FXML
  private Button ColorScheme;
  @FXML
  private Button Language;
  @FXML
  private Button Defaults;
  @FXML
  private Button Help;

  /**
   * Entry point for application
   * @param primaryStage the main stage for the Home view
   * @throws IOException caused by FileInputStream for loading the FXML
   */
  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader();
    String fxmlPath = "src/slogo/view/windows/HomeView.fxml";
    System.out.println(System.getProperty("user.dir"));
    FileInputStream fis = new FileInputStream(fxmlPath);
    GridPane ap = loader.load(fis);
    ap.setVgap(5);
    ap.setPadding(new Insets(15, 15, 15, 15));
    Scene myHomeScene = new Scene(ap, WINDOW_WIDTH, WINDOW_HEIGHT);
    primaryStage.setScene(myHomeScene);
    primaryStage.show();
  }

  /**
   * Sets actions for buttons
   *  Both for launching the application
   *  and for changing settings
   */
  public void initialize() {
    LaunchSlogo.setOnAction(e -> {
      try {
        launchSim();
      } catch (IOException ex) {
        System.out.println("INVALID SIMULATION LAUNCH ERROR");
      }
    });
    //TODO: Add actual events to these button clicks
    ColorScheme.setOnAction(e -> System.out.println("ColorScheme"));
    Language.setOnAction(e -> System.out.println("Language Select"));
    Defaults.setOnAction(e -> System.out.println("Set Defaults"));
    Help.setOnAction(e -> System.out.println("Display Help Menu"));
  }

  @FXML
  private void launchSim() throws IOException {
    new Manager(myDefaultLang);
  }

  private void setDefaultLang(String newLang) {
    myDefaultLang = newLang;
  }

}
