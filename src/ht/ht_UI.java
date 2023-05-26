package ht;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Scanner;

public class ht_UI {
    /** JFrame */
    public static JFrame htFrame = new JFrame();
    public static JLabel activeTextLabel = new JLabel("");
    public static JLabel systemTextLabel = new JLabel("a");


    public static JTextField inputField = new JTextField(20);
    public static JButton submitButton = new JButton("Submit");
    public static String submitButtonResult;

    public static JLayeredPane systemLayeredPane = new JLayeredPane();

    // Declare a shared object for synchronization
    private static final Object lock = new Object();


    public static final int RIGHT_ALIGN = -200;
    /** End JFrame */



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
        systemLayeredPane.setVisible(true);


        Timer timer = new Timer(durationInSeconds * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemTextLabel.setVisible(false);
                systemLayeredPane.setVisible(false);
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


        // Active Text Label
        activeTextLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        activeTextLabel.setVerticalAlignment(SwingConstants.TOP);
        activeTextLabel.setFont(new Font("Arial", Font.BOLD, 20));
        activeTextLabel.setForeground(Color.WHITE);
        activeTextLabel.setBounds(htFrame.getWidth() - RIGHT_ALIGN, 20, 280, 50);
        backgroundPanel.add(activeTextLabel);

        // LayeredPane (SystemText)
         systemLayeredPane = new JLayeredPane(){
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


        systemLayeredPane.setBounds(300, 720, htFrame.getWidth()-500, 50);
        systemLayeredPane.setOpaque(true); // Set this to true to make the pane visible.
        backgroundPanel.add(systemLayeredPane);
        systemLayeredPane.setVisible(false);


        // System Text Label
        systemTextLabel.setFont(new Font("Arial", Font.BOLD, 20));
        systemTextLabel.setForeground(Color.WHITE);
        systemTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        systemTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        systemTextLabel.setBounds(0, 0, systemLayeredPane.getWidth(), systemLayeredPane.getHeight());
        systemTextLabel.setVisible(false);


        systemLayeredPane.add(systemTextLabel, JLayeredPane.DEFAULT_LAYER); // Add systemTextLabel to systemLayeredPane

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

                    // Not valid age
                    if (numAge < 5 || numAge > 100) {
                        showSystemText("<html><font color='red'>Please enter a valid age! (5-100)</font></html>", 3);
                    }
                    // Valid age
                    else {
                        validAge = true;
                        // Discount validation
                        if (numAge >= 15 && numAge <= 18) {
                            showSystemText("<html><font color='green'>You deserve $5 youth discount!</font></html>", 3);


                            discount = 5;
                        }
                        activeTextLabel.setText("So you are " + inpAge + " years old, ");
                    }
                } else {
                    showSystemText("<html><font color='red'>Please enter numbers only!</font></html>", 3);

                }

                // @TODO check if should be deleted or not
                // Reattach ActionListener to submitButton
//                submitButton.addActionListener(new ActionListener() {
//                    @Override
//                    public void actionPerformed(ActionEvent e) {
//                        synchronized (lock) {
//                            lock.notify();  // Notify the waiting thread
//                        }
//                    }
//                });

                inputField.setText(""); // Clear the inputField

                if (validAge) {
                    break;  // Exit the loop if a valid age is entered
                }
            }
        }

        return discount * -1;
    }
    // END ageUI

    public static int extrasUI() throws InterruptedException {
        activeTextLabel.setText(activeTextLabel.getText() + "what do you want to drink?");

        int userExtrasPrice=0;
        synchronized (lock) {
            while (true) {
                try {
                    lock.wait();  // Wait until notify() is called
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String inpAnswer = inputField.getText();

                char answer = inpAnswer.charAt(0);
                System.out.println(answer);

                // Ask again - 1
                if (answer != 'y' && answer != 'Y') {
//                    System.out.println(ANSI_YELLOW + "We have a good extras, want to try?" + ANSI_RESET);
                    showSystemText("<html><font color='yellow'>We have a good extras, want to try?</font></html>", 3);
                    lock.wait();  // Wait until notify() is called
                    inpAnswer = inputField.getText();
                    answer = inpAnswer.charAt(0);

                    // Ask again - 2
                    if (answer != 'y' && answer != 'Y') {
//                        System.out.println(ANSI_YELLOW + "Listen you must try it, add at least one?" + ANSI_RESET);
                        showSystemText("<html><font color='yellow'>Listen you must try it, add at least one?</font></html>", 3);

                        lock.wait();  // Wait until notify() is called
                        inpAnswer = inputField.getText();
                        answer = inpAnswer.charAt(0);
                    }
                }
                if (answer == 'y' || answer == 'Y') {
                    boolean validExtra = false;
                    HashMap<String, Integer> EXTRAS_MENU = createExtrasMenu();

//                    System.out.println("Which extras do you want?\nWe have: " + EXTRAS_MENU.keySet());
                    activeTextLabel.setText("<html><body style='width: 400px'>" + "Which extras do you want?\nWe have: " + EXTRAS_MENU.keySet() + "</body></html>");


                    // consume the newline character left over from the previous input
//                    scan.nextLine();

                    while (!validExtra) {
                        lock.wait();  // Wait until notify() is called
                        inpAnswer = inputField.getText();
                        String userExtra = inpAnswer.toLowerCase();
                        userExtra = userExtra.replace(" ", "");
                        String[] userExtraSplit = userExtra.split(",");

                        for (String currentUserExtraSplit : userExtraSplit) {
                            if (userExtra.equals("exit")) {
                                validExtra = true;
                                break;
                            }
                            for (String currentExtra : EXTRAS_MENU.keySet()) {
                                if (currentUserExtraSplit.equals(currentExtra)) {
//                                    System.out.println(ANSI_GREEN + currentUserExtraSplit + " added!" + ANSI_RESET);
                                    showSystemText("<html><font color='green'>" + ANSI_GREEN + currentUserExtraSplit + " added!" + ANSI_RESET + "</font></html>", 3);

                                    userExtrasPrice += EXTRAS_MENU.get(currentUserExtraSplit);
                                    validExtra = true;
                                    break;
                                }
                            }

                        }

                        if (validExtra) {
//                            System.out.println("Do you want to add another extra?");
                            activeTextLabel.setText("<html><body style='width: 400px'>" + "Do you want to add another extra?" + "</body></html>");


                            lock.wait();  // Wait until notify() is called
                            inpAnswer = inputField.getText();
                            answer = inpAnswer.charAt(0);

                            if (answer == 'y' || answer == 'Y') {
//                                System.out.println("Which extra you want to add?");
                                activeTextLabel.setText("<html><body style='width: 400px'>" + "Which extra you want to add?" + "</body></html>");

                                validExtra = false;
//                                // consume the newline character left over from the previous input
//                                scan.nextLine();
                            }
                        } else {
//                            System.out.println(ANSI_RED + "Your extras cannot be found, please try again!" + ANSI_RESET);
                            showSystemText("<html><font color='red'>Your extras cannot be found, please try again!</font></html>", 3);

                        }
                    }

                }


        return userExtrasPrice;
        }
        }
    }

    private static HashMap<String, Integer> createExtrasMenu(){
        int EXTRAS_SMALL_PRICE = 3;
        int EXTRAS_MEDIUM_PRICE = 4;
        HashMap<String, Integer> menu = new HashMap<String, Integer>();
        // Add keys and values (Extra, Price)
        menu.put("olives", EXTRAS_SMALL_PRICE);
        menu.put("corn", EXTRAS_SMALL_PRICE);
        menu.put("mushrooms", EXTRAS_SMALL_PRICE);
        menu.put("onions", EXTRAS_MEDIUM_PRICE);
        menu.put("tomatoes", EXTRAS_MEDIUM_PRICE);
        menu.put("bulgar", EXTRAS_MEDIUM_PRICE);

        return menu;

    }



}