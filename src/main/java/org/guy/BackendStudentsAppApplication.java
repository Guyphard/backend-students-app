package org.guy;

import org.guy.entities.Payment;
import org.guy.entities.PaymentStatus;
import org.guy.entities.PaymentType;
import org.guy.entities.Student;
import org.guy.repository.PaymentRepository;
import org.guy.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
public class BackendStudentsAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(BackendStudentsAppApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
		return args -> {
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).code("000123").firstName("Pietro").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).code("000124").firstName("Laura").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).code("000125").firstName("John").build());

			PaymentType[] paymentTypes = PaymentType.values();
			Random random = new Random();
			studentRepository.findAll().forEach(st -> {
				for (int i =0; i < 10; i++) {
					int index = random.nextInt(paymentTypes.length);
					Payment payment = Payment.builder()
							.amount((double) (1000+(int)(Math.random()*10000)))
							.date(LocalDate.now())
							.type(paymentTypes[index])
							.status(PaymentStatus.CREATED)
							.file(UUID.randomUUID().toString())
							.student(st)
							.build();
					paymentRepository.save(payment);
				}
			});
		};
	}
}
