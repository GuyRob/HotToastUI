package ht;

public class ht_Main {
    public static void main(String[] args) {
        int TOASTPRICE = 15;
        System.out.println("\t\t|\t---------------------------------------\t\t|");
        System.out.println("\t\t|\t   Welcome to Hot Toast restaurant!\t\t\t|");
        System.out.println("\t\t|\tMenu: Toast $15 | Extras: $3-4 | Drink: $8\t|");
        System.out.println("\t\t|\t---------------------------------------\t\t|\n");

        System.out.println("So what is your age? (5-100)");
        int ageDiscount = ht_Functions.age();

        System.out.println("Would you like to add extras? (y/n)");
        int extrasPrice = ht_Functions.extras();

        System.out.println("Would you like to add drinks? (y/n)");

        int drinksPrice = ht_Functions.drink();

        int totalPrice = TOASTPRICE + ageDiscount + extrasPrice + drinksPrice;
        System.out.println("Total for your toast: $" + totalPrice);

    }
}
