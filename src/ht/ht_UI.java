package ht;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ht_UI {
    public static JFrame htFrame = new JFrame();
    public static JLabel textLabel = new JLabel("");
    public static JTextField inputField = new JTextField(20);
    public static JButton submitButton = new JButton("Submit");

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

        // Make visible
        htFrame.setVisible(true);
    }

    public static int ageUI() {
        // Add text
        textLabel.setText("<html><body style='width: 400px'>" + "So what is your age? (5-100)" + "</body></html>");



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
        return 0; // DELETE ME
    }

}