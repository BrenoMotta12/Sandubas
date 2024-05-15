package com.example.sandubas;

import com.example.sandubas.gui.controller.ProductRegisterFormController;
import com.example.sandubas.gui.controller.StockController;
import com.example.sandubas.gui.listener.DataChangeListener;
import com.example.sandubas.gui.util.Alerts;
import com.example.sandubas.model.Product;
import com.example.sandubas.services.remote.ProductService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    static double x;
    static double y;
    private static Scene mainScene;
    private static Parent rootPaneStock;
    private static Parent rootPaneReports;

    public static Parent getRootPaneStock() {
        return rootPaneStock;
    }

    public static void setRootPaneStock(Parent rootPaneStock) {
        Main.rootPaneStock = rootPaneStock;
    }

    public static Parent getRootPaneReports() {
        return rootPaneReports;
    }

    public static void setRootPaneReports(Parent rootPaneReports) {
        Main.rootPaneReports = rootPaneReports;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("home.fxml"));
        fxmlLoader.setRoot(new AnchorPane());
        mainScene = new Scene(fxmlLoader.load());
        stage.setMaximized(true);
        stage.setTitle("Sandubas");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void loadRootPaneStock() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("stockView.fxml"));
            fxmlLoader.setRoot(new VBox());
            rootPaneStock = fxmlLoader.load();
            StockController controller = fxmlLoader.getController();
            controller.setProductService(new ProductService());
            controller.updateTableView();

        } catch (IOException ex) {
            Alerts.showAlert("IO Exception", "Error loading stock view", ex.getMessage(), Alert.AlertType.ERROR);
        } catch (InterruptedException ex) {
            Alerts.showAlert("Interrupted Exception", "Error loading stock view", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public static void loadRootPaneReports() throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("reportsView.fxml"));
            rootPaneReports = fxmlLoader.load();

        } catch (IOException ex) {
            Alerts.showAlert("IO Exception", "Error loading view", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public static void loadDialogForm(DataChangeListener listener, Product product, String absoluteName, Stage parentStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(absoluteName));
        Pane pane = fxmlLoader.load();
        Stage dialogStage = new Stage();

        pane.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        pane.setOnMouseDragged(event -> {
            dialogStage.setX(event.getScreenX() - x);
            dialogStage.setY(event.getScreenY() - y);
        });

        ProductRegisterFormController controller = fxmlLoader.getController();
        controller.setProduct(product);
        controller.subscribeDataChangeListener(listener);
        controller.updateFormData();
        controller.setProductService(new ProductService());

        dialogStage.setTitle("Novo Produto");
        dialogStage.setScene(new Scene(pane));
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.setResizable(false);
        dialogStage.initOwner(parentStage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.showAndWait();
    }

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        launch();
    }
}