package com.example.sandubas.gui.controller;

import com.example.sandubas.gui.util.Alerts;
import com.example.sandubas.model.Product;
import com.example.sandubas.services.remote.ProductService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private BarChart<String, Number> barChartProducts;

    @FXML
    private BarChart<String, Number> barChartTotalValueProducts;

    @FXML
    private PieChart pieChartProductDistribution;

    @FXML
    private NumberAxis yAxisQuantity;

    @FXML
    private NumberAxis yAxisTotalValue;

    private ProductService productService;

    private List<Product> listData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadData();
        initBarChartProducts();
        initBarChartTotalValueProducts();
        initPieChartProductDistribution();
    }

    private void initPieChartProductDistribution() {
        int fewProducts = 0;
        int averageProducts = 0;
        int manyProducts = 0;
        pieChartProductDistribution.getData().clear();
        for (Product product : listData) {
            if (product.getQuantity() < 50) {
                fewProducts++;
            } else if (product.getQuantity() >= 50 && product.getQuantity() <= 100) {
                averageProducts++;
            } else {
                manyProducts++;
            }
        }

        addSlice("Menos de 50 unidades", fewProducts);
        addSlice("Entre 50 e 100 unidades", averageProducts);
        addSlice("Mais de 100 unidades", manyProducts);
    }

    private void addSlice(String title, int value) {
        PieChart.Data slice = new PieChart.Data(title, value);
        pieChartProductDistribution.getData().add(slice);
    }

    private void initBarChartProducts() {
        XYChart.Series<String, Number> dataSeries;
        yAxisQuantity.setLabel("QUANTIDADE");
        dataSeries = new XYChart.Series<>();
        dataSeries.setName("PRODUTOS");
        barChartProducts.getData().add(dataSeries);
        dataSeries.getData().clear();
        for (Product product : listData) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(
                    "ID: " + product.getId() + " - " + product.getName(),
                    product.getQuantity()
            );

            dataSeries.getData().add(data);
        }
    }

    private void initBarChartTotalValueProducts() {
        XYChart.Series<String, Number> dataSeries;
        yAxisTotalValue.setLabel("VALOR TOTAL");
        dataSeries = new XYChart.Series<>();
        dataSeries.setName("PRODUTOS");
        barChartTotalValueProducts.getData().add(dataSeries);
        dataSeries.getData().clear();
        for (Product product : listData) {
            XYChart.Data<String, Number> data = new XYChart.Data<>(
                    "ID: " + product.getId() + " - " + product.getName(),
                    product.getQuantity() * product.getPrice()
            );

            dataSeries.getData().add(data);
        }
    }

    // Método para adicionar dados ao gráfico

    private void loadData() {
        try {
            if (productService == null) {
                productService = new ProductService();
            }
            listData = productService.findAll();
            System.out.println(listData);
        } catch (Exception e) {
            Alerts.showAlert("Erro ao buscar os dados", null, e.getMessage(), Alert.AlertType.ERROR);
        }
    }
//    public void addData(String name, Number quantity) {
//        XYChart.Data<String, Number> data = new XYChart.Data<>(name, quantity);
//        dataSeries.getData().add(data);
//    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
