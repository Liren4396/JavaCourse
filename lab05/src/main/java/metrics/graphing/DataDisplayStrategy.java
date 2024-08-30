package metrics.graphing;

import java.util.ArrayList;
import java.util.List;

public interface DataDisplayStrategy {
    List<Double> calculateData(List<Double> inputData);
}
