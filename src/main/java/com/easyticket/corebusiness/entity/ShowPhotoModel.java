package com.easyticket.corebusiness.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Show_Photos")
public class ShowPhotoModel {

	@EqualsAndHashCode.Include
	@Id
	@Column(name = "show_photo_id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "show_photo_id")
	@MapsId
	private ShowModel show;
	
	private String fileName;
	private String description;
	private String contentType;
	private Long fileSize;
		
}