package org.guy.service;

import org.guy.entities.Payment;
import org.guy.entities.PaymentStatus;
import org.guy.entities.PaymentType;
import org.guy.entities.Student;
import org.guy.repository.PaymentRepository;
import org.guy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    private StudentRepository studentRepository;

    public PaymentService(PaymentRepository paymentRepository, StudentRepository studentRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
    }

    public Payment savePayment(MultipartFile file,
                               LocalDate date, double amount, PaymentType type, String studentCode) throws IOException {
        Path path = Paths.get(System.getProperty("user.home"), "students-app-files", "payments");
        if(!Files.exists(path)){
            Files.createDirectories(path);
        }
        String fileId = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "students-app-files", "payments", fileId+".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = studentRepository.findByCode(studentCode);
        Payment payment = Payment.builder()
                .type(type)
                .amount(amount)
                .student(student)
                .status(PaymentStatus.CREATED)
                .file(filePath.toUri().toString())
                .build();
        Payment savedPayments = paymentRepository.save(payment);
        return savedPayments;
    }
}
