package es.damdi.ismaelsg.adressappmavenjavefx.controller;


import com.dansoftware.pdfdisplayer.PDFDisplayer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import es.damdi.ismaelsg.adressappmavenjavefx.MainApp;
import es.damdi.ismaelsg.adressappmavenjavefx.charts.GenerationsChart;
import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.scene.chart.*;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

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

            // Guardar la ruta del archivo en las preferencias y actualizar la cabecera
            mainApp.setPersonFilePath(file);
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

            // Guardar la ruta del archivo en las preferencias
            mainApp.setPersonFilePath(file);
        }
    }


    @FXML
    public void handleWeb() {
        mainApp.showWeb();
    }


    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta cerrar esta
        ventanaEmergente.setTitle("About");
        ventanaEmergente.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
        Label lblNombre = new Label("By: Ismael Sánchez González");
        Label lblProyecto = new Label("Proyecto: AddresApp");
        VBox layout = new VBox(10, lblNombre, lblProyecto);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");
        Scene scene = new Scene(layout, 300, 300);

        ventanaEmergente.setScene(scene);
        ventanaEmergente.showAndWait();

    }
    /**
     * Método para cargar y mostrar el README.md en una ventana emergente.
     */
    @FXML
    private void handleDoc() {
        mainApp.showMarkdown();
    }



    /**
     * Método para cargar el pdf
     */
    @FXML
    private void handleLoadPdf() {
        // Crear un nuevo Stage (ventana) para el visor PDF
        Stage pdfWindow = new Stage();
        pdfWindow.setTitle("Ayuda - Manual de Usuario");
        pdfWindow.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));

        // Crear el visor PDF (suponiendo que PDFDisplayer es la clase correcta)
        PDFDisplayer displayer = new PDFDisplayer();
        Scene scene = new Scene(displayer.toNode());

        // Obtener la URL del PDF
        URL pdfUrl = getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/help/pdf/practica01.pdf");

        if (pdfUrl == null) {
            System.err.println("⚠️ El archivo PDF no se encontró en el classpath.");
            return;
        }

        try {
            // Convertir la URL en un archivo y cargar el PDF
            File pdfFile = new File(pdfUrl.toURI());
            displayer.loadPDF(pdfFile);
        } catch (URISyntaxException | IOException e) {
            System.err.println("❌ Error al cargar el PDF: " + e.getMessage());
            return;
        }

        // Configurar la escena y mostrar la ventana
        pdfWindow.setScene(scene);
        pdfWindow.show();
    }
    /**
    *Gráficos
     */
    @FXML
    private void showPieChart() {
        Stage stage = new Stage();
        stage.setTitle("Pie Chart");
        stage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
                new PieChart.Data("Categoría A", 30),
                new PieChart.Data("Categoría B", 25),
                new PieChart.Data("Categoría C", 45)
        );
        stage.setScene(new Scene(pieChart, 400, 300));
        stage.show();
    }
    @FXML
    private void showStackedAreaChart() {
        Stage stage = new Stage();
        stage.setTitle("Stacked Area Chart");
        stage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        StackedAreaChart<String, Number> chart = new StackedAreaChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.getData().addAll(new XYChart.Data<>("2020", 50), new XYChart.Data<>("2021", 80));

        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.getData().addAll(new XYChart.Data<>("2020", 30), new XYChart.Data<>("2021", 60));

        chart.getData().addAll(series1, series2);
        stage.setScene(new Scene(chart, 600, 400));
        stage.show();
    }
    @FXML
    private void showBarChart() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Bar Chart - Ismael Sánchez González");
        stage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Ventas por Año");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().addAll(new XYChart.Data<>("2020", 200), new XYChart.Data<>("2021", 450));

        barChart.getData().add(series);

        // Personalización de colores
        barChart.lookup(".chart-title").setStyle("-fx-text-fill: blue;");
        xAxis.lookup(".axis-label").setStyle("-fx-text-fill: red;");
        yAxis.lookup(".axis-label").setStyle("-fx-text-fill: red;");

        Scene scene = new Scene(barChart, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void showLineChart() {
        Stage stage = new Stage();
        stage.setTitle("Line Chart");
        stage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.getData().addAll(new XYChart.Data<>(1, 5), new XYChart.Data<>(2, 10), new XYChart.Data<>(3, 15));

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>(); // Nueva serie
        series2.getData().addAll(new XYChart.Data<>(1, 8), new XYChart.Data<>(2, 12), new XYChart.Data<>(3, 20));

        lineChart.getData().addAll(series1, series2);
        stage.setScene(new Scene(lineChart, 600, 400));
        stage.show();
    }

    /**
     * Cargar chart
     */
    @FXML
    private void showChart() {
        GenerationsChart.showChartWindow(mainApp.getPersonData());
    }
    /**
     * Cargar donut
     */

    @FXML
    private void showDonut(){
        mainApp.showDonutChartTile();
    }
    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
