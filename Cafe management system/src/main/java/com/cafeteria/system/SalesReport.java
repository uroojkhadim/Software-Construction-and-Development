package com.cafeteria.system;

import java.util.*;

public class SalesReport extends Report {

    public SalesReport(List<Order> orders) {
        super(orders);
    }

    @Override
    public String generateDailyReport() {
        double total = 0;
        int count = 0;
        for (Order o : orders) {
            if (o.getStatus() == Status.COMPLETED) {
                total += o.getGrandTotal();
                count++;
            }
        }
        return "--- DAILY SALES REPORT ---\n" +
               "Total Orders: " + count + "\n" +
               "Total Revenue: $" + String.format("%.2f", total) + "\n" +
               "--------------------------";
    }

    @Override
    public String generateMonthlyReport() {
        double total = orders.stream().filter(o -> o.getStatus() == Status.COMPLETED).mapToDouble(Order::getGrandTotal).sum();
        return "--- MONTHLY REVENUE REPORT ---\n" +
               "Projected Revenue based on active sessions: $" + String.format("%.2f", total) + "\n" +
               "------------------------------";
    }

    @Override
    public String generateTopItemsReport() {
        Map<String, Integer> counts = new HashMap<>();
        for (Order o : orders) {
            for (MenuItem item : o.getMenuItems()) {
                counts.put(item.getName(), counts.getOrDefault(item.getName(), 0) + 1);
            }
        }
        
        StringBuilder sb = new StringBuilder("--- BEST SELLING ITEMS ---\n");
        counts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(e -> sb.append(e.getKey()).append(": ").append(e.getValue()).append(" sold\n"));
        sb.append("--------------------------");
        return sb.toString();
    }

    public Map<String, Double> getTopItemsData() {
        Map<String, Double> data = new LinkedHashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        for (Order o : orders) {
            for (MenuItem item : o.getMenuItems()) {
                counts.put(item.getName(), counts.getOrDefault(item.getName(), 0) + 1);
            }
        }
        counts.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(e -> data.put(e.getKey(), e.getValue().doubleValue()));
        return data;
    }

    public Map<String, Double> getRevenueData() {
        Map<String, Double> data = new LinkedHashMap<>();
        double total = orders.stream().filter(o -> o.getStatus() == Status.COMPLETED).mapToDouble(Order::getGrandTotal).sum();
        data.put("Total Revenue", total);
        data.put("Projected", total * 1.2); // Just for visualization
        return data;
    }

    @Override
    public void exportReport(String format) {
        System.out.println("Exporting sales report in " + format + " format.");
    }
}
