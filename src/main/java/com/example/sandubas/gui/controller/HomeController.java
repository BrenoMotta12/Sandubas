package com.example.sandubas.gui.controller;

import com.example.sandubas.Main;
import com.example.sandubas.gui.util.Alerts;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView openSideBar, closeSideBar;

    @FXML
    private AnchorPane drawerPane, paneScreens;

    @FXML
    private Button btPaneStock, btPaneReports;

    @FXML
    private ImageView iconReports;

    @FXML
    private ImageView iconStock;

    @FXML
    private ImageView imageWelcome;

    // Carregar as imagens
    private final Image stockIcon = new Image(getClass().getResourceAsStream("/com/example/sandubas/images/ic_stock.png"));
    private final Image stockIconSelected = new Image(getClass().getResourceAsStream("/com/example/sandubas/images/ic_stock_selected.png"));
    private final Image reportsIcon = new Image(getClass().getResourceAsStream("/com/example/sandubas/images/ic_reports.png"));
    private final Image reportsIconSelected = new Image(getClass().getResourceAsStream("/com/example/sandubas/images/ic_reports_selected.png"));

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Definir ícones padrão
        iconStock.setImage(stockIcon);
        iconReports.setImage(reportsIcon);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();

        openSideBar.setOnMouseClicked(event -> {
            openSideBar.setDisable(true);
            openSideBar.setVisible(false);
            transition(600);
        });

        closeSideBar.setOnMouseClicked(event -> {
            closeSideBar();
        });

        btPaneStock.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                try {
                    Main.loadRootPaneStock();
                    paneScreens.getChildren().clear();
                    paneScreens.getChildren().add(Main.getRootPaneStock());
                    closeSideBar();
                    updateIconSelection(iconStock);
                    imageWelcome.setVisible(false);
                } catch (IOException ex) {
                    Alerts.showAlert("IO Exception", "Error loading view", ex.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });

        btPaneReports.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 1) {
                try {
                    Main.loadRootPaneReports();
                    paneScreens.getChildren().clear();
                    paneScreens.getChildren().add(Main.getRootPaneReports());
                    closeSideBar();
                    updateIconSelection(iconReports);
                    imageWelcome.setVisible(false);
                } catch (IOException ex) {
                    Alerts.showAlert("IO Exception", "Error loading view", ex.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void closeSideBar() {
        closeSideBar.setDisable(true);
        closeSideBar.setVisible(false);
        transition(-600);
    }

    private void transition(int direction) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), drawerPane);
        translateTransition.setByX(direction);
        translateTransition.play();

        if (direction > 0) {
            translateTransition.setOnFinished(e -> {
                closeSideBar.setDisable(false);
            });
            closeSideBar.setVisible(true);
        } else {
            translateTransition.setOnFinished(e -> {
                openSideBar.setDisable(false);
            });
            openSideBar.setVisible(true);
        }
    }

    private void updateIconSelection(ImageView selectedIcon) {
        if (selectedIcon == iconStock) {
            iconStock.setImage(stockIconSelected);
            iconReports.setImage(reportsIcon);
        } else if (selectedIcon == iconReports) {
            iconStock.setImage(stockIcon);
            iconReports.setImage(reportsIconSelected);
        }
    }
}
