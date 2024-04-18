package com.example.sandubas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
//    double x, y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 980);
//        stage.initStyle(StageStyle.UNDECORATED);
//        scene.setOnMousePressed(mouseEvent -> {
//            x = mouseEvent.getSceneX();
//            y = mouseEvent.getSceneY();
//        });
//        scene.setOnMouseDragged( mouseEvent -> {
//            stage.setX(mouseEvent.getScreenX() - x);
//            stage.setY(mouseEvent.getScreenY() - y);
//        });


        stage.setFullScreen(true);
        stage.setTitle("Sandubas");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    public static void main(String[] args) {
        launch();
    }
}