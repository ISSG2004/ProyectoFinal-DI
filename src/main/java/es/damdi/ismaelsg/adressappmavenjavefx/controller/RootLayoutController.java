package es.damdi.ismaelsg.adressappmavenjavefx.controller;


import com.dansoftware.pdfdisplayer.PDFDisplayer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import es.damdi.ismaelsg.adressappmavenjavefx.MainApp;
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

    @FXML
    public void handleWeb() {
        // Crear un componente WebView para mostrar el contenido del HTML local
        WebView webView = new WebView();

        try {
            // Obtener el archivo HTML local
            // Aquí asumimos que el archivo está en el directorio "resources/help/html" dentro del proyecto
            File file = new File("src/main/resources/help/web/index.html");

            if (file.exists()) {
                // Convertir el archivo a una URL
                URI uri = file.toURI();
                URL url = uri.toURL();

                // Cargar el archivo HTML en el WebView
                webView.getEngine().load(url.toExternalForm());
            } else {
                // Si el archivo no existe, mostrar un error
                System.err.println("⚠️ No se encontró el archivo HTML.");
                webView.getEngine().loadContent("<html><body><h2>Error: No se pudo cargar el archivo HTML.</h2></body></html>");
            }
        } catch (Exception e) {
            // Manejo de excepciones
            System.err.println("⚠️ Ocurrió un error al cargar el archivo HTML: " + e.getMessage());
            webView.getEngine().loadContent("<html><body><h2>Error: No se pudo cargar el archivo HTML.</h2></body></html>");
        }

        // Crear un nuevo Stage para la ventana de ayuda no modal
        Stage webViewStage = new Stage();
        webViewStage.setTitle("Archivo HTML");
        webViewStage.setWidth(800);
        webViewStage.setHeight(600);

        // Crear una escena con el WebView
        Scene scene = new Scene(webView, 800, 600);

        // Asignar la escena al nuevo Stage
        webViewStage.setScene(scene);

        // Mostrar la ventana no modal
        webViewStage.show();
    }


    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta cerrar esta
        ventanaEmergente.setTitle("About");
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
        // Crear una ventana emergente (Stage) para mostrar la documentación
        Stage ventanaEmergente = new Stage();
        ventanaEmergente.initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta cerrar esta
        ventanaEmergente.setTitle("Documentación Técnica");

        // Cargar el contenido del archivo Markdown
        String markdownContent = loadMarkdown();

        // Convertir Markdown a HTML utilizando Flexmark
        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        Node document = parser.parse(markdownContent);
        String htmlContent = renderer.render(document);

        // Crear un WebView para mostrar el contenido HTML generado
        WebView webView = new WebView();
        webView.getEngine().loadContent(htmlContent);

        // Configurar el layout y la escena para la ventana emergente
        VBox layout = new VBox(webView);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");
        Scene scene = new Scene(layout, 800, 600);

        ventanaEmergente.setScene(scene);
        ventanaEmergente.showAndWait();
    }

    /**
     * Método para cargar el contenido del archivo Markdown desde los recursos del classpath.
     *
     * @return Contenido del archivo Markdown como String, o un mensaje de error si ocurre un problema.
     */
    private String loadMarkdown() {
        // Obtener la URL del archivo Markdown en recursos
        URL resourceUrl = getClass().getClassLoader().getResource("help/markdown/README.md");

        if (resourceUrl == null) {
            System.err.println("⚠️ El archivo README.md no se encontró en el classpath.");
            return "Error: No se pudo encontrar el archivo Markdown.";
        } else {
            System.out.println("Archivo encontrado: " + resourceUrl);
        }

        try {
            // Convertir la URL en una ruta válida para leer el archivo
            Path path = Paths.get(resourceUrl.toURI());
            return Files.readString(path);
        } catch (IOException | URISyntaxException e) {
            System.err.println("❌ Error al cargar el archivo Markdown: " + e.getMessage());
            return "Error al cargar el archivo Markdown.";
        }
    }

    /**
     * Método para cargar el pdf
     */
    @FXML
    private void handleLoadPdf() {
        // Crear un nuevo Stage (ventana) para el visor PDF
        Stage pdfWindow = new Stage();
        pdfWindow.setTitle("Ayuda - Manual de Usuario");

        // Crear el visor PDF (suponiendo que PDFDisplayer es la clase correcta)
        PDFDisplayer displayer = new PDFDisplayer();
        Scene scene = new Scene(displayer.toNode());

        // Obtener la URL del PDF
        URL pdfUrl = getClass().getResource("help/pdf/practica01.pdf");

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
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
