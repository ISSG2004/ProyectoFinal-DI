package es.damdi.ismaelsg.adressappmavenjavefx.controller;


import es.damdi.ismaelsg.adressappmavenjavefx.MainApp;
import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;
import es.damdi.ismaelsg.adressappmavenjavefx.util.JsonUtils;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class RootLayoutController {

    // Referencia a la aplicación principal
    private MainApp mainApp;

    /**
     * Es llamado por la aplicación principal para proporcionar una referencia a sí misma.
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Crea un nuevo libro de direcciones vacío.
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Abre un FileChooser para que el usuario seleccione un archivo JSON para cargar.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Establecer filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar diálogo de apertura de archivo
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            List<Person> persons = JsonUtils.loadPersonsFromFile(file);
            if (persons != null) {
                mainApp.setPersonData(persons);
                mainApp.setPersonFilePath(file);
            }
        }
    }

    /**
     * Guarda el archivo en la ubicación actual. Si no hay un archivo abierto, muestra "Guardar como".
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            JsonUtils.savePersonsToFile(mainApp.getPersonData(), personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Abre un FileChooser para que el usuario seleccione un archivo para guardar.
     */
    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Establecer filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar diálogo de guardado
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Asegurar que tiene la extensión correcta
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            JsonUtils.savePersonsToFile(mainApp.getPersonData(), file);
            mainApp.setPersonFilePath(file);
        }
    }

    /**
     * Muestra un diálogo de información sobre la aplicación.
     */
    @FXML
    private void handleAbout() {
        Dialogs.create()
                .title("AddressApp")
                .masthead("Acerca de")
                .message("Autor: Marco Jakob\nSitio web: http://code.makery.ch")
                .showInformation();
    }

    /**
     * Cierra la aplicación.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}