package es.damdi.ismaelsg.adressappmavenjavefx.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;

public class LineChartController {
    @FXML
    private Pane paneView;
    @FXML
    private void initialize() {loadData();}

    private void loadData() {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number,Number> lineChart =
                new LineChart<Number,Number>(xAxis,yAxis);

        lineChart.setTitle("Stock Monitoring, 2010");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Serie 1");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        // Definir la segunda serie
        XYChart.Series series2 = new XYChart.Series();
        series2.setName("SErie2");
        series2.getData().add(new XYChart.Data(1, 30));
        series2.getData().add(new XYChart.Data(2, 18));
        series2.getData().add(new XYChart.Data(3, 25));
        series2.getData().add(new XYChart.Data(4, 28));
        series2.getData().add(new XYChart.Data(5, 40));
        series2.getData().add(new XYChart.Data(6, 33));
        series2.getData().add(new XYChart.Data(7, 27));
        series2.getData().add(new XYChart.Data(8, 50));
        series2.getData().add(new XYChart.Data(9, 41));
        series2.getData().add(new XYChart.Data(10, 22));
        series2.getData().add(new XYChart.Data(11, 34));
        series2.getData().add(new XYChart.Data(12, 30));

        // Crear la escena y agregar las series
        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(series, series2);
    }
}

