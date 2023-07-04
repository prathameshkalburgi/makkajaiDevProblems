package org.problems.salestax;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {
    private int quantity;
    private String name;
    private BigDecimal price;
    private boolean imported;
    private boolean taxExempt;

    public Item(int quantity, String name, BigDecimal price, boolean imported, boolean taxExempt) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
        this.imported = imported;
        this.taxExempt = taxExempt;
    }

    public BigDecimal getTax() {
        BigDecimal taxRate = taxExempt ? BigDecimal.ZERO : new BigDecimal("0.10"); // Basic sales tax rate
        BigDecimal importTaxRate = imported ? new BigDecimal("0.05") : BigDecimal.ZERO; // Import duty tax rate

        BigDecimal tax = price.multiply(taxRate.add(importTaxRate));
        // Round up to the nearest 0.05
        tax = tax.setScale(2, RoundingMode.UP);
        tax = tax.divide(new BigDecimal("0.05"), 0, RoundingMode.UP).multiply(new BigDecimal("0.05"));
        return tax;
    }

    public BigDecimal getTotalPrice() {
        return price.add(getTax()).multiply(new BigDecimal(quantity));
    }

    public String toString() {
        return quantity + " " + (imported ? "imported " : "") + name + ": " + getTotalPrice();
    }
}
