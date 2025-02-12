module es.damdi.ismaelsg.adressappmavenjavefx {
    requires javafx.controls;
    requires javafx.fxml;

    opens es.damdi.ismaelsg.adressappmavenjavefx to javafx.fxml;
    opens es.damdi.ismaelsg.adressappmavenjavefx.controller;
    opens es.damdi.ismaelsg.adressappmavenjavefx.model;
    exports es.damdi.ismaelsg.adressappmavenjavefx;
}