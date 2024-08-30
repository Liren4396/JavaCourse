package metrics;

import java.util.ArrayList;
import java.util.List;

public class Emitter {
    public void emitMetric(double xValue, Plot plot) {
        Double metric = Math.sin(Math.toRadians(xValue));
        plot.updateMetric(metric);
    }
}
