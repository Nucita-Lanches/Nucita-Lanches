package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

final class UiSupport {
    private UiSupport() {
    }

    static Label label(String text, String style) {
        Label result = new Label(text);
        result.getStyleClass().add(style);
        result.setWrapText(true);
        return result;
    }

    static Region spacer() {
        Region region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        return region;
    }

    static String money(double value) {
        return NumberFormat.getCurrencyInstance(Locale.of("pt", "BR")).format(value);
    }

    static String mesaName(Mesa mesa) {
        return "Mesa " + mesa.getNumero();
    }

    static <T> Dialog<T> dialog(String title, Window owner, Button menuNav, Button productsNav,
                                Button tablesNav, Button ordersNav) {
        Dialog<T> dialog = new Dialog<>();
        dialog.setTitle("Nucita Lanches • " + title);
        dialog.initOwner(owner);
        dialog.getDialogPane().getStylesheets().add(Objects.requireNonNull(
                UiSupport.class.getResource("/com/senai/nucitalanches/nucitalanches/css/javafx-nucita-theme.css"))
                .toExternalForm());
        dialog.getDialogPane().getStyleClass().add("management-dialog");
        VBox header = new VBox(7,
                label("NUCITA / ATENDIMENTO", "dialog-eyebrow"),
                label(title, "dialog-title"),
                label(switch (title) {
                    case "Produtos" -> "Os sabores da casa, organizados em um só lugar.";
                    case "Mesas" -> "Prepare o próximo atendimento e escolha onde servir.";
                    case "Pedidos" -> "Cada mesa, cada escolha. Continue de onde parou.";
                    default -> "Mais um favorito para este pedido.";
                }, "dialog-copy"));
        header.getStyleClass().add("dialog-header");
        Button active = switch (title) {
            case "Produtos" -> productsNav;
            case "Mesas" -> tablesNav;
            case "Pedidos" -> ordersNav;
            default -> menuNav;
        };
        List<Button> navigation = List.of(menuNav, productsNav, tablesNav, ordersNav);
        for (Button button : navigation) {
            button.getStyleClass().remove("nav-active");
            if (button == active) button.getStyleClass().add("nav-active");
        }
        dialog.getDialogPane().setHeader(header);
        dialog.setOnHidden(event -> {
            for (Button button : List.of(productsNav, tablesNav, ordersNav)) {
                button.getStyleClass().remove("nav-active");
            }
            if (!menuNav.getStyleClass().contains("nav-active")) {
                menuNav.getStyleClass().add("nav-active");
            }
        });
        dialog.setResizable(true);
        return dialog;
    }

    static <T> void configureList(ListView<T> list, Function<T, Node> display) {
        list.setCellFactory(view -> new ListCell<>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                setText(null);
                Node graphic = empty || item == null ? null : display.apply(item);
                if (graphic instanceof Region row) {
                    row.prefWidthProperty().bind(list.widthProperty().subtract(16));
                }
                setGraphic(graphic);
            }
        });
        list.setPrefHeight(315);
        list.setMinHeight(180);
    }

    static HBox recordRow(Node marker, String title, String subtitle, Node end) {
        VBox text = new VBox(5, label(title, "record-name"), label(subtitle, "record-meta"));
        text.setMinWidth(0);
        HBox.setHgrow(text, Priority.ALWAYS);
        HBox row = new HBox(11, marker, text);
        if (end != null) row.getChildren().add(end);
        row.setAlignment(Pos.CENTER_LEFT);
        row.getStyleClass().add("record-row");
        return row;
    }

    static VBox content(Node... nodes) {
        VBox box = new VBox(15, nodes);
        box.getStyleClass().add("dialog-content");
        box.setPrefWidth(510);
        return box;
    }

    static VBox form(Node... nodes) {
        VBox box = new VBox(14, nodes);
        box.getStyleClass().add("form-panel");
        box.setMinWidth(280);
        return box;
    }

    static VBox field(String caption, TextField input) {
        Label name = label(caption, "field-label");
        name.setLabelFor(input);
        return new VBox(7, name, input);
    }

    static HBox splitContent(VBox left, VBox right) {
        left.setPrefWidth(365);
        left.setMinWidth(300);
        right.setPrefWidth(305);
        HBox.setHgrow(left, Priority.ALWAYS);
        HBox.setHgrow(right, Priority.ALWAYS);
        HBox box = new HBox(22, left, right);
        box.getStyleClass().add("dialog-content");
        box.setPrefWidth(790);
        box.setAlignment(Pos.TOP_LEFT);
        return box;
    }

    static Label feedback(String id) {
        Label feedback = label("", "form-feedback");
        feedback.setId(id);
        feedback.visibleProperty().bind(feedback.textProperty().isNotEmpty());
        feedback.managedProperty().bind(feedback.visibleProperty());
        return feedback;
    }

    static void action(Label feedback, Runnable operation) {
        feedback.getStyleClass().remove("feedback-error");
        try {
            operation.run();
        } catch (IllegalArgumentException exception) {
            feedback.getStyleClass().add("feedback-error");
            feedback.setText(exception.getMessage());
        }
    }

    static int integer(String value, String field) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(field + ": informe um número inteiro válido.");
        }
    }

    static double price(String value) {
        try {
            return Double.parseDouble(value.trim().replace(',', '.'));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Informe um preço numérico, sem separador de milhar. Ex.: 12,50.");
        }
    }

    static File chooseImageFile(Window owner) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Selecionar imagem do produto");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(
                "Imagens (PNG, JPG, WEBP, GIF)", "*.png", "*.jpg", "*.jpeg", "*.webp", "*.gif"));
        return chooser.showOpenDialog(owner);
    }
}
