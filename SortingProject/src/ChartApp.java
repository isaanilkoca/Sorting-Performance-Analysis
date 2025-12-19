import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

public class ChartApp extends Application {

    @Override
    public void start(Stage stage) {

        // CSV oku
        List<ResultRow> rows;
        try {
            String csvPath = Paths.get("experiments", "aggregated_results.csv").toString();
            rows = CsvLoader.loadAggregated(csvPath);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // 3 grafik oluştur
        LineChart<Number, Number> randomChart = buildChart(rows, "random");
        LineChart<Number, Number> sortedChart = buildChart(rows, "sorted");
        LineChart<Number, Number> reversedChart = buildChart(rows, "reversed");

        // Sekmeler
        TabPane tabs = new TabPane();

        Tab tabRandom = new Tab("Random", randomChart);
        Tab tabSorted = new Tab("Sorted", sortedChart);
        Tab tabReversed = new Tab("Reversed", reversedChart);

        tabRandom.setClosable(false);
        tabSorted.setClosable(false);
        tabReversed.setClosable(false);

        tabs.getTabs().addAll(tabRandom, tabSorted, tabReversed);

        // Pencere
        BorderPane root = new BorderPane(tabs);
        Scene scene = new Scene(root, 900, 600);
        stage.setScene(scene);
        stage.setTitle("Sorting Algorithm Performance Comparison");
        stage.show();
    }

    private LineChart<Number, Number> buildChart(List<ResultRow> rows, String datasetName) {

        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Input size n");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Average time (nanoseconds)");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle(datasetName.toUpperCase() + " dataset – Runtime vs n");

        String[] algos = {"InsertionSort", "MergeSort", "QuickSort"};

        for (String algo : algos) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(algo);

            rows.stream()
                    .filter(r -> r.dataset.equals(datasetName) && r.algorithm.equals(algo))
                    .sorted(Comparator.comparingInt(r -> r.n))
                    .forEach(r -> series.getData().add(new XYChart.Data<>(r.n, r.avgNano)));

            chart.getData().add(series);
        }

        return chart;
    }

    public static void main(String[] args) {
        launch();
    }
}
