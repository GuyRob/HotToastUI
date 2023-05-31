package ht;

import javax.swing.*;
import java.awt.*;

public class TEMPLATE_TestingLayoutManagers {
    private JPanel northFlowLayoutPanel;
    private JPanel southBorderLayoutPanel;
    private JPanel centerGridBagLayoutPanel;
    private JPanel westBoxLayoutPanel;
    private JPanel eastGridLayoutPanel;

    private final JButton northButton = new JButton("North Button");
    private final JButton southButton = new JButton("South Button");
    private final JButton centerButton = new JButton("Center Button");
    private final JButton eastButton = new JButton("East Button");

    private final JButton menuButton1 = new JButton("Menu Item 1");
    private final JButton menuButton2 = new JButton("Menu Item 2");
    private final JButton menuButton3 = new JButton("Menu Item 3");
    private final JButton menuButton4 = new JButton("Menu Item 4");
    private final JButton menuButton5 = new JButton("Menu Item 5");

    public TEMPLATE_TestingLayoutManagers() {
        northFlowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southBorderLayoutPanel = new JPanel(new BorderLayout());
        centerGridBagLayoutPanel = new JPanel(new GridBagLayout());
        eastGridLayoutPanel = new JPanel(new GridLayout(1, 1));
        Box box = Box.createVerticalBox();
        westBoxLayoutPanel = new JPanel();

        northFlowLayoutPanel.add(northButton);
        northFlowLayoutPanel.setBorder(BorderFactory.createTitledBorder("Flow Layout"));

        southBorderLayoutPanel.add(southButton);
        southBorderLayoutPanel.setBorder(BorderFactory.createTitledBorder("Border Layout"));

        centerGridBagLayoutPanel.add(centerButton);
        centerGridBagLayoutPanel.setBorder(BorderFactory.createTitledBorder("GridBag Layout"));

        eastGridLayoutPanel.add(eastButton);
        eastGridLayoutPanel.setBorder(BorderFactory.createTitledBorder("Grid Layout"));

        box.add(menuButton1);
        box.add(menuButton2);
        box.add(menuButton3);
        box.add(menuButton4);
        box.add(menuButton5);
        westBoxLayoutPanel.add(box);
        westBoxLayoutPanel.setBorder(BorderFactory.createTitledBorder("Box Layout"));

        JFrame frame = new JFrame("Test Layout Managers");
        frame.setLayout(new BorderLayout());      // This is the deafault layout
        frame.add(northFlowLayoutPanel, BorderLayout.PAGE_START);
        frame.add(southBorderLayoutPanel, BorderLayout.PAGE_END);
        frame.add(centerGridBagLayoutPanel, BorderLayout.CENTER);
        frame.add(eastGridLayoutPanel, BorderLayout.LINE_END);
        frame.add(westBoxLayoutPanel, BorderLayout.LINE_START);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException
                    | UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            }

            new TEMPLATE_TestingLayoutManagers();
        });
    }
}
