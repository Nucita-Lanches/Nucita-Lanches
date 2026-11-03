package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.application.Restaurante;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Window;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

final class ProductDialogController {
    private final Restaurante restaurante;
    private final Button menuNav;
    private final Button productsNav;
    private final Button tablesNav;
    private final Button ordersNav;
    private final Consumer<String> setStatus;
    private final Runnable refreshCatalog;
    private final Runnable refreshOrder;

    ProductDialogController(Restaurante restaurante, Button menuNav, Button productsNav,
                            Button tablesNav, Button ordersNav, Consumer<String> setStatus,
                            Runnable refreshCatalog, Runnable refreshOrder) {
        this.restaurante = restaurante;
        this.menuNav = menuNav;
        this.productsNav = productsNav;
        this.tablesNav = tablesNav;
        this.ordersNav = ordersNav;
        this.setStatus = setStatus;
        this.refreshCatalog = refreshCatalog;
        this.refreshOrder = refreshOrder;
    }

    void show(Window owner) {
        Dialog<Void> dialog = UiSupport.dialog("Produtos", owner, menuNav, productsNav, tablesNav, ordersNav);
        dialog.getDialogPane().setId("productsDialog");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        ListView<Produto> list = new ListView<>();
        list.setId("productsList");
        UiSupport.configureList(list, produto -> UiSupport.recordRow(
                ProductVisuals.createMarker(produto, "record-number"),
                produto.getNome(),
                "Produto #" + produto.getId() + (produto.possuiImagem() ? " • Com foto" : ""),
                UiSupport.label(UiSupport.money(produto.getPreco()), "record-value")));
        list.getItems().setAll(restaurante.listarProdutos());
        list.setPlaceholder(new Label("Nenhum produto cadastrado."));

        TextField name = new TextField();
        name.setId("productName");
        name.setPromptText("Nome do produto");
        TextField price = new TextField();
        price.setId("productPrice");
        price.setPromptText("Ex.: 12,50");

        AtomicReference<String> selectedImageUrl = new AtomicReference<>(null);
        ImageView preview = new ImageView();
        preview.setFitWidth(44);
        preview.setFitHeight(44);
        preview.setPreserveRatio(false);
        preview.setClip(new Circle(22, 22, 22));
        StackPane previewHolder = new StackPane(UiIcons.icon("products"));
        previewHolder.getStyleClass().add("empty-symbol");
        previewHolder.setMinSize(44, 44);
        previewHolder.setPrefSize(44, 44);
        previewHolder.setMaxSize(44, 44);
        Label imageNameLabel = UiSupport.label("Nenhuma imagem selecionada", "form-hint");
        Button chooseImageButton = new Button("Escolher imagem...");
        chooseImageButton.setId("selectProductImage");
        chooseImageButton.getStyleClass().add("secondary-button");
        Button clearImageButton = new Button("Limpar");
        clearImageButton.setId("clearProductImage");
        clearImageButton.getStyleClass().add("item-control");
        clearImageButton.setVisible(false);
        clearImageButton.setManaged(false);

        Runnable resetImageSelection = () -> {
            selectedImageUrl.set(null);
            previewHolder.getChildren().setAll(UiIcons.icon("products"));
            imageNameLabel.setText("Nenhuma imagem selecionada");
            clearImageButton.setVisible(false);
            clearImageButton.setManaged(false);
        };
        chooseImageButton.setOnAction(event -> {
            File file = UiSupport.chooseImageFile(dialog.getDialogPane().getScene().getWindow());
            if (file == null) return;
            String uri = file.toURI().toString();
            javafx.scene.image.Image image = ProductVisuals.loadImage(uri, 88, 88);
            if (image == null) return;
            selectedImageUrl.set(uri);
            preview.setImage(image);
            previewHolder.getChildren().setAll(preview);
            imageNameLabel.setText(file.getName());
            clearImageButton.setVisible(true);
            clearImageButton.setManaged(true);
        });
        clearImageButton.setOnAction(event -> resetImageSelection.run());

        HBox imageButtons = new HBox(8, chooseImageButton, clearImageButton);
        imageButtons.setAlignment(Pos.CENTER_LEFT);
        VBox imageControls = new VBox(5, imageButtons, imageNameLabel);
        HBox.setHgrow(imageControls, javafx.scene.layout.Priority.ALWAYS);
        HBox imagePickerRow = new HBox(12, previewHolder, imageControls);
        imagePickerRow.setAlignment(Pos.CENTER_LEFT);
        VBox imageField = new VBox(7,
                UiSupport.label("Imagem do produto (opcional)", "field-label"), imagePickerRow);

        Label feedback = UiSupport.feedback("productFeedback");
        Button save = new Button("Cadastrar produto");
        save.setId("saveProduct");
        save.getStyleClass().add("hero-button");
        save.setMaxWidth(Double.MAX_VALUE);
        save.setOnAction(event -> UiSupport.action(feedback, () -> {
            Produto produto = new Produto(name.getText(), UiSupport.price(price.getText()), selectedImageUrl.get());
            restaurante.cadastroProduto(produto);
            list.getItems().setAll(restaurante.listarProdutos());
            name.clear();
            price.clear();
            resetImageSelection.run();
            feedback.setText("Produto cadastrado: " + produto.getNome() + ".");
            setStatus.accept("Produto cadastrado no cardápio.");
            refreshCatalog.run();
            name.requestFocus();
        }));

        Button updateSelectedImageButton = new Button("Definir imagem no produto selecionado");
        updateSelectedImageButton.setId("updateSelectedProductImage");
        updateSelectedImageButton.getStyleClass().add("secondary-button");
        updateSelectedImageButton.setMaxWidth(Double.MAX_VALUE);
        updateSelectedImageButton.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        updateSelectedImageButton.setOnAction(event -> UiSupport.action(feedback, () -> {
            Produto selected = list.getSelectionModel().getSelectedItem();
            if (selected == null) return;
            File file = UiSupport.chooseImageFile(dialog.getDialogPane().getScene().getWindow());
            if (file == null) return;
            selected.setImagemUrl(file.toURI().toString());
            list.refresh();
            refreshCatalog.run();
            refreshOrder.run();
            feedback.setText("Imagem atualizada para " + selected.getNome() + ".");
            setStatus.accept("Imagem do produto atualizada.");
        }));

        VBox catalog = new VBox(10, UiSupport.label("Produtos cadastrados", "panel-heading"), list,
                updateSelectedImageButton,
                UiSupport.label("O cardápio é atualizado assim que você cadastra ou altera um produto.", "form-hint"));
        dialog.getDialogPane().setContent(UiSupport.splitContent(catalog,
                UiSupport.form(UiSupport.label("Adicionar ao cardápio", "panel-heading"),
                        UiSupport.label("Um nome, um preço, uma foto e mais um motivo para voltar.", "form-hint"),
                        UiSupport.field("Nome do produto", name),
                        UiSupport.field("Preço por unidade · R$", price),
                        UiSupport.label("Use vírgula ou ponto para os centavos.", "form-hint"),
                        imageField, feedback, save)));
        dialog.showAndWait();
    }
}
