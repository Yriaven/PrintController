package klasy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/core/sample.fxml"));
        primaryStage.setTitle("EP Print Management");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        DBManager.ConnectToDatabase();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
