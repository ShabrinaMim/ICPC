import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage mystage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mystage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("loggedout.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("standings.fxml"));
        primaryStage.setTitle("ICPC Baylor");
        primaryStage.setScene(new Scene(root, 640, 480));
        primaryStage.show();
    }

    public static void main(String[] args) {
        magic.connectDB();
        launch(args);
    }

    public static void setScene(String filename) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Main.class.getResource(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mystage.setTitle("ICPC Baylor");
        mystage.setScene(new Scene(root, 640, 480));
        mystage.show();
    }

    public static void quitScene() {
        mystage.close();
    }
}
