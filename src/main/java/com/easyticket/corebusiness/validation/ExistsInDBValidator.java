package com.easyticket.corebusiness.validation;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.util.Assert;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ExistsInDBValidator implements ConstraintValidator<ExistsInDB, Object> {

    private Class<?> entity;
    private String field;

    private final EntityManager entityManager;

    @Override
    public void initialize(ExistsInDB existsInDB) {
        entity = existsInDB.entity();
        field = existsInDB.field();
    }

    @Override
    public boolean isValid(Object valueToBeValidated, ConstraintValidatorContext context) {
        Assert.notNull(entityManager, "The entity manager should not be null");
        String jpql = String.format("select count(1) from %s where %s = :value", entity.getSimpleName(), field);
        
        Long count = entityManager
                .createQuery(jpql, Long.class)
                .setParameter("value", valueToBeValidated)
                .getSingleResult();
        
        if(count.equals(0L)) {
        	return false;
        }
        return true;
    }

}
