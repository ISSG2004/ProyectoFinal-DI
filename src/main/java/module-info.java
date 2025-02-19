module es.damdi.ismaelsg.adressappmavenjavefx {
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.controls;
    requires java.prefs;
    requires flexjson;

    opens es.damdi.ismaelsg.adressappmavenjavefx to javafx.fxml;
    opens es.damdi.ismaelsg.adressappmavenjavefx.controller;
    opens es.damdi.ismaelsg.adressappmavenjavefx.model;
    exports es.damdi.ismaelsg.adressappmavenjavefx;
}