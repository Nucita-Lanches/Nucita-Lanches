package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.application.Restaurante;
import com.senai.nucitalanches.nucitalanches.domain.ItemPedido;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.Produto;
import com.senai.nucitalanches.nucitalanches.domain.StatusPedido;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Window;

import java.util.Objects;
import java.util.function.Consumer;

final class QuantityDialogController {
    private final Restaurante restaurante;
    private final Button menuNav;
    private final Button productsNav;
    private final Button tablesNav;
    private final Button ordersNav;

    QuantityDialogController(Restaurante restaurante, Button menuNav, Button productsNav,
                             Button tablesNav, Button ordersNav) {
        this.restaurante = restaurante;
        this.menuNav = menuNav;
        this.productsNav = productsNav;
        this.tablesNav = tablesNav;
        this.ordersNav = ordersNav;
    }

    void show(Window owner, Pedido activeOrder, Produto produto, ItemPedido editing,
              Runnable refreshOrder, Consumer<String> setStatus) {
        if (activeOrder == null || activeOrder.getStatus() != StatusPedido.ABERTO) return;
        Dialog<Void> dialog = UiSupport.dialog(editing == null ? "Adicionar item" : "Alterar quantidade",
                owner, menuNav, productsNav, tablesNav, ordersNav);
        dialog.getDialogPane().setId(editing == null ? "addItemDialog" : "editItemDialog");
        ButtonType confirm = new ButtonType(editing == null ? "Adicionar ao pedido" : "Salvar quantidade",
                ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirm, ButtonType.CANCEL);
        TextField quantity = new TextField(editing == null ? "1" : editing.getQuantidade().toString());
        quantity.setId("itemQuantity");
        Label feedback = UiSupport.feedback("itemFeedback");
        double priceInOrder = activeOrder.getItens().stream()
                .filter(item -> Objects.equals(item.getProduto().getId(), produto.getId()))
                .mapToDouble(ItemPedido::getPrecoUnitario)
                .findFirst().orElse(produto.getPreco());
        HBox summary = UiSupport.recordRow(ProductVisuals.createMarker(produto, "record-number"),
                produto.getNome(), "Preço por unidade neste pedido",
                UiSupport.label(UiSupport.money(priceInOrder), "record-value"));
        dialog.getDialogPane().setContent(UiSupport.content(summary,
                UiSupport.label("Pedido #" + activeOrder.getNumeroPedido() + " · "
                        + UiSupport.mesaName(activeOrder.getMesa()), "form-hint"),
                UiSupport.field("Quantas unidades vamos servir?", quantity), feedback));
        Node confirmButton = dialog.getDialogPane().lookupButton(confirm);
        confirmButton.setId("saveItem");
        confirmButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                int value = UiSupport.integer(quantity.getText(), "Quantidade");
                if (editing == null) {
                    restaurante.adicionarItemAoPedido(activeOrder.getNumeroPedido(), produto, value);
                } else {
                    restaurante.alterarQuantidadeItem(activeOrder.getNumeroPedido(), editing, value);
                }
                refreshOrder.run();
                setStatus.accept(editing == null
                        ? produto.getNome() + " adicionado ao pedido #" + activeOrder.getNumeroPedido() + "."
                        : "Quantidade atualizada.");
            } catch (IllegalArgumentException exception) {
                feedback.getStyleClass().remove("feedback-error");
                feedback.getStyleClass().add("feedback-error");
                feedback.setText(exception.getMessage());
                event.consume();
            }
        });
        dialog.showAndWait();
    }
}
