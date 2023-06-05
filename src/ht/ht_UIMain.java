package ht;

public class ht_UIMain {
    public static void main(String[] args) throws InterruptedException {
        boolean tryAgain = true;

        ht_UI_LayoutManager.createUI_Main(); // Initial UI

        while (tryAgain) {
            int ageDiscount = ht_UI_LayoutManager.ageUI();
            int extrasPrice = ht_UI_LayoutManager.extrasUI();
            int drinksPrice = ht_UI_LayoutManager.drinkUI();

            tryAgain = ht_UI_LayoutManager.totalOrderUI(ageDiscount, extrasPrice, drinksPrice); // Print total order + ask if try again
        }

    }


}
