package es.damdi.ismaelsg.adressappmavenjavefx.charts;

import es.damdi.ismaelsg.adressappmavenjavefx.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GenerationsChart { private static PieChart pieChart;

    // Método para calcular la distribución de generaciones
    private static Map<String, Integer> calculateGenerations(List<Integer> birthYears) {
        Map<String, Integer> generationCount = new HashMap<>();
        for (int year : birthYears) {
            String generation = getGeneration(year);
            generationCount.put(generation, generationCount.getOrDefault(generation, 0) + 1);
        }
        return generationCount;
    }

    // Método para determinar la generación según el año de nacimiento
    private static String getGeneration(int year) {
        if (year < 1946) return "Generación Silenciosa";
        else if (year < 1965) return "Baby Boomers";
        else if (year < 1981) return "Generación X";
        else if (year < 1997) return "Millennials";
        else return "Generación Z";
    }

    // Método para actualizar los datos del gráfico
    private static void updateChartData(ObservableList<Person> persons) {
        // Extraer los años de nacimiento de la lista de personas
        List<Integer> birthYears = persons.stream()
                .map(Person::getBirthday)
                .collect(Collectors.toList());

        Map<String, Integer> data = calculateGenerations(birthYears);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        pieChart.setData(pieChartData);
    }

    // Método para mostrar la ventana con el gráfico
    public static void showChartWindow(ObservableList<Person> persons) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Gráfico de Generaciones");

        pieChart = new PieChart();
        pieChart.setTitle("Distribución de Generaciones");

        updateChartData(persons);  // Cargar datos iniciales

        // Escuchar cambios en la lista y actualizar el gráfico en tiempo real
        persons.addListener((ListChangeListener<Person>) change -> updateChartData(persons));

        Scene scene = new Scene(pieChart, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}