package com.easyticket.corebusiness.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easyticket.corebusiness.entity.ShowModel;

public interface ShowModelRepository extends JpaRepository<ShowModel, Long> {
	
}
