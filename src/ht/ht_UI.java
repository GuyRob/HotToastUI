package ht;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class ht_UI {
    public static JFrame htFrame = new JFrame();
    public static JLabel textLabel = new JLabel("");
    public static JTextField inputField = new JTextField(20);
    public static JButton submitButton = new JButton("Submit");
    public static String submitButtonResult;

    // Declare a shared object for synchronization
    private static final Object lock = new Object();


    public static final int RIGHT_ALIGN = -150;



    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static Scanner scan = new Scanner(System.in);

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
        ImageIcon backgroundimage = new ImageIcon("extFiles\\WP_FoodTruck.jpg");
        JLabel backgroundLabel = new JLabel(backgroundimage);
        backgroundLabel.setBounds(0, 0, htFrame.getWidth(), htFrame.getHeight());

        // Set the background label as the content pane of the frame
        htFrame.setContentPane(backgroundLabel);

        // Adjust text and buttons location
        // Text Label
        textLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        textLabel.setVerticalAlignment(SwingConstants.TOP);
        textLabel.setFont(new Font("Arial", Font.BOLD, 20));
        textLabel.setForeground(Color.WHITE);
        textLabel.setBounds(htFrame.getWidth() - RIGHT_ALIGN, 20, 280, 50);
        htFrame.add(textLabel);

        // Input Field
        inputField.setBounds(htFrame.getWidth() - RIGHT_ALIGN, htFrame.getHeight() - 80, 200, 30); // Position the inputField in the bottom right
        htFrame.add(inputField);

        // Button
        submitButton.setBounds(htFrame.getWidth() - RIGHT_ALIGN, htFrame.getHeight() - 40, 80, 30); // Position the submitButton below the inputField
        htFrame.add(submitButton);

        // ActionListener for submitButton
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve the text from the inputField
                String inputText = inputField.getText();

                // Store the result in the instance variable
                submitButtonResult = inputText;

                synchronized (lock) {
                    lock.notify();  // Notify the waiting thread
                }
                submitButton.removeActionListener(this);  // Remove the action listener
            }
        });

        // Access the result after the button is clicked
        String value = submitButtonResult;



        // Make visible
        htFrame.setVisible(true);
    }

    public static int ageUI() {
        // Add text
        textLabel.setText("<html><body style='width: 400px'>" + "So what is your age? (5-100)" + "</body></html>");
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
                        textLabel.setText("<html><font color='red'>Please enter a valid age! (5-100)</font></html>");
                    } else {
                        validAge = true;
                        // Discount validation
                        if (numAge >= 15 && numAge <= 18) {
                            textLabel.setText("<html><font color='green'>You deserve $5 youth discount!</font></html>");
                            discount = 5;
                        }
                        textLabel.setText("So you are " + inpAge + " years old,");
                    }
                } else {
                    textLabel.setText("<html><font color='red'>Please enter numbers only!</font></html>");
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