package com.senai.nucitalanches.nucitalanches;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("view/cardapio.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1440, 900);
        stage.setTitle("Nucita Lanches | Atendimento");
        stage.setMinWidth(1220);
        stage.setMinHeight(730);
        stage.getIcons().add(new javafx.scene.image.Image(
                HelloApplication.class.getResourceAsStream("images/logo.png")));
        stage.setScene(scene);
        stage.show();
    }
}
