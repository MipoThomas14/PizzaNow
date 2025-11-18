package edu.ksu.pizzanow.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.ksu.pizzanow.domain.model.Customer;
import edu.ksu.pizzanow.domain.model.Item;
import edu.ksu.pizzanow.domain.model.Order;

public class ReportService {
    private static final DateTimeFormatter RECEIPT_TIME_FORMAT = DateTimeFormatter.ofPattern("H:mm, MM-dd-uuuu"); // 9:00, 11-14-2025
    private static final DateTimeFormatter PLACED_TIME_FORMAT = DateTimeFormatter.ofPattern("MM-dd-uuuu H:mm"); // 11-14-2025 14:03


    public String generateOrderReceipt(Order order) {
        StringBuilder sb = new StringBuilder();

        Customer customer = order.getCustomer();
        LocalDateTime timeCreated = order.getTimeCreated();

        sb.append("PizzaNow - Order Receipt").append(System.lineSeparator()).append(System.lineSeparator());

        sb.append(String.format("Order: %s%n", order.getOrderId()));
        sb.append(String.format("Time: %s%n", formatReceiptTime(timeCreated)));
        sb.append(String.format("Order Type: %s%n", order.getOrderType()));
        sb.append(String.format("Payment: %s%n", order.getPaymentType()));
        sb.append(System.lineSeparator());

        sb.append(String.format("Customer: %s%n", customer.getName()));
        sb.append(String.format("Phone: %s%n", customer.getPhoneNumber()));
        sb.append("-------------------------------------------------------------").append(System.lineSeparator());

        // Header row: Qty | Item | Unit Price | Total
        sb.append(String.format("%-4s %-30s %12s %10s%n","Qty", "Item", "Unit Price", "Total"));

        for (LineItemSummary line : summarizeOrderItems(order.getOrderItems())) {
            sb.append(String.format("%-4d %-30s %12s %10s%n",
                    line.quantity,
                    line.itemName,
                    formatMoney(line.unitPrice),
                    formatMoney(line.totalPrice)));
        }

        sb.append("-------------------------------------------------------------").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(String.format("%-10s %10s%n", "SUBTOTAL:", formatMoney(order.getSubTotal())));
        sb.append(String.format("%-10s %10s%n", "TAX:",       formatMoney(order.getTax())));
        sb.append(String.format("%-10s %10s%n", "TOTAL:",     formatMoney(order.getTotal())));

        sb.append(System.lineSeparator());
        return sb.toString();
    }

    public String generateCompletedOrdersReport(List<Order> completedOrders) {
        return generateCompletedOrdersReport(completedOrders, LocalDateTime.now());
    }

    public String generateCompletedOrdersReport(List<Order> completedOrders, LocalDateTime generatedTime) {
        StringBuilder sb = new StringBuilder();

        sb.append("PizzaNow - Completed Orders").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(String.format("Time Generated: %s%n", formatReceiptTime(generatedTime)));
        sb.append("-------------------------------------------------------------").append(System.lineSeparator());

        // Header: Order ID | Customer | Type | Placed | Total
        sb.append(String.format("%-14s %-15s %-10s %-18s %10s%n","Order ID", "Customer", "Type", "Placed", "Total"));

        double totalRevenue = 0.0;
        for (Order order : completedOrders) {
            Customer customer = order.getCustomer();
            String placed = formatPlacedTime(order.getTimeCreated());
            double total = order.getTotal();
            totalRevenue += total;

            sb.append(String.format("%-14s %-15s %-10s %-18s %10s%n",
                    order.getOrderId(),
                    truncate(customer.getName(), 15),
                    order.getOrderType(),
                    placed,
                    formatMoney(total)));
        }

        sb.append("-------------------------------------------------------------").append(System.lineSeparator()).append(System.lineSeparator());
        sb.append(String.format("Orders Processed: %d%n", completedOrders.size()));
        sb.append(String.format("Total Revenue Generated: %s%n", formatMoney(totalRevenue)));
        sb.append("-------------------------------------------------------------").append(System.lineSeparator());

        return sb.toString();
    }


    private String formatReceiptTime(LocalDateTime time) {
        if (time == null) {
            return "N/A";
        }
        return time.format(RECEIPT_TIME_FORMAT);
    }

    private String formatPlacedTime(LocalDateTime time) {
        if (time == null) {
            return "N/A";
        }
        return time.format(PLACED_TIME_FORMAT);
    }

    private String formatMoney(double value) {
        return String.format("$%.2f", value);
    }

    private String truncate(String s, int maxLen) {
        if (s == null) return "";
        return s.length() <= maxLen ? s : s.substring(0, maxLen);
    }

    private List<LineItemSummary> summarizeOrderItems(List<Item> items) {
        Map<String, LineItemSummary> map = new LinkedHashMap<>();

        for (Item item : items) {
            // Adjust these method names to match your Item/Pizza class
            String name = item.toString();
            double unitPrice = item.getPrice();

            String key = name + "|" + unitPrice;
            LineItemSummary summary = map.get(key);

            if (summary == null) {
                summary = new LineItemSummary(name, unitPrice, 0);
                map.put(key, summary);
            }

            summary.quantity++;
            summary.totalPrice = summary.quantity * summary.unitPrice;
        }

        return List.copyOf(map.values());
    }

    private static class LineItemSummary {
        final String itemName;
        final double unitPrice;
        int quantity;
        double totalPrice;

        LineItemSummary(String itemName, double unitPrice, int quantity) {
            this.itemName = itemName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
            this.totalPrice = unitPrice * quantity;
        }
    }
}