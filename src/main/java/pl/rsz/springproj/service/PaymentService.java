package pl.rsz.springproj.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.rsz.springproj.domain.Order;
import pl.rsz.springproj.domain.Payment;
import pl.rsz.springproj.repositories.OrderRepository;
import pl.rsz.springproj.repositories.PaymentRepository;

import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void payForOrder(Long orderId, String method) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Zamówienie nie istnieje"));

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setPaymentMethod(method);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaid(true);

        paymentRepository.save(payment);

        order.setStatus("PAID");
        orderRepository.save(order);
    }
}