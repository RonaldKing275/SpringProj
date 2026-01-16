package pl.rsz.springproj.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.rsz.springproj.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}