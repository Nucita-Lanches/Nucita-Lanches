package com.senai.nucitalanches.nucitalanches;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/cardapio.fxml"));
        Parent applicationRoot = fxmlLoader.load();
        Scene applicationScene = new Scene(applicationRoot, 1440, 900);
        stage.setTitle("Nucita Lanches | Atendimento");
        stage.setMinWidth(1220);
        stage.setMinHeight(730);
        stage.getIcons().add(new javafx.scene.image.Image(
                HelloApplication.class.getResourceAsStream("images/logo.png")));

        URL videoUrl = HelloApplication.class.getResource("videos/abertura.mp4");
        if (Boolean.getBoolean("nucita.skipIntro") || videoUrl == null) {
            stage.setScene(applicationScene);
            stage.show();
            return;
        }

        try {
            Media media = new Media(videoUrl.toExternalForm());
            MediaPlayer player = new MediaPlayer(media);
            MediaView mediaView = new MediaView(player);
            mediaView.setPreserveRatio(true);
            mediaView.fitWidthProperty().bind(applicationScene.widthProperty());
            mediaView.fitHeightProperty().bind(applicationScene.heightProperty());

            Button skipButton = new Button("Pular vídeo");
            skipButton.setStyle("-fx-background-color: rgba(30, 24, 26, 0.82); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 16; -fx-background-radius: 8;");
            StackPane introRoot = new StackPane(mediaView, skipButton);
            introRoot.setStyle("-fx-background-color: black;");
            StackPane.setAlignment(skipButton, Pos.TOP_RIGHT);
            StackPane.setMargin(skipButton, new Insets(20));
            Scene introScene = new Scene(introRoot, 1440, 900);

            Runnable showApplication = () -> {
                if (stage.getScene() != applicationScene) {
                    stage.setScene(applicationScene);
                    stage.show();
                    player.stop();
                    player.dispose();
                }
            };
            skipButton.setOnAction(event -> showApplication.run());
            player.setOnEndOfMedia(showApplication);
            player.setOnError(() -> {
                System.err.println("Falha ao reproduzir o vídeo de abertura: " + player.getError());
                showApplication.run();
            });
            media.setOnError(() -> {
                System.err.println("Falha ao carregar o vídeo de abertura: " + media.getError());
                showApplication.run();
            });

            stage.setScene(introScene);
            stage.show();
            player.setVolume(1.0);
            player.play();
        } catch (MediaException exception) {
            System.err.println("Falha ao iniciar o vídeo de abertura: " + exception.getMessage());
            stage.setScene(applicationScene);
            stage.show();
        }
    }
}
