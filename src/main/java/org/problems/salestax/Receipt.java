package org.problems.salestax;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Receipt {
    private List<Item> items;

    public Receipt() {
        items = new ArrayList<>();
    }

    public void addItem(int quantity, String name, BigDecimal price, boolean imported, boolean taxExempt) {
        Item item = new Item(quantity, name, price, imported, taxExempt);
        items.add(item);
    }

    public BigDecimal getTotalSalesTax() {
        BigDecimal totalSalesTax = BigDecimal.ZERO;
        for (Item item : items) {
            totalSalesTax = totalSalesTax.add(item.getTax());
        }
        return totalSalesTax;
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Item item : items) {
            total = total.add(item.getTotalPrice());
        }
        return total;
    }

    public void printReceipt() {
        for (Item item : items) {
            System.out.println(item.toString());
        }
        System.out.println("Sales Taxes: " + getTotalSalesTax());
        System.out.println("Total: " + getTotal());
    }
}
