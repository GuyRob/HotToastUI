package ht;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class ht_Functions {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public static Scanner scan = new Scanner(System.in);

    public static int age(){
        int discount = 0;
        boolean validAge = false;

        while (!validAge) {
            String inpAge = scan.next();

            // Numbers only validation
            if(inpAge.matches("^[0-9]*$")){
                int numAge = Integer.parseInt(inpAge);
                System.out.println(numAge);
                // Valid age validation
                if (numAge < 5 || numAge > 100) {
                    System.out.println(ANSI_RED + "Please enter a valid age! (5-100)" + ANSI_RESET);
                } else {
                    validAge = true;
                    // Discount validation
                    if (numAge >= 15 && numAge <= 18) {
                        System.out.println(ANSI_GREEN + "You deserve $5 youth discount!" + ANSI_RESET);
                        discount = 5;
                    }
                    System.out.println("So you are " + inpAge + " years old,");
                }

            } else {
                System.out.println(ANSI_RED + "Please enter numbers only!" + ANSI_RESET);
            }
        }
        return discount*-1;
    }

    public static int extras (){
        int userExtrasPrice=0;
        char answer = scan.next().charAt(0);
        System.out.println(answer);

        // Ask again - 1
        if (answer !='y' && answer != 'Y'){
            System.out.println(ANSI_YELLOW + "We have a good extras, want to try?" + ANSI_RESET);
            answer = scan.next().charAt(0);
            // Ask again - 2
            if (answer !='y' && answer != 'Y'){
                System.out.println(ANSI_YELLOW + "Listen you must try it, add at least one?" + ANSI_RESET);
                answer = scan.next().charAt(0);
            }
        }
        if(answer == 'y' || answer == 'Y'){
            boolean validExtra = false;
            HashMap<String, Integer> EXTRAS_MENU = createExtrasMenu();

            System.out.println("Which extras do you want?\nWe have: " + EXTRAS_MENU.keySet());

            // consume the newline character left over from the previous input
            scan.nextLine();

            while (!validExtra) {
                String userExtra = scan.nextLine().toLowerCase();
                userExtra = userExtra.replace(" ", "");
                String[] userExtraSplit = userExtra.split(",");

                for (String currentUserExtraSplit : userExtraSplit){
                    if (userExtra.equals("exit")){
                        validExtra = true;
                        break;
                    }
                    for (String currentExtra : EXTRAS_MENU.keySet()) {
                        if (currentUserExtraSplit.equals(currentExtra)) {
                            System.out.println(ANSI_GREEN + currentUserExtraSplit + " added!" + ANSI_RESET);
                            userExtrasPrice += EXTRAS_MENU.get(currentUserExtraSplit);
                            validExtra = true;
                            break;
                        }
                    }

                }

                if (validExtra){
                    System.out.println("Do you want to add another extra?");
                    answer = scan.next().charAt(0);
                    if (answer == 'y' || answer == 'Y'){
                        System.out.println("Which extra you want to add?");
                        validExtra = false;
                        // consume the newline character left over from the previous input
                        scan.nextLine();
                    }
                } else {
                    System.out.println(ANSI_RED + "Your extras cannot be found, please try again!" + ANSI_RESET);
                }
            }

        }
        return userExtrasPrice;
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

    public static int drink (){
        int DRINK_PRICE = 8;
        String[] DRINK_NAMES = {"cola", "fanta"};
        int userDrinksPrice=0;
        char answer = scan.next().charAt(0);
        if (answer !='y' && answer != 'Y'){
            System.out.println(ANSI_YELLOW + "You'll be thirsty, add drink?" + ANSI_RESET);
            answer = scan.next().charAt(0);
        }
        if(answer == 'y' || answer == 'Y'){
            System.out.println("What do you want to drink?\nWe have: " + Arrays.toString(DRINK_NAMES));
            int drinksAmount = 0;
            boolean validDrink = false;
            while (!validDrink) {
                String userDrink = scan.next().toLowerCase();
                for (String currentDrink : DRINK_NAMES) {
                    if (userDrink.equals(currentDrink)) {
                        System.out.println(ANSI_GREEN + userDrink + " added!" + ANSI_RESET);
                        drinksAmount++;
                        validDrink = true;
                        break;
                    }
                }
                if (!validDrink){
                    System.out.println(ANSI_RED + "Please enter a valid one drink!" + ANSI_RESET);
                } else {
                    System.out.println("Do you want to add another drink?");
                    answer = scan.next().charAt(0);
                    if (answer == 'y' || answer == 'Y'){
                        System.out.println("Which drink you want to add?");
                        validDrink = false;
                    }
                }
            }

            userDrinksPrice = drinksAmount * DRINK_PRICE;

        }
        return userDrinksPrice;
    }
}
