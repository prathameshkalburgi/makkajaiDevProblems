package org.problems.salestax;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        Receipt receipt1 = new Receipt();
        receipt1.addItem(1, "book", new BigDecimal("12.49"), false, true);
        receipt1.addItem(1, "music CD", new BigDecimal("14.99"), false, false);
        receipt1.addItem(1, "chocolate bar", new BigDecimal("0.85"), false, true);
        System.out.println("Output 1:");
        receipt1.printReceipt();

        Receipt receipt2 = new Receipt();
        receipt2.addItem(1, "imported box of chocolates", new BigDecimal("10.00"), true, true);
        receipt2.addItem(1, "imported bottle of perfume", new BigDecimal("47.50"), true, false);
        System.out.println("\nOutput 2:");
        receipt2.printReceipt();

        Receipt receipt3 = new Receipt();
        receipt3.addItem(1, "imported bottle of perfume", new BigDecimal("27.99"), true, false);
        receipt3.addItem(1, "bottle of perfume", new BigDecimal("18.99"), false, false);
        receipt3.addItem(1, "packet of headache pills", new BigDecimal("9.75"), false, true);
        receipt3.addItem(1, "box of imported chocolates", new BigDecimal("11.25"), true, true);
        System.out.println("\nOutput 3:");
        receipt3.printReceipt();
    }
}