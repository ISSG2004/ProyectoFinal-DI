package es.damdi.ismaelsg.adressappmavenjavefx.documentacion;


import es.damdi.ismaelsg.adressappmavenjavefx.charts.GenerationsChart;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;

/**
 * The type Help viewer.
 */
public class HelpViewer extends Application {

    @Override
    public void start(Stage primaryStage) {
        WebView webView = new WebView();

        // Extraer HTML temporalmente
        String tempHtmlPath = extractResource("/es/damdi/ismaelsg/adressappmavenjavefx/help/web/index.html");
        if (tempHtmlPath != null) {
            webView.getEngine().load(new File(tempHtmlPath).toURI().toString());
        } else {
            System.err.println("⚠️ No se encontró el archivo HTML en el classpath.");
            webView.getEngine().loadContent("<html><body><h2>Error: No se pudo cargar la ayuda.</h2></body></html>");
        }

        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.setTitle("Ayuda - Documentación");

        // Extraer icono temporalmente
        String tempImagePath = extractResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg");
        if (tempImagePath != null) {
            primaryStage.getIcons().add(new Image(new File(tempImagePath).toURI().toString()));
        } else {
            System.err.println("⚠️ No se encontró el icono en el classpath.");
        }

        primaryStage.show();
    }

    /**
     * Extrae un recurso del JAR a un archivo temporal y devuelve su ruta.
     */
    private String extractResource(String resourcePath) {
        try (InputStream inputStream = getClass().getResourceAsStream(resourcePath)) {
            if (inputStream == null) {
                return null;
            }

            File tempFile = Files.createTempFile("temp_resource", resourcePath.substring(resourcePath.lastIndexOf("."))).toFile();
            tempFile.deleteOnExit(); // Se eliminará automáticamente al cerrar la aplicación

            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

