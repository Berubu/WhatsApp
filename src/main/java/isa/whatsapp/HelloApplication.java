package isa.whatsapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/isa/whatsapp/Connection.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 315, 240);
        scene.getStylesheets().add(getClass().getResource("/isa/whatsapp/style.css").toExternalForm());
        stage.setTitle("WhatsApp Chat");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
