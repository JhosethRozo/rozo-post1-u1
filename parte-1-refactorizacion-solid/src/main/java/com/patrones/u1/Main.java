package com.patrones.u1;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderRepository repo = new OrderRepository();
        EmailNotifier notifier = new EmailNotifier();
        TaxCalculator calc = new TaxCalculator(0.19);
        OrderReporter reporter = new OrderReporter();

        // Orden con cliente VIP
        OrderService vipService = new OrderService(
            calc, repo, notifier, new VipDiscount());
        vipService.processOrder("ORD-001", "vip@mail.com",
            List.of(100.0, 200.0, 50.0));

        // Orden con cliente Regular
        OrderService regService = new OrderService(
            calc, repo, notifier, new RegularDiscount());
        regService.processOrder("ORD-002", "reg@mail.com",
            List.of(80.0, 120.0));

        // Orden con sin descuento
        OrderService noDiscService = new OrderService(
            calc, repo, notifier, new NoDiscount());
        noDiscService.processOrder("ORD-003", "regular@mail.com",
            List.of(50.0, 50.0));

        System.out.println();
        reporter.print(repo.findAll());
    }
}
