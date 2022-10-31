package com.easyticket.corebusiness.validation.exist_in_db.should_exist_in_db;

import javax.persistence.EntityManager;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.easyticket.corebusiness.validation.exist_in_db.ExistsInDataBaseValidator;

public class ShouldExistInDataBaseValidator extends ExistsInDataBaseValidator<ShouldExistInDataBase> implements ConstraintValidator<ShouldExistInDataBase, Object> {
    
    public ShouldExistInDataBaseValidator(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
    public void initialize(ShouldExistInDataBase existsInDB) {
        this.entity = existsInDB.entity();
        field = existsInDB.field();
    }

    @Override
    public boolean isValid(Object valueToBeValidated, ConstraintValidatorContext context) {
        return super.isValid(valueToBeValidated);
    }

}
