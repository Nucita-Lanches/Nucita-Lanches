package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.domain.ItemPedido;
import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.StatusPedido;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

final class OrderPanelController {
    private final VBox cartItems;
    private final Label countLabel;
    private final Label tableLabel;
    private final Label orderTitle;
    private final Label nextTableLabel;
    private final Label orderStatusLabel;
    private final Label totalLabel;
    private final Button newOrderButton;
    private final Button finalizeOrderButton;
    private final Consumer<ItemPedido> editItem;
    private final Consumer<ItemPedido> removeItem;

    OrderPanelController(VBox cartItems, Label countLabel, Label tableLabel, Label orderTitle,
                         Label nextTableLabel, Label orderStatusLabel, Label totalLabel,
                         Button newOrderButton, Button finalizeOrderButton,
                         Consumer<ItemPedido> editItem, Consumer<ItemPedido> removeItem) {
        this.cartItems = cartItems;
        this.countLabel = countLabel;
        this.tableLabel = tableLabel;
        this.orderTitle = orderTitle;
        this.nextTableLabel = nextTableLabel;
        this.orderStatusLabel = orderStatusLabel;
        this.totalLabel = totalLabel;
        this.newOrderButton = newOrderButton;
        this.finalizeOrderButton = finalizeOrderButton;
        this.editItem = editItem;
        this.removeItem = removeItem;
    }

    void render(Pedido activeOrder, Mesa selectedMesa) {
        cartItems.getChildren().clear();
        orderTitle.setText(activeOrder == null ? "Seu pedido" : "Pedido #" + activeOrder.getNumeroPedido());
        tableLabel.setText(activeOrder == null ? "Nenhum pedido aberto" : UiSupport.mesaName(activeOrder.getMesa()));
        nextTableLabel.setText(selectedMesa == null ? "Selecione uma mesa para criar um pedido."
                : "Próximo pedido: " + UiSupport.mesaName(selectedMesa));
        newOrderButton.setDisable(selectedMesa == null);
        boolean orderOpen = activeOrder != null && activeOrder.getStatus() == StatusPedido.ABERTO;
        orderStatusLabel.setText(activeOrder == null ? "Aguardando" : orderOpen ? "Aberto" : "Finalizado");
        orderStatusLabel.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), orderOpen);
        finalizeOrderButton.setDisable(!orderOpen);

        long count = 0;
        if (activeOrder != null) {
            for (ItemPedido item : activeOrder.getItens()) {
                count += item.getQuantidade();
                Node marker = ProductVisuals.createMarker(item.getProduto(), "item-avatar");
                VBox text = new VBox(6,
                        UiSupport.label(item.getProduto().getNome(), "cart-item-name"),
                        UiSupport.label(UiSupport.money(item.getPrecoUnitario()) + " / un.", "cart-price"));
                HBox.setHgrow(text, Priority.ALWAYS);
                Label quantity = UiSupport.label(item.getQuantidade() + "×", "item-quantity");
                HBox line = new HBox(10, marker, text, quantity);
                line.setAlignment(Pos.CENTER_LEFT);

                Label subtotal = UiSupport.label(UiSupport.money(item.getSubtotal()), "item-subtotal");
                subtotal.setId("itemSubtotal-" + item.getProduto().getId());
                Button edit = new Button("Alterar quantidade");
                edit.setId("editItem-" + item.getProduto().getId());
                edit.getStyleClass().add("item-control");
                edit.setDisable(!orderOpen);
                edit.setOnAction(event -> editItem.accept(item));
                Button remove = new Button("Remover");
                remove.setId("removeItem-" + item.getProduto().getId());
                remove.getStyleClass().add("item-control");
                remove.setDisable(!orderOpen);
                remove.setOnAction(event -> removeItem.accept(item));

                HBox values = new HBox(UiSupport.label("Subtotal", "cart-price"), UiSupport.spacer(), subtotal);
                values.setAlignment(Pos.CENTER_LEFT);
                HBox controls = new HBox(12, edit, remove);
                VBox row = new VBox(8, line, values, controls);
                row.getStyleClass().add("order-item");
                cartItems.getChildren().add(row);
            }
        }

        if (cartItems.getChildren().isEmpty()) {
            StackPane symbol = new StackPane(UiIcons.icon("orders"));
            symbol.getStyleClass().add("empty-symbol");
            symbol.setMaxSize(54, 54);
            symbol.setMinSize(54, 54);
            VBox empty = new VBox(symbol,
                    UiSupport.label(activeOrder == null ? "Tudo começa na mesa." : "Uma boa escolha vem aí.", "order-empty-title"),
                    UiSupport.label(activeOrder == null
                            ? "Selecione uma mesa e abra um novo pedido para começar."
                            : "Escolha um produto no cardápio e adicione ao pedido.", "order-empty-copy"));
            empty.getStyleClass().add("order-empty");
            cartItems.getChildren().add(empty);
        }

        countLabel.setText(Long.toString(count));
        totalLabel.setText(UiSupport.money(activeOrder == null ? 0 : activeOrder.getTotal()));
    }
}
