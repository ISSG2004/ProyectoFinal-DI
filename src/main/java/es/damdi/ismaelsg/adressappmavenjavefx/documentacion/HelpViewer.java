package es.damdi.ismaelsg.adressappmavenjavefx.documentacion;


import es.damdi.ismaelsg.adressappmavenjavefx.charts.GenerationsChart;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.net.URL;
public class HelpViewer extends Application {

    @Override
    public void start(Stage primaryStage) {

        WebView webView = new WebView();

        URL url = getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/help/web/index.html");


        if (url != null) {
            webView.getEngine().load(url.toExternalForm());
        } else {
            System.err.println("⚠️ No se encontró el archivo HTML en el classpath.");
            webView.getEngine().loadContent("<html><body><h2>Error: No se pudo cargar la ayuda.</h2></body></html>");
        }

        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.setTitle("Ayuda - Documentación");
        primaryStage.show();
        primaryStage.getIcons().add(new Image(getClass().getResource("/es/damdi/ismaelsg/adressappmavenjavefx/media/OIP.jpg").toExternalForm()));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
