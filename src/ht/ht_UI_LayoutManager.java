package ht;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;

public class ht_UI_LayoutManager {
    /** Layout Manager */
    private static JPanel northFlowLayoutPanel;
    private static JPanel southBorderLayoutPanel;
    private static JPanel centerGridBagLayoutPanel;
//    private static JPanel westBoxLayoutPanel;
    private static JPanel eastGridLayoutPanel;

    /** JFrame - App */
    public static JLabel activeTextLabel = new JLabel("ACTIVETEXT");
    public static JLabel systemTextLabel = new JLabel("SYSTEMTEXT");

    public static JTextField inputField = new JTextField(40);
    public static JButton submitButton = new JButton("Submit");
    public static String submitButtonResult;

//    public static JLayeredPane systemLayeredPane = new JLayeredPane();

    // Declare a shared object for synchronization
    private static final Object lock = new Object(); // LOCK

    /** Functions - Layout Manager */
    public static void createUI_Main() {
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

            configurationUI(); // Calling configuration UI
        });
    }

    public static void configurationUI() {
        northFlowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northFlowLayoutPanel.setLayout(new BoxLayout(northFlowLayoutPanel, BoxLayout.Y_AXIS));
        northFlowLayoutPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        southBorderLayoutPanel = new JPanel(new BorderLayout());
        centerGridBagLayoutPanel = new JPanel(new GridBagLayout());
        eastGridLayoutPanel = new JPanel(new GridLayout(1, 1));

        // Up - northFlowLayoutPanel
        // Active Text Label
        activeTextLabel.setFont(new Font("Courier", Font.BOLD, 24));
        activeTextLabel.setForeground(Color.BLACK);
        activeTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel activeTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Create a nested panel for centering the activeTextLabel
        activeTextPanel.add(activeTextLabel);
        northFlowLayoutPanel.add(activeTextPanel);

        // System Text Label
        systemTextLabel.setFont(new Font("Courier", Font.BOLD, 24));
        systemTextLabel.setForeground(Color.BLACK);
        systemTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel systemTextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        systemTextPanel.add(systemTextLabel);
        northFlowLayoutPanel.add(systemTextPanel);

        systemTextLabel.setVisible(false);

        // Down - southBorderLayoutPanel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(inputField);
        inputPanel.add(submitButton);

        southBorderLayoutPanel.add(inputPanel, BorderLayout.SOUTH);
        submitButton.addActionListener(submitButtonListener);
        southBorderLayoutPanel.setBorder(BorderFactory.createTitledBorder("Please enter input"));


        inputField.addKeyListener(new KeyAdapter() { // Listener to 'ENTER'
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitButton.doClick();

                }
            }
        });

        // Center - Default Frame
        JFrame frame = new JFrame("HOT TOAST");
        frame.setLayout(new BorderLayout());      // This is the default layout
        frame.add(northFlowLayoutPanel, BorderLayout.PAGE_START);
        frame.add(southBorderLayoutPanel, BorderLayout.PAGE_END);
        frame.add(centerGridBagLayoutPanel, BorderLayout.CENTER);
        frame.add(eastGridLayoutPanel, BorderLayout.LINE_END);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        ImageIcon iconimage = new ImageIcon("extFiles\\ht_icon.png");
        frame.setIconImage(iconimage.getImage());
        frame.setTitle("HOT TOAST");

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
        frame.add(backgroundPanel);

    }

    /** Functions - JFrame - App */
    private static ActionListener submitButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String inputText = inputField.getText();
            submitButtonResult = inputText;
            inputField.setText(""); // Clear the inputField
            synchronized (lock) {
                lock.notify();
            }
        }
    };

    private static void showSystemText(String text, int durationInSeconds) throws InterruptedException {
        systemTextLabel.setText(text);
        systemTextLabel.setVisible(true);

        // Need to use this timer to not cause a sleep in all app
        Timer timer = new Timer(durationInSeconds * 1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemTextLabel.setVisible(false);
                ((Timer) e.getSource()).stop();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static int ageUI() throws InterruptedException {
        activeTextLabel.setText("<html><font color='black'>So what is your age? (5-100)</font></html>");         // Add text

        int discount = 0;
        boolean validAge = false;


        synchronized (lock) { // LOCK
            while (true) { // LOCK

                lock.wait();  // Wait until notify() is called


                String inpAge = submitButtonResult;

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

                if (validAge) {
                    break;  // Exit the loop if a valid age is entered
                }
            }
        }

        return discount * -1;
    }
    // END ageUI

    public static int extrasUI() throws InterruptedException {
        activeTextLabel.setText(activeTextLabel.getText() + "do you want extras? (y/n)");

        int userExtrasPrice=0;
        synchronized (lock) { // LOCK
            while (true) { // LOCK
                lock.wait();  // Wait until notify() is called

                String inpAnswer = submitButtonResult;
                char answer = inpAnswer.charAt(0);

                // Ask again - 1
                if (answer != 'y' && answer != 'Y') {
//                    System.out.println(ANSI_YELLOW + "We have a good extras, want to try?" + ANSI_RESET);
                    showSystemText("<html><font color='orange'>We have a good extras, want to try?</font></html>", 3);
                    lock.wait();  // Wait until notify() is called
                    inpAnswer = submitButtonResult;
                    answer = inpAnswer.charAt(0);

                    // Ask again - 2
                    if (answer != 'y' && answer != 'Y') {
//                        System.out.println(ANSI_YELLOW + "Listen you must try it, add at least one?" + ANSI_RESET);
                        showSystemText("<html><font color='orange'>Listen you must try it, add at least one?</font></html>", 3);

                        lock.wait();  // Wait until notify() is called
                        inpAnswer = submitButtonResult;
                        answer = inpAnswer.charAt(0);
                    }
                }
                if (answer == 'y' || answer == 'Y') {
                    boolean validExtra = false;
                    HashMap<String, Integer> EXTRAS_MENU = createExtrasMenu();

                    activeTextLabel.setText("<html><font color='black'>" + "Which extras do you want?\nWe have: " + EXTRAS_MENU.keySet() + "</font></html>");

                    while (!validExtra) {
                        lock.wait();  // Wait until notify() is called
                        inpAnswer = submitButtonResult;
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
                                    showSystemText("<html><font color='green'>" + currentUserExtraSplit + " added!" + "</font></html>", 3);
                                    Thread.sleep(1000); // Sleep to fix print delay
                                    userExtrasPrice += EXTRAS_MENU.get(currentUserExtraSplit);
                                    validExtra = true;
                                    break;
                                }
                            }

                        }

                        if (validExtra) {
                            activeTextLabel.setText("<html><font color='black'>" + "Do you want to add another extra?" + "</font></html>");


                            lock.wait();  // Wait until notify() is called
                            inpAnswer = submitButtonResult;
                            answer = inpAnswer.charAt(0);

                            if (answer == 'y' || answer == 'Y') {
                                activeTextLabel.setText("<html><font color='black'>Which extra you want to add?</font></html>");         // Add text
                                validExtra = false;

                            }
                        } else {
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

    public static int drinkUI () throws InterruptedException {
        activeTextLabel.setText("<html><font color='black'>Would you like to add drinks? (y/n)</font></html>");         // Add text

        int DRINK_PRICE = 8;
        String[] DRINK_NAMES = {"cola", "fanta"};
        int userDrinksPrice = 0;

        synchronized (lock) { // LOCK
            while (true) { // LOCK
                lock.wait(); // Wait until notify() is called


                char answer = submitButtonResult.charAt(0);
                if (answer != 'y' && answer != 'Y') {
                    showSystemText("<html><font color='orange'>You'll be thirsty, add drink?</font></html>", 3);
                    lock.wait();  // Wait until notify() is called
                    answer = submitButtonResult.charAt(0);
                }
                if (answer == 'y' || answer == 'Y') {
                    activeTextLabel.setText("<html><font color='black'>" + "What do you want to drink?  We have: " + Arrays.toString(DRINK_NAMES) + "</font></html>");         // Add text
                    int drinksAmount = 0;
                    boolean validDrink = false;
                    while (!validDrink) {
                        lock.wait();  // Wait until notify() is called
                        String userDrink = submitButtonResult.toLowerCase();
                        for (String currentDrink : DRINK_NAMES) {
                            if (userDrink.equals(currentDrink)) {
                                showSystemText("<html><font color='green'>" + userDrink + " added!</font></html>", 3);
                                drinksAmount++;
                                validDrink = true;
                                break;
                            }
                        }
                        if (!validDrink) {
                            showSystemText("<html><font color='red'>Please enter a valid one drink!</font></html>", 3);
                        } else {
                            /** @ANOTHERDRINK - Enable/Disable another drink option: */
                            activeTextLabel.setText("<html><font color='black'>Do you want to add another drink? (y/n)</font></html>");         // Add text
                            lock.wait(); // Wait until notify() is called
                            answer = submitButtonResult.charAt(0);
                            if (answer == 'y' || answer == 'Y') {
                                activeTextLabel.setText("<html><font color='black'>Which drink you want to add?</font></html>");         // Add text
                                validDrink = false;
                            }
                        }
                         /** @ANOTHERDRINK */

                    }

                    userDrinksPrice = drinksAmount * DRINK_PRICE;

                }
                return userDrinksPrice;
            }
        }
    }

    public static boolean totalOrderUI(int ageDiscount, int extrasPrice, int drinksPrice) throws InterruptedException {
        boolean tryAgain = false;
        int TOASTPRICE = 15;
        int totalPrice = TOASTPRICE + ageDiscount + extrasPrice + drinksPrice;
        System.out.println("Total for your toast: $" + totalPrice);
        activeTextLabel.setText("<html><font color='green'>Total for your toast: $" + totalPrice + "</font><font color='navy'>\t[Do you want to order again? (y/n)]</font></html>");

        synchronized (lock) { // LOCK
            while (true) { // LOCK
                lock.wait(); // Wait until notify() is called
                char answer = submitButtonResult.charAt(0);
                if (answer == 'y' || answer == 'Y') {
                    tryAgain = true;
                    systemTextLabel.setVisible(false);
                }

                return tryAgain;
            }
        }
    }



}
