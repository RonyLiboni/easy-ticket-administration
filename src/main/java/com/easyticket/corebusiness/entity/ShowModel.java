package com.easyticket.corebusiness.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Shows")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShowModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 100)
	private String name;
	@Column(nullable = false)
	private BigDecimal price;
	@ManyToOne (fetch = FetchType.LAZY)
	private AddressModel address;
	@Column(nullable = false)
	private LocalDateTime showDate;
	@Column(nullable = false)
	private Integer ticketCapacity;
	@Builder.Default
	@Column(nullable = false)
	private Integer soldTickets = 0;
	
}
