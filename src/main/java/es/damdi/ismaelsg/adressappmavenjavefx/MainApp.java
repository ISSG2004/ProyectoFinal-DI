package es.damdi.ismaelsg.adressappmavenjavefx;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

import es.damdi.ismaelsg.adressappmavenjavefx.charts.DonutTileChart;
import es.damdi.ismaelsg.adressappmavenjavefx.controller.PersonEditDialogController;
import es.damdi.ismaelsg.adressappmavenjavefx.controller.PersonOverviewController;
import es.damdi.ismaelsg.adressappmavenjavefx.controller.RootLayoutController;
import es.damdi.ismaelsg.adressappmavenjavefx.documentacion.HelpViewer;
import es.damdi.ismaelsg.adressappmavenjavefx.documentacion.MarkDown;
import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

public class MainApp extends Application {

    public MainApp() {
        loadLastSavedPersonData();
    }

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp Ismael Sánchez González");
        this.primaryStage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));


        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

            // scene.getStylesheets().add("css/modena_mod.css");
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            //scene.getStylesheets().add("./css/modena_mod.css");
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }



    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("AddressApp");
        }
    }

    public void savePersonDataToFile(File file) {
        JSONSerializer serializer = new JSONSerializer();

        // Convertimos personData en una lista de objetos simples
        List<Map<String, Object>> simplePersonList = new ArrayList<>();
        for (Person p : personData) {
            Map<String, Object> personMap = new HashMap<>();
            personMap.put("firstName", p.getFirstName());
            personMap.put("lastName", p.getLastName());
            personMap.put("street", p.getStreet());
            personMap.put("postalCode", p.getPostalCode());
            personMap.put("city", p.getCity());
            personMap.put("birthday", p.getBirthday());
            simplePersonList.add(personMap);
        }

        try (FileWriter writer = new FileWriter(file)) {
            String json = serializer.exclude("*.class").serialize(simplePersonList);
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadPersonDataFromFile(File file) {
        JSONDeserializer<List<Map<String, Object>>> deserializer = new JSONDeserializer<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Map<String, Object>> loadedPersons = deserializer.deserialize(reader);

            personData.clear();
            for (Map<String, Object> personMap : loadedPersons) {
                Person p = new Person();
                p.setFirstName((String) personMap.get("firstName"));
                p.setLastName((String) personMap.get("lastName"));
                p.setStreet((String) personMap.get("street"));
                p.setPostalCode(((Number) personMap.get("postalCode")).intValue());
                p.setCity((String) personMap.get("city"));
                p.setBirthday(((Number) personMap.get("birthday")).intValue());
                personData.add(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Configura la extensión del archivo
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos JSON (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra el diálogo de guardar archivo
        File file = fileChooser.showSaveDialog(primaryStage);

        if (file != null) {
            // Asegura que el archivo tenga la extensión correcta
            if (!file.getPath().endsWith(".json")) {
                file = new File(file.getPath() + ".json");
            }
            savePersonDataToFile(file);
        }
    }

    public void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Configura la extensión del archivo
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos JSON (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Muestra el diálogo de abrir archivo
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            loadPersonDataFromFile(file);
        }
    }
    public void showMarkdown(){
        try {
            MarkDown markdown = new MarkDown();
            markdown.start(new Stage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showWeb(){
        try {
            HelpViewer helpViewer = new HelpViewer();
            helpViewer.start(new Stage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void showDonutChartTile() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/DonutChartTile.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("DonutChart- Generaciones");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DonutTileChart controller = loader.getController();
            controller.setMainApp(this);

            dialogStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadLastSavedPersonData() {
        File file = getPersonFilePath();
        if (file != null && file.exists()) {
            loadPersonDataFromFile(file);
        } else {
            // Si no hay archivo guardado, se puede dejar vacío o agregar datos de ejemplo opcionalmente
            personData.add(new Person("Hans", "Muster"));
            personData.add(new Person("Ruth", "Mueller"));
            personData.add(new Person("Heinz", "Kurz"));
            personData.add(new Person("Cornelia", "Meier"));
            personData.add(new Person("Werner", "Meyer"));
            personData.add(new Person("Lydia", "Kunz"));
            personData.add(new Person("Anna", "Best"));
            personData.add(new Person("Stefan", "Meier"));
            personData.add(new Person("Martin", "Mueller"));
        }
    }

}