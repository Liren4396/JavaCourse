package metrics.graphing;

import java.util.ArrayList;
import java.util.List;

public class SumStrategy implements DataDisplayStrategy {
    @Override
    public List<Double> calculateData(List<Double> inputData) {
        if (inputData.isEmpty()) {
            return new ArrayList<>(); // Handle empty input
        }
        double sum = 0.0;
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < inputData.size(); i++) {
            if (i % 10 == 0) {
                result.add(sum);
                sum = 0;
            }
            sum += inputData.get(i);
        }
        return result;
    }
}
