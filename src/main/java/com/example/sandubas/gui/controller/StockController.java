package com.example.sandubas.gui.controller;

import com.example.sandubas.Main;
import com.example.sandubas.gui.listener.DataChangeListener;
import com.example.sandubas.gui.util.Alerts;
import com.example.sandubas.gui.util.Utils;
import com.example.sandubas.model.Product;
import com.example.sandubas.services.remote.ProductService;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
    private TableColumn<Product, Product> tableColumnEDIT;
    @FXML
    private TableColumn<Product, Product> tableColumnREMOVE;

    @FXML
    private Label labelQuantityItems;
    @FXML
    private TextField filterField;

    private ProductService productService;
    private ObservableList<Product> products;
    private FilteredList<Product> filteredList;

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
        tableViewProduct.setTableMenuButtonVisible(false);
    }

    public void updateTableView() throws IOException, InterruptedException {
        if (productService == null) {
            throw new IllegalStateException("Service was null");
        }
        List<Product> list = productService.findAll();
        products = FXCollections.observableArrayList(list);

        filteredList = new FilteredList<>(products, b -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(product -> {
                if (newValue.isEmpty() || newValue.isBlank()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (product.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                }  else if (product.getId().toString().contains(searchKeyword)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<Product> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableViewProduct.comparatorProperty());
        tableViewProduct.setItems(sortedList);
        labelQuantityItems.setText(products.size() + " itens cadastrados");
        initEditButtons(this);
        initRemoveButtons();
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    private void initEditButtons(DataChangeListener listener) {
        tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnEDIT.setCellFactory(param -> new TableCell<Product, Product>() {
            private final Button button = new Button("EDITAR");

            @Override
            protected void updateItem(Product obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> {
                    try {
                        Main.loadDialogForm(listener, obj, "productRegisterForm.fxml", Utils.currentStage(event));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }

    private void initRemoveButtons() {
        tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tableColumnREMOVE.setCellFactory(param -> new TableCell<Product, Product>() {
            private final Button button = new Button("EXCLUIR");

            @Override
            protected void updateItem(Product obj, boolean empty) {
                super.updateItem(obj, empty);
                if (obj == null) {
                    setGraphic(null);
                    return;
                }
                setGraphic(button);
                button.setOnAction(event -> removeEntity(obj));
            }
        });
    }

    private void removeEntity(Product obj) {
        Optional<ButtonType> result = Alerts.showConfirmation("Confirmação", "Tem certeza que deseja deletar o produto " + obj.getName() + "?");

        if (result.get() == ButtonType.OK) {
            try {
                productService.remove(obj);
                updateTableView();
            } catch (Exception e) {
                Alerts.showAlert("Erro ao remover um produto", null, e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
