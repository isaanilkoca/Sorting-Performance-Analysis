import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public static List<ResultRow> loadAggregated(String path) throws Exception {
        List<ResultRow> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine(); // header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 5) continue;

                String algorithm = parts[0].trim();
                String dataset   = parts[1].trim();
                int n            = Integer.parseInt(parts[2].trim());
                int trials       = Integer.parseInt(parts[3].trim());
                long avg         = Long.parseLong(parts[4].trim());

                list.add(new ResultRow(algorithm, dataset, n, trials, avg));
            }
        }

        return list;
    }
}
