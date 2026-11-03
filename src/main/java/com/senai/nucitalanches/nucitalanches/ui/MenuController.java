package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.application.Restaurante;
import com.senai.nucitalanches.nucitalanches.domain.ItemPedido;
import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import com.senai.nucitalanches.nucitalanches.domain.StatusPedido;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

public class MenuController {
    @FXML private TextField searchField;
    @FXML private TilePane productGrid;
    @FXML private HBox searchBox;
    @FXML private VBox cartItems;
    @FXML private VBox emptyState;
    @FXML private Label emptyLabel;
    @FXML private Label countLabel;
    @FXML private Label tableLabel;
    @FXML private Label statusLabel;
    @FXML private Label orderTitle;
    @FXML private Label nextTableLabel;
    @FXML private Label catalogCountLabel;
    @FXML private Label catalogHintLabel;
    @FXML private Label orderStatusLabel;
    @FXML private Label emptyTitle;
    @FXML private Label totalLabel;
    @FXML private Button newOrderButton;
    @FXML private Button finalizeOrderButton;
    @FXML private Button emptyActionButton;
    @FXML private Button menuNav;
    @FXML private Button productsNav;
    @FXML private Button tablesNav;
    @FXML private Button ordersNav;

    private final Restaurante restaurante = new Restaurante();
    private Mesa selectedMesa;
    private Pedido activeOrder;
    private ProductCatalogController catalog;
    private OrderPanelController orderPanel;
    private ProductDialogController productDialog;
    private TableDialogController tableDialog;
    private OrdersDialogController ordersDialog;
    private QuantityDialogController quantityDialog;

    @FXML
    private void initialize() {
        menuNav.setGraphic(UiIcons.icon("menu"));
        productsNav.setGraphic(UiIcons.icon("products"));
        tablesNav.setGraphic(UiIcons.icon("tables"));
        ordersNav.setGraphic(UiIcons.icon("orders"));
        newOrderButton.setGraphic(UiIcons.icon("plus"));
        newOrderButton.setGraphicTextGap(10);

        catalog = new ProductCatalogController(restaurante, searchField, productGrid, searchBox,
                emptyState, emptyLabel, catalogCountLabel, catalogHintLabel, emptyTitle,
                emptyActionButton, this::showProducts, this::showMenu, this::showAddItem);
        orderPanel = new OrderPanelController(cartItems, countLabel, tableLabel, orderTitle,
                nextTableLabel, orderStatusLabel, totalLabel, newOrderButton, finalizeOrderButton,
                this::showEditQuantity, this::removeItem);
        productDialog = new ProductDialogController(restaurante, menuNav, productsNav, tablesNav,
                ordersNav, statusLabel::setText, this::renderProducts, this::renderOrder);
        tableDialog = new TableDialogController(restaurante, menuNav, productsNav, tablesNav, ordersNav);
        ordersDialog = new OrdersDialogController(menuNav, productsNav, tablesNav, ordersNav);
        quantityDialog = new QuantityDialogController(restaurante, menuNav, productsNav, tablesNav, ordersNav);

        renderProducts();
        renderOrder();
        statusLabel.setText("Cadastre produtos e mesas para começar o atendimento.");
    }

    private Window owner() {
        return searchField.getScene().getWindow();
    }

    private void renderProducts() {
        catalog.render(activeOrder);
    }

    private void renderOrder() {
        orderPanel.render(activeOrder, selectedMesa);
    }

    @FXML
    private void showMenu() {
        searchField.clear();
        renderProducts();
    }

    @FXML
    private void showProducts() {
        productDialog.show(owner());
    }

    @FXML
    private void showTables() {
        tableDialog.show(owner(), selectedMesa).ifPresent(mesa -> {
            selectedMesa = mesa;
            renderOrder();
            statusLabel.setText(UiSupport.mesaName(mesa) + " selecionada para o próximo pedido.");
        });
    }

    @FXML
    private void createOrder() {
        if (selectedMesa == null) return;
        try {
            activeOrder = restaurante.criarPedido(selectedMesa.getNumero());
            renderOrder();
            renderProducts();
            statusLabel.setText("Pedido #" + activeOrder.getNumeroPedido() + " aberto. Adicione produtos pelo cardápio.");
        } catch (IllegalArgumentException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void finalizeOrder() {
        if (activeOrder == null) return;
        try {
            restaurante.finalizarPedido(activeOrder.getNumeroPedido());
            renderOrder();
            renderProducts();
            statusLabel.setText("Pedido #" + activeOrder.getNumeroPedido() + " finalizado.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    private void showAddItem(Produto produto) {
        showQuantityDialog(produto, null);
    }

    private void showEditQuantity(ItemPedido item) {
        showQuantityDialog(item.getProduto(), item);
    }

    private void showQuantityDialog(Produto produto, ItemPedido editing) {
        quantityDialog.show(owner(), activeOrder, produto, editing, this::renderOrder, statusLabel::setText);
    }

    private void removeItem(ItemPedido item) {
        if (activeOrder == null) return;
        try {
            restaurante.removerItemDoPedido(activeOrder.getNumeroPedido(), item);
            renderOrder();
            statusLabel.setText("Item removido do pedido.");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            statusLabel.setText(exception.getMessage());
        }
    }

    @FXML
    private void showOrders() {
        ordersDialog.show(owner(), activeOrder).ifPresent(pedido -> {
            activeOrder = pedido;
            selectedMesa = pedido.getMesa();
            renderOrder();
            renderProducts();
            statusLabel.setText(pedido.getStatus() == StatusPedido.ABERTO
                    ? "Pedido #" + pedido.getNumeroPedido() + " selecionado para incluir itens."
                    : "Pedido #" + pedido.getNumeroPedido() + " finalizado; consulta disponível.");
        });
    }
}
