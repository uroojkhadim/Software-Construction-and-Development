package com.cafeteria.system;

import java.util.List;

public class InventoryReport extends Report {

    public InventoryReport(List<Order> orders) {
        super(orders);
    }

    @Override
    public String generateDailyReport() {
        return "Daily inventory consumption report based on orders.";
    }

    @Override
    public String generateMonthlyReport() {
        return "Monthly inventory forecasting report.";
    }

    @Override
    public String generateTopItemsReport() {
        return "Inventory report: Top items consuming ingredients.";
    }

    @Override
    public void exportReport(String format) {
        System.out.println("Exporting inventory report in " + format + " format.");
    }
}
