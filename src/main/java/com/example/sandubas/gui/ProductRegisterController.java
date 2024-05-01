package com.example.sandubas.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductRegisterController implements Initializable {

    // Inputs
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextField txtQuantity;
    @FXML
    private TextField txtPrice;

    // Mensagens de erro
    @FXML
    private Label labelErrorName;
    @FXML
    private Label labelErrorDescription;
    @FXML
    private Label labelErrorQuantity;
    @FXML
    private Label labelErrorPrice;

    // Botões salvar e cancelas
    @FXML
    private Button btSave;
    @FXML Button btCancel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void initializeNodes() {
        Constraints
    }
}
