package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.domain.ItemPedido;
import com.senai.nucitalanches.nucitalanches.domain.Pedido;
import com.senai.nucitalanches.nucitalanches.domain.StatusPedido;
import com.senai.nucitalanches.nucitalanches.storage.BancoDeDados;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.util.Optional;

final class OrdersDialogController {
    private final Button menuNav;
    private final Button productsNav;
    private final Button tablesNav;
    private final Button ordersNav;

    OrdersDialogController(Button menuNav, Button productsNav, Button tablesNav, Button ordersNav) {
        this.menuNav = menuNav;
        this.productsNav = productsNav;
        this.tablesNav = tablesNav;
        this.ordersNav = ordersNav;
    }

    Optional<Pedido> show(Window owner, Pedido activeOrder) {
        Dialog<Pedido> dialog = UiSupport.dialog("Pedidos", owner, menuNav, productsNav, tablesNav, ordersNav);
        dialog.getDialogPane().setId("ordersDialog");
        ButtonType select = new ButtonType("Selecionar pedido", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(select, ButtonType.CANCEL);
        ListView<Pedido> list = new ListView<>();
        list.setId("ordersList");
        UiSupport.configureList(list, pedido -> UiSupport.recordRow(UiIcons.icon("orders"),
                "Pedido #" + pedido.getNumeroPedido(), UiSupport.mesaName(pedido.getMesa()),
                UiSupport.label(pedido.getStatus() == StatusPedido.ABERTO ? "Aberto" : "Finalizado", "record-state")));
        list.getItems().setAll(BancoDeDados.getInstancia().listarPedidos());
        list.setPlaceholder(new Label("Nenhum pedido criado nesta sessão."));

        TextArea details = new TextArea();
        details.setId("orderDetails");
        details.setEditable(false);
        details.setWrapText(true);
        details.setPrefRowCount(7);
        list.getSelectionModel().selectedItemProperty().addListener((obs, old, pedido) ->
                details.setText(pedido == null ? "" : describe(pedido)));

        Button selectButton = (Button) dialog.getDialogPane().lookupButton(select);
        selectButton.setId("selectOrder");
        selectButton.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        dialog.setResultConverter(button -> button == select ? list.getSelectionModel().getSelectedItem() : null);
        details.setPrefWidth(290);
        details.setMinWidth(0);
        VBox summary = UiSupport.form(UiSupport.label("Resumo do pedido", "panel-heading"), details);
        VBox.setVgrow(details, javafx.scene.layout.Priority.ALWAYS);
        summary.setMinHeight(340);
        VBox left = new VBox(10, UiSupport.label("Pedidos desta sessão", "panel-heading"), list,
                UiSupport.label("Selecione um pedido para consultar ou continuar se ele estiver aberto.", "form-hint"));
        dialog.getDialogPane().setContent(UiSupport.splitContent(left, summary));
        if (activeOrder != null) list.getSelectionModel().select(activeOrder);
        return dialog.showAndWait();
    }

    private String describe(Pedido pedido) {
        StringBuilder text = new StringBuilder("Pedido #" + pedido.getNumeroPedido() + " • "
                + UiSupport.mesaName(pedido.getMesa()) + "\nStatus: " + pedido.getStatus() + "\n\n");
        if (pedido.getItens().isEmpty()) text.append("Nenhum item adicionado.");
        for (ItemPedido item : pedido.getItens()) {
            text.append(item.getQuantidade()).append(" × ").append(item.getProduto().getNome())
                    .append(" • preço unitário: ").append(UiSupport.money(item.getPrecoUnitario()))
                    .append(" • subtotal: ").append(UiSupport.money(item.getSubtotal())).append('\n');
        }
        return text.append("\nTotal: ").append(UiSupport.money(pedido.getTotal())).toString();
    }
}
