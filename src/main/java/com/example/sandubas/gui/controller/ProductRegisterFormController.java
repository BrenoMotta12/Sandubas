package com.example.sandubas.gui.controller;

import com.example.sandubas.gui.listener.DataChangeListener;
import com.example.sandubas.gui.util.Constraints;
import com.example.sandubas.gui.util.Utils;
import com.example.sandubas.model.Product;
import com.example.sandubas.model.exceptions.ValidationException;
import com.example.sandubas.services.remote.ProductService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ProductRegisterFormController implements Initializable {

    private Product entity;
    private ProductService service;
    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private Label labelTitle;
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
    private void onBtSaveAction(ActionEvent event) throws IOException, InterruptedException {
        try {
            entity = getFormData();
            service.saveOrUpdate(entity);
            notifyDataChangeListeners();
            Utils.currentStage(event).close();

        } catch (ValidationException ex) {
            setErrorMessages(ex.getErrors());
        }
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
        txtId.setDisable(true);


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

    public void subscribeDataChangeListener(DataChangeListener listener) {
        dataChangeListeners.add(listener);
    }

    public void updateFormData() {
        if (entity.getId() != null) {
            labelTitle.setText("Edição do Produto");
        } else {
            labelTitle.setText("Novo Produto");
        }
        if (entity == null) {
            throw new IllegalStateException("Entity was null");
        } else {
            if (entity.getId() != null) {
                txtId.setText(String.valueOf(entity.getId()));
            }
            txtName.setText(entity.getName());
            txtDescription.setText(entity.getDescription());
            txtPrice.setText(String.valueOf(entity.getPrice()));
            txtQuantity.setText(String.valueOf(entity.getQuantity()));
        }

    }

    private Product getFormData() {
        ValidationException exception= new ValidationException("Validation error");
        Product formData = new Product();

        if (txtName.getText() == null || txtName.getText().trim().equals("")) {
            exception.addError("name", "O campo não pode ser vazio.");
        } else {
            formData.setName(txtName.getText());
        }
        formData.setId(Utils.tryParseToInt(txtId.getText()));
        formData.setDescription(txtDescription.getText());
        formData.setPrice(Utils.tryParseToDouble(txtPrice.getText()));
        formData.setQuantity(Utils.tryParseToInt(txtQuantity.getText()));

        if (exception.getErrors().size() > 0) {
            throw exception;
        }

        return formData;
    }

    private void notifyDataChangeListeners() throws IOException, InterruptedException {
        for (DataChangeListener listener : dataChangeListeners) {
            listener.onDataChanged();
        }
    }

    private void setErrorMessages(Map<String, String> errors) {
        Set<String> fields = errors.keySet();

        if(fields.contains("name")) {
            labelErrorName.setText(errors.get("name"));
        }
    }
}
