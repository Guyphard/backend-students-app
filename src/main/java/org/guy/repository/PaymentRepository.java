package org.guy.repository;

import org.guy.entities.Payment;
import org.guy.entities.PaymentStatus;
import org.guy.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
   List<Payment> findByStudentCode(String Code);
   List<Payment> findByStatus(PaymentStatus status);
}
