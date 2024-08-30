package metrics.graphing;

import java.util.ArrayList;
import java.util.List;

public class AllPointsStrategy implements DataDisplayStrategy {
    @Override
    public List<Double> calculateData(List<Double> inputData) {
        // Return the input data as is (no change)
        return inputData;
    }
}