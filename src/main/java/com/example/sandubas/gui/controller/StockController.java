package com.example.sandubas.gui.controller;

import com.example.sandubas.Main;
import com.example.sandubas.gui.listener.DataChangeListener;
import com.example.sandubas.gui.util.Alerts;
import com.example.sandubas.gui.util.Utils;
import com.example.sandubas.model.Product;
import com.example.sandubas.services.remote.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class StockController implements Initializable, DataChangeListener {

    @FXML
    private Button btNewProduct;
    @FXML
    private TableView<Product> tableViewProduct;
    @FXML
    private TableColumn<Product, String> tableColumnName;
    @FXML
    private TableColumn<Product, Integer> tableColumnId;
    @FXML
    private TableColumn<Product, String> tableColumnDescription;
    @FXML
    private TableColumn<Product, Double> tableColumnQuantity;
    @FXML
    private TableColumn<Product, Double> tableColumnPrice;
    @FXML
    private Label labelQuantityItems;

    private ProductService productService;
    private ObservableList<Product> products;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    @Override
    public void onDataChanged() throws IOException, InterruptedException {
        updateTableView();
    }
    @FXML
    public void onBtNewProduct(ActionEvent event) {
        try {
            Stage parentStage = Utils.currentStage(event);
            Product product = new Product();
            Main.loadDialogForm(this, product, "productRegisterForm.fxml", parentStage);
        } catch (IOException ex) {
            Alerts.showAlert("IO Exception", "Error loading form view", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    private void initializeNodes() {
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumnQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public void updateTableView() throws IOException, InterruptedException {
        // METODO PARA ATUALIZAR A TABELA DE PRODUTOS
        if (productService == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Product> list = productService.findAll();
        products = FXCollections.observableArrayList(list);
        tableViewProduct.setItems(products);
        labelQuantityItems.setText(products.size() + " itens cadastrados");
    }
    public void setProductService (ProductService productService) {
        // METODO PARA SETAR A INSTANCIA SERVIÇO QUE BUSCA OS PRODUTOS
        this.productService = productService;
    }


}
