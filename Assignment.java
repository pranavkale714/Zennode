package demo;
import java.util.Scanner;

public class Assignment {

    private static final String[] PRODUCT_NAMES = {"Product A", "Product B", "Product C"};
    private static final double[] PRODUCT_PRICES = {20.0, 40.0, 50.0};
    private static final double[] DISCOUNT_RULES = {10.0, 5.0, 10.0, 50.0};
    private static final double GIFT_WRAP_FEE_PER_UNIT = 1.0;
    private static final int UNITS_PER_PACKAGE = 10;
    private static final double SHIPPING_FEE_PER_PACKAGE = 5.0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] quantities = new int[PRODUCT_NAMES.length];
        String[] giftWrapStatus = new String[PRODUCT_NAMES.length];

        for (int i = 0; i < PRODUCT_NAMES.length; i++) {
            System.out.print("Enter quantity for " + PRODUCT_NAMES[i] + ": ");
            quantities[i] = scanner.nextInt();

            System.out.print("Is " + PRODUCT_NAMES[i] + " wrapped as a gift? (yes/no): ");
            giftWrapStatus[i] = scanner.next().toLowerCase();
        }

        double subtotal = calculateSubtotal(quantities);
        double discount = calculateDiscount(subtotal, quantities);
        double shippingFee = calculateShippingFee(quantities);
        double giftWrapFee = calculateGiftWrapFee(giftWrapStatus);

        double grandTotal = subtotal - discount + shippingFee + giftWrapFee;

        displayOrderDetails(quantities, subtotal, discount, shippingFee, giftWrapFee, grandTotal);
    }

    private static double calculateSubtotal(int[] quantities) {
        double subtotal = 0;
        for (int i = 0; i < PRODUCT_NAMES.length; i++) {
            subtotal += quantities[i] * PRODUCT_PRICES[i];
        }
        return subtotal;
    }

    private static double calculateDiscount(double subtotal, int[] quantities) {
        double maxDiscount = 0;

        for (int i = 0; i < DISCOUNT_RULES.length; i++) {
            double discountValue = DISCOUNT_RULES[i];

            switch (i) {
                case 0:
                    if (subtotal > 200) {
                        maxDiscount = Math.max(maxDiscount, discountValue);
                    }
                    break;
                case 1:
                    if (quantities[i] > 10) {
                        maxDiscount = Math.max(maxDiscount, PRODUCT_PRICES[i] * quantities[i] * (discountValue / 100));
                    }
                    break;
                case 2:
                    int totalQuantity = 0;
                    for (int quantity : quantities) {
                        totalQuantity += quantity;
                    }
                    if (totalQuantity > 20) {
                        maxDiscount = Math.max(maxDiscount, subtotal * (discountValue / 100));
                    }
                    break;
                case 3:
                    totalQuantity = 0;
                    for (int quantity : quantities) {
                        totalQuantity += quantity;
                    }
                    if (totalQuantity > 30 && quantities[i] > 15) {
                        maxDiscount += (quantities[i] - 15) * PRODUCT_PRICES[i] * (discountValue / 100);
                    }
                    break;
            }
        }

        return maxDiscount;
    }

    private static double calculateShippingFee(int[] quantities) {
        int totalQuantity = 0;
        for (int quantity : quantities) {
            totalQuantity += quantity;
        }
        return Math.ceil(totalQuantity / (double) UNITS_PER_PACKAGE) * SHIPPING_FEE_PER_PACKAGE;
    }

    private static double calculateGiftWrapFee(String[] giftWrapStatus) {
        int totalGiftWrappedUnits = 0;
        for (String status : giftWrapStatus) {
            if (status.equals("yes")) {
                totalGiftWrappedUnits++;
            }
        }
        return totalGiftWrappedUnits * GIFT_WRAP_FEE_PER_UNIT;
    }

    private static void displayOrderDetails(int[] quantities, double subtotal, double discount, double shippingFee, double giftWrapFee, double grandTotal) {
        System.out.println("\nOrder Summary:");

        for (int i = 0; i < PRODUCT_NAMES.length; i++) {
            double totalAmount = PRODUCT_PRICES[i] * quantities[i];
            System.out.println(PRODUCT_NAMES[i] + " - Quantity: " + quantities[i] + ", Total Amount: $" + String.format("%.2f", totalAmount));
        }

        System.out.println("\nSubtotal: $" + String.format("%.2f", subtotal));
        System.out.println("Discount Applied: $" + String.format("%.2f", discount));
        System.out.println("Shipping Fee: $" + String.format("%.2f", shippingFee));
        System.out.println("Gift Wrap Fee: $" + String.format("%.2f", giftWrapFee));
        System.out.println("\nGrand Total: $" + String.format("%.2f", grandTotal));
    }
}
