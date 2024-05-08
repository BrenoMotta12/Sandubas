package com.example.sandubas.gui;

import com.example.sandubas.gui.util.Constraints;
import com.example.sandubas.gui.util.Utils;
import com.example.sandubas.model.Product;
import com.example.sandubas.services.remote.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.text.Normalizer;
import java.util.ResourceBundle;

public class ProductRegisterController implements Initializable {

    private Product entity;
    private ProductService service;

    // Inputs
    @FXML
    private TextField txtId;
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

    // Botões salvar e cancelar
    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;

    @FXML
    private void onBtSaveAction(ActionEvent event) {
        entity = getFormData();
        service.saveOrUpdate(entity);
        Utils.currentStage(event).close();
    }

    @FXML
    private void onBtCancelAction(ActionEvent event) {
        Utils.currentStage(event).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNodes();
    }

    private void initializeNodes() {
        Constraints.setTextFieldInteger(txtQuantity);
        Constraints.setTextFieldDouble(txtPrice);
        Constraints.setTextFieldMaxLength(txtName, 30);
        Constraints.setTextFieldMaxLength(txtDescription, 30);
    }

    public void setProduct(Product entity) {
        this.entity = entity;
    }
    public void setProductService(ProductService service) {
        this.service = service;
    }
    public void updateFormData() {
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        } else {
            txtName.setText(entity.getName());
            txtDescription.setText(entity.getDescription());
            txtPrice.setText(String.valueOf(entity.getPrice()));
            txtQuantity.setText(String.valueOf(entity.getQuantity()));
        }

    }

    private Product getFormData() {
        Product formData = new Product();
        formData.setId(Utils.tryParseToInt(txtId.getText()));
        formData.setName(txtName.getText());
        formData.setDescription(txtDescription.getText());
        formData.setPrice(Utils.tryParseToDouble(txtPrice.getText()));
        formData.setQuantity(Utils.tryParseToInt(txtQuantity.getText()));


        return formData;
    }
}
