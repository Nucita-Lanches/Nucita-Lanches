package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.application.Restaurante;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import com.senai.nucitalanches.nucitalanches.domain.StatusPedido;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.util.Collection;
import java.util.function.Consumer;

final class ProductCatalogController {
    private final Restaurante restaurante;
    private final TextField searchField;
    private final TilePane productGrid;
    private final HBox searchBox;
    private final VBox emptyState;
    private final Label emptyLabel;
    private final Label catalogCountLabel;
    private final Label catalogHintLabel;
    private final Label emptyTitle;
    private final Button emptyActionButton;
    private final Runnable manageProducts;
    private final Runnable showMenu;
    private final Consumer<Produto> addProduct;
    private Pedido activeOrder;

    ProductCatalogController(Restaurante restaurante, TextField searchField, TilePane productGrid,
                             HBox searchBox, VBox emptyState, Label emptyLabel,
                             Label catalogCountLabel, Label catalogHintLabel, Label emptyTitle,
                             Button emptyActionButton, Runnable manageProducts, Runnable showMenu,
                             Consumer<Produto> addProduct) {
        this.restaurante = restaurante;
        this.searchField = searchField;
        this.productGrid = productGrid;
        this.searchBox = searchBox;
        this.emptyState = emptyState;
        this.emptyLabel = emptyLabel;
        this.catalogCountLabel = catalogCountLabel;
        this.catalogHintLabel = catalogHintLabel;
        this.emptyTitle = emptyTitle;
        this.emptyActionButton = emptyActionButton;
        this.manageProducts = manageProducts;
        this.showMenu = showMenu;
        this.addProduct = addProduct;
        searchField.textProperty().addListener((obs, old, value) -> render(activeOrder));
        searchField.focusedProperty().addListener((obs, old, focused) ->
                searchBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("focused-within"), focused));
        productGrid.widthProperty().addListener((obs, old, value) -> sizeProductCards());
    }

    void render(Pedido activeOrder) {
        this.activeOrder = activeOrder;
        productGrid.getChildren().clear();
        String query = ProductVisuals.normalize(searchField.getText().trim());
        Collection<Produto> products = restaurante.listarProdutos();
        for (Produto produto : products) {
            if (!ProductVisuals.normalize(produto.getNome()).contains(query)) continue;
            Node artwork = ProductVisuals.createArtwork(produto);
            Button add = new Button("Adicionar", UiIcons.icon("plus"));
            add.setGraphicTextGap(5);
            add.setId("addProduct-" + produto.getId());
            add.getStyleClass().add("add-button");
            add.setAccessibleText("Adicionar " + produto.getNome() + " ao pedido");
            boolean orderOpen = activeOrder != null && activeOrder.getStatus() == StatusPedido.ABERTO;
            add.setDisable(!orderOpen);
            add.setTooltip(new Tooltip(activeOrder == null
                    ? "Selecione uma mesa e crie um pedido primeiro."
                    : orderOpen ? "Adicionar ao pedido " + activeOrder.getNumeroPedido()
                    : "Este pedido já foi finalizado."));
            add.setOnAction(event -> addProduct.accept(produto));

            VBox pricing = new VBox(3,
                    UiSupport.label(UiSupport.money(produto.getPreco()), "product-price"),
                    UiSupport.label("por unidade", "unit-caption"));
            HBox footer = new HBox(8, pricing, UiSupport.spacer(), add);
            footer.setAlignment(Pos.CENTER_LEFT);
            Label name = UiSupport.label(produto.getNome(), "product-name");
            name.setMinHeight(40);
            name.setMaxHeight(40);
            name.setTooltip(new Tooltip(produto.getNome()));
            VBox body = new VBox(9,
                    UiSupport.label("PRODUTO " + String.format("%02d", produto.getId()), "product-description"),
                    name, footer);
            body.getStyleClass().add("product-body");
            VBox card = new VBox(artwork, body);
            card.setPrefWidth(220);
            card.setMaxWidth(220);
            card.getStyleClass().add("product-card");
            productGrid.getChildren().add(card);
        }

        boolean empty = productGrid.getChildren().isEmpty();
        int count = products.size();
        catalogCountLabel.setText(count == 0 ? "Seu cardápio começa aqui."
                : count + (count == 1 ? " produto preparado para o atendimento."
                : " produtos preparados para o atendimento."));
        catalogHintLabel.setText(activeOrder == null ? "Abra um pedido para adicionar itens."
                : "Adicionando ao pedido #" + activeOrder.getNumeroPedido());
        emptyTitle.setText(count == 0 ? "Seu cardápio começa aqui." : "Nenhum sabor por aqui.");
        emptyLabel.setText(count == 0
                ? "Cadastre seu primeiro produto e prepare a casa para receber novos pedidos."
                : "Tente outro nome para encontrar o que você procura.");
        emptyLabel.setVisible(empty);
        emptyLabel.setManaged(empty);
        emptyState.setVisible(empty);
        emptyState.setManaged(empty);
        emptyActionButton.setText(count == 0 ? "Cadastrar primeiro produto" : "Ver todo o cardápio");
        emptyActionButton.setOnAction(event -> {
            if (count == 0) manageProducts.run();
            else showMenu.run();
        });
        productGrid.setManaged(!empty);
        sizeProductCards();
    }

    private void sizeProductCards() {
        double available = productGrid.getWidth();
        if (available <= 0) return;
        int columns = Math.min(3, Math.max(1, (int) ((available + 18) / 228)));
        double width = Math.floor((available - (columns - 1) * 18) / columns);
        productGrid.setPrefTileWidth(width);
        for (Node child : productGrid.getChildren()) {
            if (child instanceof VBox card) {
                card.setMinWidth(width);
                card.setPrefWidth(width);
                card.setMaxWidth(width);
            }
        }
    }
}
