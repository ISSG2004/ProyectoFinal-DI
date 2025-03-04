package es.damdi.ismaelsg.adressappmavenjavefx.controller;


import es.damdi.ismaelsg.adressappmavenjavefx.MainApp;
import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;

import java.io.File;
import java.util.List;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;

public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Creates an empty address book.
     */
    @FXML
    private void handleNew() {
        if (mainApp != null) {
            mainApp.getPersonData().clear();
            mainApp.setPersonFilePath(null);
        }
    }

    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    private void handleOpen() {
        if (mainApp == null) {
            System.err.println("MainApp is not initialized.");
            return;
        }

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Saves the file to the person file that is currently open. If there is no
     * open file, the "save as" dialog is shown.
     */
    @FXML
    private void handleSave() {
        if (mainApp == null) {
            System.err.println("MainApp is not initialized.");
            return;
        }

        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
        if (mainApp == null) {
            System.err.println("MainApp is not initialized.");
            return;
        }

        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            mainApp.savePersonDataToFile(file);
        }
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        System.out.println("Author: Marco Jakob\nWebsite: http://code.makery.ch");
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
