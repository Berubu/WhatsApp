package isa.whatsapp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ConnectionController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField ipField;
    @FXML
    private TextField portField;
    @FXML
    private Button connectButton;

    @FXML
    private void initialize() {
        connectButton.setOnAction(event -> {
            String name = nameField.getText();
            String ip = ipField.getText();
            int port = Integer.parseInt(portField.getText());

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/isa/whatsapp/Chat.fxml"));
                Parent root = loader.load();

                ChatController chatController = loader.getController();
                chatController.initializeChat(name, ip, port);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Chat - " + name);
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
