package com.senai.nucitalanches.nucitalanches.ui;

import com.senai.nucitalanches.nucitalanches.application.Restaurante;
import com.senai.nucitalanches.nucitalanches.domain.Mesa;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.util.Optional;

final class TableDialogController {
    private final Restaurante restaurante;
    private final Button menuNav;
    private final Button productsNav;
    private final Button tablesNav;
    private final Button ordersNav;

    TableDialogController(Restaurante restaurante, Button menuNav, Button productsNav,
                          Button tablesNav, Button ordersNav) {
        this.restaurante = restaurante;
        this.menuNav = menuNav;
        this.productsNav = productsNav;
        this.tablesNav = tablesNav;
        this.ordersNav = ordersNav;
    }

    Optional<Mesa> show(Window owner, Mesa selectedMesa) {
        Dialog<Mesa> dialog = UiSupport.dialog("Mesas", owner, menuNav, productsNav, tablesNav, ordersNav);
        dialog.getDialogPane().setId("tablesDialog");
        ButtonType select = new ButtonType("Usar no próximo pedido", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(select, ButtonType.CANCEL);
        ListView<Mesa> list = new ListView<>();
        list.setId("tablesList");
        UiSupport.configureList(list, mesa -> UiSupport.recordRow(
                UiSupport.label(String.format("%02d", mesa.getNumero()), "record-number"),
                UiSupport.mesaName(mesa), "Pronta para receber um pedido", UiIcons.icon("arrow")));
        list.getItems().setAll(restaurante.listarMesas());
        list.setPlaceholder(new Label("Nenhuma mesa cadastrada."));
        if (selectedMesa != null) list.getSelectionModel().select(selectedMesa);

        TextField number = new TextField();
        number.setId("tableNumber");
        number.setPromptText("Número da mesa");
        Label feedback = UiSupport.feedback("tableFeedback");
        Button save = new Button("Cadastrar mesa");
        save.setId("saveTable");
        save.getStyleClass().add("hero-button");
        save.setMaxWidth(Double.MAX_VALUE);
        save.setOnAction(event -> UiSupport.action(feedback, () -> {
            Mesa mesa = new Mesa(UiSupport.integer(number.getText(), "Mesa"));
            if (!restaurante.cadastrarMesa(mesa)) {
                throw new IllegalArgumentException("O número deve ser positivo e não pode pertencer a uma mesa já cadastrada.");
            }
            list.getItems().setAll(restaurante.listarMesas());
            list.getSelectionModel().select(mesa);
            feedback.setText(UiSupport.mesaName(mesa) + " cadastrada. Selecione Usar no próximo pedido para continuar.");
            number.clear();
        }));

        Button find = new Button("Consultar número");
        find.setId("findTable");
        find.getStyleClass().add("secondary-button");
        find.setMaxWidth(Double.MAX_VALUE);
        find.setOnAction(event -> UiSupport.action(feedback, () -> {
            Mesa mesa = restaurante.consultarMesaPorNumero(UiSupport.integer(number.getText(), "Mesa"));
            if (mesa == null) throw new IllegalArgumentException("Mesa não encontrada.");
            list.getSelectionModel().select(mesa);
            list.scrollTo(mesa);
            feedback.setText(UiSupport.mesaName(mesa) + " encontrada.");
        }));

        Button selectButton = (Button) dialog.getDialogPane().lookupButton(select);
        selectButton.setId("selectTable");
        selectButton.disableProperty().bind(list.getSelectionModel().selectedItemProperty().isNull());
        dialog.setResultConverter(button -> button == select ? list.getSelectionModel().getSelectedItem() : null);
        VBox left = new VBox(10, UiSupport.label("Escolha uma mesa", "panel-heading"), list,
                UiSupport.label("Selecione na lista para usar no próximo pedido.", "form-hint"));
        VBox right = UiSupport.form(UiSupport.label("Organize o atendimento", "panel-heading"),
                UiSupport.label("Cadastre uma nova mesa ou encontre uma pelo número.", "form-hint"),
                UiSupport.field("Número da mesa", number),
                UiSupport.label("Use um número inteiro, positivo e ainda não cadastrado.", "form-hint"),
                save, find, feedback);
        dialog.getDialogPane().setContent(UiSupport.splitContent(left, right));
        return dialog.showAndWait();
    }
}
