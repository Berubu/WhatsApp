module isa.whatsapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens isa.whatsapp to javafx.fxml;
    exports isa.whatsapp;
    exports isa.whatsapp.server;
    opens isa.whatsapp.server to javafx.fxml;
}