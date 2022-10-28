package com.easyticket.corebusiness.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Customers")
@Data
public class CustomerModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 150)
	private String name;
	@Column(nullable = false)
	private LocalDate birthDate;
	@Column(nullable = false, length = 14, unique = true)
	private String documentId;
	@Column(nullable = false, length = 100, unique = true)
	private String email;

}
