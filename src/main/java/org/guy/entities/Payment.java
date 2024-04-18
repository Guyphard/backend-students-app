package org.guy.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @ToString @Builder

public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private Double amount;
    private PaymentType type;
    private PaymentStatus status = PaymentStatus.CREATED;
    private String file;
    @ManyToOne
    private Student student;
}
