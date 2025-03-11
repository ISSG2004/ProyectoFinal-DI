package es.damdi.ismaelsg.adressappmavenjavefx.documentacion;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Mark down.
 */
public class MarkDown extends Application {
    @Override
    public void start(Stage primaryStage) {

        String markdownContent = loadMarkdown();

        Parser parser = Parser.builder().build();
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        Node document = parser.parse(markdownContent);
        String htmlContent = renderer.render(document);

        WebView webView = new WebView();
        webView.getEngine().loadContent(htmlContent);

        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.setTitle("Ayuda en Markdown");
        primaryStage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
        primaryStage.show();
    }
    private String loadMarkdown() {
        try (var inputStream = getClass().getResourceAsStream("/es/damdi/ismaelsg/adressappmavenjavefx/help/markdown/README.md")) {
            if (inputStream == null) {
                System.err.println("⚠️ El archivo Markdown no se encontró en el classpath.");
                return "Error: No se pudo encontrar el archivo Markdown.";
            }
            return new String(inputStream.readAllBytes()); // Leer todo el contenido
        } catch (IOException e) {
            System.err.println("❌ Error al cargar el archivo Markdown: " + e.getMessage());
            return "Error al cargar el archivo Markdown.";
        }
    }
}

