package pl.rsz.springproj.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.rsz.springproj.service.PaymentService;

@Controller
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/payment/pay/{orderId}")
    public String showPaymentPage(@PathVariable Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "payment";
    }

    @PostMapping("/payment/process")
    public String processPayment(@RequestParam Long orderId, @RequestParam String method) {
        paymentService.payForOrder(orderId, method);
        return "redirect:/panel";
    }
}