package com.easyticket.corebusiness.validation.exist_in_db;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public abstract class ExistsInDataBaseValidator<T> {
	
	protected Class<?> entity;
	protected String field;
	protected final EntityManager entityManager;
	
	public abstract void initialize(T existsInDB);
	
	public boolean isValid(Object valueToBeValidated) {
		Assert.notNull(entityManager, "The entity manager should not be null");
		String jpql = String.format("select count(1) from %s where %s = :value", entity.getSimpleName(), field);

		Long count = entityManager.createQuery(jpql, Long.class).setParameter("value", valueToBeValidated)
				.getSingleResult();

		if (count.equals(0L)) {
			return false;
		}
		return true;
	}
}
