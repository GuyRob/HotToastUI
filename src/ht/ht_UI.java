package ht;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class ht_UI {
    public static JFrame htFrame = new JFrame();
    public static JLabel activeTextLabel = new JLabel("");
    public static JLabel systemTextLabel = new JLabel("a");


    public static JTextField inputField = new JTextField(20);
    public static JButton submitButton = new JButton("Submit");
    public static String submitButtonResult;

    // Declare a shared object for synchronization
    private static final Object lock = new Object();


    public static final int RIGHT_ALIGN = -200;



    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static Scanner scan = new Scanner(System.in);

    private static ActionListener submitButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = inputField.getText();
            submitButtonResult = inputText;
            synchronized (lock) {
                lock.notify();
            }
        }
    };

    private static void showSystemText(String text, int durationInSeconds) {
        systemTextLabel.setText(text);
        systemTextLabel.setVisible(true);

        Timer timer = new Timer(durationInSeconds * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemTextLabel.setVisible(false);
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void createFrame() {
        // Creating Frame
        htFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        htFrame.setLayout(new FlowLayout());

        // Adjusting size and title
        htFrame.setTitle("HOT TOAST");
        htFrame.setSize(1024, 720);
        htFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Icon
        ImageIcon iconimage = new ImageIcon("extFiles\\ht_icon.png");
        htFrame.setIconImage(iconimage.getImage());

        // Background
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundimage = new ImageIcon("extFiles\\WP_FoodTruck.jpg");
                Image image = backgroundimage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(null); // Use absolute positioning for components
        htFrame.setContentPane(backgroundPanel);

        // Adjust text and buttons location
        // Active Text Label
        activeTextLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        activeTextLabel.setVerticalAlignment(SwingConstants.TOP);
        activeTextLabel.setFont(new Font("Arial", Font.BOLD, 20));
        activeTextLabel.setForeground(Color.WHITE);
        activeTextLabel.setBounds(htFrame.getWidth() - RIGHT_ALIGN, 20, 280, 50);
        backgroundPanel.add(activeTextLabel);

        // Create a new JLabel for the systemTextBorder
//        JLabel systemTextBorder = new JLabel();
//        systemTextBorder.setBounds(htFrame.getWidth() / 2 + 100, 350, 500, 50);
//        systemTextBorder.setBorder(new LineBorder(Color.BLACK, 2)); // Set the border color and thickness
//        backgroundPanel.add(systemTextBorder);         // Add the components to the backgroundPanel

        JLayeredPane layeredPane = new JLayeredPane(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            // Set the desired opacity level (0.0 - 1.0)
            float opacity = 0.8f;


            // Create an AlphaComposite with the specified opacity
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
            g2d.setComposite(alphaComposite);

            // Set the desired background color
            g2d.setColor(new Color(200,89,42));
            g2d.fillRect(0, 0, getWidth(), getHeight());

            g2d.dispose();
        }
        };

        layeredPane.setBounds(300, 720, htFrame.getWidth()-500, 50);
        layeredPane.setOpaque(true); // Set this to true to make the pane visible.
        backgroundPanel.add(layeredPane);

        // System Text Label
        systemTextLabel.setFont(new Font("Arial", Font.BOLD, 20));
        systemTextLabel.setForeground(Color.WHITE);
        systemTextLabel.setBounds(0, 0, layeredPane.getWidth(), layeredPane.getHeight());

        layeredPane.add(systemTextLabel, JLayeredPane.DEFAULT_LAYER);

        // Input Field
        inputField.setBounds(htFrame.getWidth() - RIGHT_ALIGN, htFrame.getHeight() - 80, 200, 30); // Position the inputField in the bottom right
        backgroundPanel.add(inputField);

        // Button
        submitButton.setBounds(htFrame.getWidth() - RIGHT_ALIGN, htFrame.getHeight() - 40, 80, 30); // Position the submitButton below the inputField
        backgroundPanel.add(submitButton);

        submitButton.addActionListener(submitButtonListener);


        htFrame.setVisible(true);
    }



    public static int ageUI() {
        // Add text
        activeTextLabel.setText("<html><body style='width: 400px'>" + "So what is your age? (5-100)" + "</body></html>");
        int discount = 0;
        boolean validAge = false;


        synchronized (lock) {
            while (true) {
                try {
                    lock.wait();  // Wait until notify() is called
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String inpAge = inputField.getText();

                // Numbers only validation
                if (inpAge.matches("^[0-9]*$")) {
                    int numAge = Integer.parseInt(inpAge);

                    // Valid age validation
                    if (numAge < 5 || numAge > 100) {
//                        activeTextLabel.setText("<html><font color='red'>Please enter a valid age! (5-100)</font></html>");
                        showSystemText("<html><font color='red'>Please enter a valid age! (5-100)</font></html>", 3);

                    } else {
                        validAge = true;
                        // Discount validation
                        if (numAge >= 15 && numAge <= 18) {
//                            activeTextLabel.setText("<html><font color='green'>You deserve $5 youth discount!</font></html>");
                            showSystemText("<html><font color='green'>You deserve $5 youth discount!</font></html>", 3);


                            discount = 5;
                        }
                        activeTextLabel.setText("So you are " + inpAge + " years old,");
                    }
                } else {
//                    activeTextLabel.setText("<html><font color='red'>Please enter numbers only!</font></html>");
                    showSystemText("<html><font color='red'>Please enter numbers only!</font></html>", 3);

                }

                // Reattach ActionListener to submitButton
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        synchronized (lock) {
                            lock.notify();  // Notify the waiting thread
                        }
                    }
                });

                // Clear the inputField
                inputField.setText("");

                if (validAge) {
                    break;  // Exit the loop if a valid age is entered
                }
            }
        }

        return discount * -1;
    }
    // END CHATGPT copy

    // COPY age function
//        int discount = 0;
//        boolean validAge = false;
//
//        while (!validAge) {
//            String inpAge = scan.next();
//
//            // Numbers only validation
//            if(inpAge.matches("^[0-9]*$")){
//                int numAge = Integer.parseInt(inpAge);
//                System.out.println(numAge);
//                // Valid age validation
//                if (numAge < 5 || numAge > 100) {
//                    System.out.println(ANSI_RED + "Please enter a valid age! (5-100)" + ANSI_RESET);
//                } else {
//                    validAge = true;
//                    // Discount validation
//                    if (numAge >= 15 && numAge <= 18) {
//                        System.out.println(ANSI_GREEN + "You deserve $5 youth discount!" + ANSI_RESET);
//                        discount = 5;
//                    }
//                    System.out.println("So you are " + inpAge + " years old,");
//                }
//
//            } else {
//                System.out.println(ANSI_RED + "Please enter numbers only!" + ANSI_RESET);
//            }
//        }
//        return discount*-1;
    // END COPY

}