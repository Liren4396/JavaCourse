package metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import metrics.graphing.Panel;

public class Plot {
    private Panel panel;
    // You may remove the example data for metrics to complete your implementation
    private List<Double> metrics = new ArrayList<>();

    public void updateMetric(Double metric) {
        metrics.add(metric);
        if (panel != null) {
            panel.repaint();
        }
    }
    private int range = 150; 

    public Panel getPanel() {
        return panel;
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public List<Double> getData() {
        List<Double> newList = metrics;
        if (newList.size() > range) {
            newList = newList.subList(newList.size() - range - 1, newList.size() - 1);
        }
        return newList;
    }
}
