package metrics.graphing;

import java.util.ArrayList;

import java.util.List;

public class MaxStrategy implements DataDisplayStrategy {
    @Override
    public List<Double> calculateData(List<Double> inputData) {
        if (inputData.isEmpty()) {
            return new ArrayList<>(); // Handle empty input
        }
        double max = Double.MIN_VALUE;
        List<Double> result = new ArrayList<>();
        for (int i = 0; i < inputData.size(); i++) {
            if (i % 10 == 0) {
                result.add(max);
                max = inputData.get(i);
            }
            double num = inputData.get(i);
            max = Math.max(num, max);
        }
        return result;
    }
}

