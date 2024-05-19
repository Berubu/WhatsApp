package isa.whatsapp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ChatController {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox messagesBox;
    @FXML
    private TextField messageField;
    @FXML
    private Button sendButton;
    @FXML
    private ImageView backgroundImage;

    private PrintWriter out;
    private String userName;
    private Map<String, String> userColors = new HashMap<>();
    private int colorIndex = 0;
    private String[] colors =
            {"#E6E6FA", "#F0E68C", "#FFB6C1", "#87CEEB", "#FFA07A", "#98FB98", "#DDA0DD", "#FA8072", "#FFD700"};

    public void initializeChat(String name, String ip, int port) {
        this.userName = name;

        Image image = new Image("file:src/main/resources/isa/whatsapp/3.jpg");
        backgroundImage.setImage(image);

        try {
            Socket socket = new Socket(ip, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(name);

            // Hilo para escuchar los mensajes dl servidor
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        String finalMsg = msg;
                        Platform.runLater(() -> addMessage(finalMsg, !finalMsg.startsWith(userName + ":")));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            sendButton.setOnAction(event -> {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    out.println(name + ": " + message);
                    addMessage(name + ": " + message, false);
                    messageField.clear();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addMessage(String message, boolean isReceived) {
        HBox hBox = new HBox();
        Label label = new Label(message);
        hBox.getChildren().add(label);
        HBox.setHgrow(label, Priority.ALWAYS);

        String user = message.split(":")[0];
        if (!userColors.containsKey(user)) {
            userColors.put(user, colors[colorIndex % colors.length]);
            colorIndex++;
        }

        label.setStyle("-fx-background-color: " + userColors.get(user) + "; -fx-padding: 5px; -fx-background-radius: 10px;");

        if (isReceived) {
            hBox.setStyle("-fx-alignment: center-left;");
        } else {
            hBox.setStyle("-fx-alignment: center-right;");
        }

        messagesBox.getChildren().add(hBox);
        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }
}
