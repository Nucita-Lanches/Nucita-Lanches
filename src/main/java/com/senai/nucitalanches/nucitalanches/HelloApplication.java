package com.senai.nucitalanches.nucitalanches;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class HelloApplication extends Application {
    private static final Duration INTRO_START_DELAY = Duration.millis(150);
    private static final Duration MEDIA_READY_TIMEOUT = Duration.seconds(8);
    private static final Duration PLAYBACK_CHECK_INTERVAL = Duration.seconds(4);
    private static final int MAX_STALLED_CHECKS = 2;

    private MediaPlayer introPlayer;
    private PauseTransition introStartDelay;
    private PauseTransition mediaReadyTimeout;
    private Timeline playbackWatchdog;
    private boolean introFinished;

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
        if (Boolean.getBoolean("nucita.skipIntro")) {
            stage.setScene(applicationScene);
            stage.show();
            return;
        }

        if (videoUrl == null) {
            System.err.println("Vídeo de abertura não encontrado; abrindo o atendimento sem a introdução.");
            showApplication(stage, applicationScene, null);
            return;
        }

        Button skipButton = new Button("Pular vídeo");
        skipButton.setStyle("-fx-background-color: rgba(30, 24, 26, 0.82); -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 16; -fx-background-radius: 8;");
        StackPane introRoot = new StackPane(skipButton);
        introRoot.setStyle("-fx-background-color: black;");
        StackPane.setAlignment(skipButton, Pos.TOP_RIGHT);
        StackPane.setMargin(skipButton, new Insets(20));
        Scene introScene = new Scene(introRoot, 1440, 900);

        // Show a usable window before initializing the platform-dependent media decoder.
        stage.setScene(introScene);
        stage.show();
        skipButton.setOnAction(event -> showApplication(stage, applicationScene, null));

        introStartDelay = new PauseTransition(INTRO_START_DELAY);
        introStartDelay.setOnFinished(event -> startIntro(stage, applicationScene, videoUrl, introRoot));
        introStartDelay.playFromStart();
    }

    private void startIntro(Stage stage, Scene applicationScene, URL videoUrl, StackPane introRoot) {
        if (introFinished) {
            return;
        }

        mediaReadyTimeout = new PauseTransition(MEDIA_READY_TIMEOUT);
        mediaReadyTimeout.setOnFinished(event -> showApplication(
                stage,
                applicationScene,
                "O vídeo de abertura não ficou pronto no tempo esperado; abrindo o atendimento."));
        mediaReadyTimeout.playFromStart();

        try {
            Media media = new Media(resolveMediaSource(videoUrl));
            media.setOnError(() -> showApplication(
                    stage,
                    applicationScene,
                    "Falha ao carregar o vídeo de abertura: " + media.getError()));
            if (media.getError() != null) {
                showApplication(
                        stage,
                        applicationScene,
                        "Falha ao carregar o vídeo de abertura: " + media.getError());
                return;
            }

            MediaPlayer player = new MediaPlayer(media);
            introPlayer = player;
            player.setOnError(() -> showApplication(
                    stage,
                    applicationScene,
                    "Falha ao reproduzir o vídeo de abertura: " + player.getError()));
            if (player.getError() != null) {
                showApplication(
                        stage,
                        applicationScene,
                        "Falha ao reproduzir o vídeo de abertura: " + player.getError());
                return;
            }

            MediaView mediaView = new MediaView(player);
            mediaView.setPreserveRatio(true);
            mediaView.fitWidthProperty().bind(introRoot.widthProperty());
            mediaView.fitHeightProperty().bind(introRoot.heightProperty());
            mediaView.setOnError(event -> showApplication(
                    stage,
                    applicationScene,
                    "Falha ao exibir o vídeo de abertura: " + event.getMediaError()));
            introRoot.getChildren().add(0, mediaView);

            player.setOnReady(() -> {
                if (introFinished) {
                    return;
                }
                mediaReadyTimeout.stop();
                try {
                    player.setVolume(1.0);
                    player.play();
                    startPlaybackWatchdog(stage, applicationScene, player);
                } catch (RuntimeException exception) {
                    showApplication(
                            stage,
                            applicationScene,
                            "Falha ao iniciar a reprodução do vídeo de abertura: " + exception.getMessage());
                }
            });
            player.setOnEndOfMedia(() -> showApplication(stage, applicationScene, null));
            player.setOnHalted(() -> showApplication(
                    stage,
                    applicationScene,
                    "O reprodutor interrompeu o vídeo de abertura; abrindo o atendimento."));
        } catch (RuntimeException | IOException exception) {
            showApplication(
                    stage,
                    applicationScene,
                    "Falha ao iniciar o vídeo de abertura: " + exception.getMessage());
        }
    }

    private void startPlaybackWatchdog(Stage stage, Scene applicationScene, MediaPlayer player) {
        double[] lastPlaybackPosition = {Double.NaN};
        int[] stalledChecks = {0};
        playbackWatchdog = new Timeline(new KeyFrame(PLAYBACK_CHECK_INTERVAL, event -> {
            if (introFinished) {
                return;
            }

            try {
                double currentPosition = player.getCurrentTime().toMillis();
                if (Double.isFinite(currentPosition)
                        && (!Double.isFinite(lastPlaybackPosition[0])
                        || currentPosition > lastPlaybackPosition[0] + 50)) {
                    lastPlaybackPosition[0] = currentPosition;
                    stalledChecks[0] = 0;
                    return;
                }

                stalledChecks[0]++;
                if (stalledChecks[0] < MAX_STALLED_CHECKS) {
                    return;
                }
                showApplication(
                        stage,
                        applicationScene,
                        "A reprodução do vídeo não avançou; abrindo o atendimento.");
            } catch (RuntimeException exception) {
                showApplication(
                        stage,
                        applicationScene,
                        "Falha ao acompanhar o vídeo de abertura: " + exception.getMessage());
            }
        }));
        playbackWatchdog.setCycleCount(Animation.INDEFINITE);
        playbackWatchdog.playFromStart();
    }

    private void showApplication(Stage stage, Scene applicationScene, String diagnostic) {
        if (introFinished) {
            return;
        }
        introFinished = true;

        if (diagnostic != null) {
            System.err.println(diagnostic);
        }
        if (introStartDelay != null) {
            introStartDelay.stop();
        }
        if (mediaReadyTimeout != null) {
            mediaReadyTimeout.stop();
        }
        if (playbackWatchdog != null) {
            playbackWatchdog.stop();
        }
        disposeIntroPlayer();

        stage.setScene(applicationScene);
        stage.show();
    }

    private void disposeIntroPlayer() {
        if (introPlayer != null) {
            introPlayer.dispose();
            introPlayer = null;
        }
    }

    @Override
    public void stop() {
        introFinished = true;
        if (introStartDelay != null) {
            introStartDelay.stop();
        }
        if (mediaReadyTimeout != null) {
            mediaReadyTimeout.stop();
        }
        if (playbackWatchdog != null) {
            playbackWatchdog.stop();
        }
        disposeIntroPlayer();
    }

    private static String resolveMediaSource(URL videoUrl) throws IOException {
        if ("file".equalsIgnoreCase(videoUrl.getProtocol())) {
            return videoUrl.toExternalForm();
        }

        Path temporaryVideo = Files.createTempFile("nucita-abertura-", ".mp4");
        try (var videoStream = videoUrl.openStream()) {
            Files.copy(videoStream, temporaryVideo, StandardCopyOption.REPLACE_EXISTING);
            temporaryVideo.toFile().deleteOnExit();
            return temporaryVideo.toUri().toString();
        } catch (IOException exception) {
            Files.deleteIfExists(temporaryVideo);
            throw exception;
        }
    }
}
