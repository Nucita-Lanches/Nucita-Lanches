package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.domain.Produto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.text.Normalizer;
import java.util.Locale;

final class ProductVisuals {
    private ProductVisuals() {
    }

    static Image loadImage(String url, double width, double height) {
        if (url == null || url.isBlank()) return null;
        try {
            Image image = new Image(url, width, height, true, true, false);
            return image.isError() ? null : image;
        } catch (Exception exception) {
            return null;
        }
    }

    static StackPane createArtwork(Produto produto) {
        Circle ring = new Circle(43);
        ring.getStyleClass().add("product-art-ring");
        Label monogram = UiSupport.label(initials(produto.getNome()), "product-monogram");
        Label mark = UiSupport.label("NUCITA LANCHES", "product-art-caption");
        StackPane.setAlignment(mark, Pos.BOTTOM_CENTER);
        StackPane.setMargin(mark, new Insets(0, 0, 12, 0));

        StackPane pane = new StackPane(ring, monogram, mark);
        pane.getStyleClass().add("product-art");
        int variant = (int) (produto.getId() % 3);
        if (variant == 1) pane.getStyleClass().add("product-art-dark");
        else if (variant == 2) pane.getStyleClass().add("product-art-rose");

        if (produto.possuiImagem()) {
            Image loaded = loadImage(produto.getImagemUrl(), 440, 280);
            if (loaded != null) {
                ImageView imageView = new ImageView(loaded);
                imageView.setPreserveRatio(false);
                imageView.setSmooth(true);
                imageView.fitWidthProperty().bind(pane.widthProperty());
                imageView.fitHeightProperty().bind(pane.heightProperty());

                Rectangle clip = new Rectangle();
                clip.widthProperty().bind(pane.widthProperty());
                clip.heightProperty().bind(pane.heightProperty());
                clip.setArcWidth(24);
                clip.setArcHeight(24);
                imageView.setClip(clip);
                pane.getChildren().setAll(imageView, mark);
            }
        }
        return pane;
    }

    static Node createMarker(Produto produto, String fallbackStyle) {
        if (produto.possuiImagem()) {
            Image loaded = loadImage(produto.getImagemUrl(), 72, 72);
            if (loaded != null) {
                ImageView imageView = new ImageView(loaded);
                imageView.setFitWidth(36);
                imageView.setFitHeight(36);
                imageView.setPreserveRatio(false);
                imageView.setClip(new Circle(18, 18, 18));
                StackPane wrapper = new StackPane(imageView);
                wrapper.setMinSize(36, 36);
                wrapper.setPrefSize(36, 36);
                wrapper.setMaxSize(36, 36);
                return wrapper;
            }
        }
        return UiSupport.label(initials(produto.getNome()), fallbackStyle);
    }

    private static String initials(String name) {
        String[] words = name.trim().split("\\s+");
        String first = Character.toString(words[0].codePointAt(0));
        String last = words.length > 1 ? Character.toString(words[words.length - 1].codePointAt(0)) : "";
        return (first + last).toUpperCase(Locale.of("pt", "BR"));
    }

    static String normalize(String text) {
        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT);
    }
}
