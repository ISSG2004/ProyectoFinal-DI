package es.damdi.ismaelsg.adressappmavenjavefx.charts;

import es.damdi.ismaelsg.adressappmavenjavefx.MainApp;
import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.chart.ChartData;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class DonutTileChart {
    @FXML
    private Tile donutChartTile;
    @FXML
    private AnchorPane donutChartPane;
    private MainApp mainApp;
    public void setMainApp(MainApp mainApp){
        this.mainApp = this.mainApp;
        mostrarDatos();
    }
    public void mostrarDatos() {
        int genZ = 0, millennials = 0, genX = 0, boomers = 0;

        for (Person p : mainApp.getPersonData()) {
            int year = p.getBirthday();
            if (year >= 1997 && year <= 2012) genZ++;
            else if (year >= 1981 && year <= 1996) millennials++;
            else if (year >= 1965 && year <= 1980) genX++;
            else if (year >= 1946 && year <= 1964) boomers++;
        }

        ChartData chartData1 = new ChartData("Generación Z", genZ, Color.web("#FF6F61"));
        ChartData chartData2 = new ChartData("Millennials", millennials, Color.web("#FFD966"));
        ChartData chartData3 = new ChartData("Generación X", genX, Color.web("#6FA8DC"));
        ChartData chartData4 = new ChartData("Baby Boomers", boomers, Color.web("#93C47D"));

        donutChartTile = TileBuilder.create()
                .skinType(Tile.SkinType.DONUT_CHART)
                .prefSize(700, 400)
                .title("Generaciones")
                .textVisible(false)
                .chartData(chartData1, chartData2, chartData3, chartData4)
                .build();

        donutChartPane.getChildren().add(donutChartTile);
    }
    public AnchorPane getDonutChartTile() {
        return donutChartPane;
    }
}
