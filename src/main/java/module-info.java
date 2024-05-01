module com.example.sandubas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.example.sandubas to javafx.fxml;
    exports com.example.sandubas;
    exports com.example.sandubas.gui;
    exports com.example.sandubas.model;
    opens com.example.sandubas.gui to javafx.fxml;
    opens com.example.sandubas.model to javafx.base;
}