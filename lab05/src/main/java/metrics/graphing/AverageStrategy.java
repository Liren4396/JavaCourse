package metrics.graphing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AverageStrategy implements DataDisplayStrategy {
    @Override
    public List<Double> calculateData(List<Double> inputData) {
        if (inputData.isEmpty()) {
            return new ArrayList<>();
        }
        double average = 0.0;
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < inputData.size(); i++) {
            if (i % 10 == 0) {
                result.add(average/10);
                average = 0;
            }
            average += inputData.get(i);
        }
        return result;
    }
}