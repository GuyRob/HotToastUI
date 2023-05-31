package ht;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class ht_UI_LayoutManager {
    /** Layout Manager */
    private static JPanel northFlowLayoutPanel;
    private static JPanel southBorderLayoutPanel;
    private static JPanel centerGridBagLayoutPanel;
    private static JPanel westBoxLayoutPanel;
    private static JPanel eastGridLayoutPanel;

    private static final JButton northButton = new JButton("North Button");
    private static final JButton southButton = new JButton("South Button");
    private static final JButton centerButton = new JButton("Center Button");
    private static final JButton eastButton = new JButton("East Button");

    private static final JButton menuButton1 = new JButton("Menu Item 1");
    private static final JButton menuButton2 = new JButton("Menu Item 2");
    private static final JButton menuButton3 = new JButton("Menu Item 3");
    private static final JButton menuButton4 = new JButton("Menu Item 4");
    private static final JButton menuButton5 = new JButton("Menu Item 5");

    /** JFrame - App */
//    public static JFrame htFrame = new JFrame(); // DELETE?
    public static JLabel activeTextLabel = new JLabel("TEST");
    public static JLabel systemTextLabel = new JLabel("\n\na");


    public static JTextField inputField = new JTextField(20);
    public static JButton submitButton = new JButton("Submit");
    public static String submitButtonResult;

    public static JLayeredPane systemLayeredPane = new JLayeredPane();

    // Declare a shared object for synchronization
    private static final Object lock = new Object();

    /** Functions - Layout Manager */
    public static void initialLayout_Main() {
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

            TestingLayoutManagers();
        });
    }

    public static void TestingLayoutManagers() {
        northFlowLayoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southBorderLayoutPanel = new JPanel(new BorderLayout());
        centerGridBagLayoutPanel = new JPanel(new GridBagLayout());
        eastGridLayoutPanel = new JPanel(new GridLayout(1, 1));
        Box box = Box.createVerticalBox();
        westBoxLayoutPanel = new JPanel();

        // Up - northFlowLayoutPanel
        // Active Text Label
        activeTextLabel.setFont(new Font("Courier", Font.BOLD, 24));
        activeTextLabel.setForeground(Color.BLACK);
        northFlowLayoutPanel.add(activeTextLabel);

        // System Text Label
        systemTextLabel.setFont(new Font("Courier", Font.BOLD, 24));
        systemTextLabel.setForeground(Color.BLACK);
//        systemTextLabel.setVisible(false);
        northFlowLayoutPanel.add(systemTextLabel);

        // Down - southBorderLayoutPanel
        inputField.setBounds(2, 2, 2, 2);
        submitButton.setBounds(2, 2, 2, 2);

        southBorderLayoutPanel.add(submitButton);
        southBorderLayoutPanel.add(inputField);

        submitButton.addActionListener(submitButtonListener);

        southBorderLayoutPanel.setBorder(BorderFactory.createTitledBorder("Please enter input"));

        // Center - Default Frame
        JFrame frame = new JFrame("HOT TOAST");
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
                                    showSystemText("<html><font color='green'>" + currentUserExtraSplit + " added!" + "</font></html>", 3);

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
