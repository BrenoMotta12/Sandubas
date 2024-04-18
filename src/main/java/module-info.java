module com.example.sandubas {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.sandubas to javafx.fxml;
    exports com.example.sandubas;
    exports com.example.sandubas.gui;
    opens com.example.sandubas.gui to javafx.fxml;
}