package com.easyticket.corebusiness.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easyticket.corebusiness.entity.CustomerModel;

public interface CustomerModelRepository extends JpaRepository<CustomerModel, Long> {

}
