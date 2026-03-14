package com.cafeteria.system;

import java.util.List;

public abstract class Report {
    protected List<Order> orders;

    public Report(List<Order> orders) {
        this.orders = orders;
    }

    public abstract String generateDailyReport();
    public abstract String generateMonthlyReport();
    public abstract String generateTopItemsReport();
    public abstract void exportReport(String format);
}
