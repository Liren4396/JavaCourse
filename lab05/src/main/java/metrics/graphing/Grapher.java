package metrics.graphing;

import java.awt.BorderLayout;

import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import metrics.Emitter;
import metrics.Plot;

public class Grapher {
    public static void main(String[] args) throws InterruptedException {

        Plot plot = new Plot();
        // Create the GUI on the Event-Dispatch-Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Grapher.createAndShowGUI(plot);
            }
        });

        Emitter emitter = new Emitter();
        int i = 0;
        while (true) {
            Thread.sleep(100);
            emitter.emitMetric(i, plot);
            i += 5;
        }

    }

    public static Panel createAndShowGUI(Plot plot) {
        JFrame frame = new JFrame("Metrics");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(800, 600);

        Panel plotPanel = new Panel(plot);
        plot.setPanel(plotPanel);

        frame.getContentPane().add(plotPanel, BorderLayout.CENTER);

        JComponent controlPanel = createControlPanel(plotPanel);
        frame.getContentPane().add(controlPanel, BorderLayout.EAST);

        frame.setVisible(true);
        return plotPanel;
    }

    private static JComponent createControlPanel(final Panel plotPanel) {

        JPanel controlPanel = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(0, 2));
        controlPanel.add(panel, BorderLayout.NORTH);

        String[] statistics = new String[] {
                "All Points", "Average", "Max", "Sum"
        };

        final JList<String> statisticsList = new JList<>(statistics);

        statisticsList.setSelectedIndex(0);

        panel.add(new JLabel("statistic"));
        panel.add(statisticsList);

        ListSelectionListener changeListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                String statistic = statisticsList.getSelectedValue();
                // TODO Change Strategy based on what button is pressed
                System.out.println(statistic);

                DataDisplayStrategy strategy = new AllPointsStrategy();

                if (statistic.equals("All Points")) {
                    strategy = new AllPointsStrategy();
                } else if (statistic.equals("Average")) {
                    strategy = new AverageStrategy();
                } else if (statistic.equals("Max")) {
                    strategy = new MaxStrategy();
                } else if (statistic.equals("Sum")) {
                    strategy = new SumStrategy();
                }
                plotPanel.setDataDisplayStrategy(strategy);
            
                // Repaint the panel to apply the new strategy
                // plotPanel.repaint();
            }
        };
        statisticsList.addListSelectionListener(changeListener);

        return controlPanel;
    }
}
