module es.damdi.ismaelsg.adressappmavenjavefx {
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;
    requires java.prefs;
    requires flexjson;
    requires flexmark;
    requires flexmark.util.ast;
    requires javafx.web;
    requires PDFViewerFX;

    opens es.damdi.ismaelsg.adressappmavenjavefx to javafx.fxml;
    opens es.damdi.ismaelsg.adressappmavenjavefx.controller;
    opens es.damdi.ismaelsg.adressappmavenjavefx.model;
    exports es.damdi.ismaelsg.adressappmavenjavefx;
}