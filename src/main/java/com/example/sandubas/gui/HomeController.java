package com.example.sandubas.gui;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView openSideBar, closeSideBar;

    @FXML
    private AnchorPane drawerPane;

    @FXML
    private Button btStock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), drawerPane);
        translateTransition.setByX(-600);
        translateTransition.play();

        openSideBar.setOnMouseClicked(event -> {
            openSideBar.setDisable(true);
            openSideBar.setVisible(false);
            transition(600);

        });

        closeSideBar.setOnMouseClicked(event -> {
            closeSideBar.setDisable(true);
            closeSideBar.setVisible(false);
            transition(-600);
        });
    }


    @FXML
    private void onBtStockAction() {
        System.out.println("Cliquei");
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

}
