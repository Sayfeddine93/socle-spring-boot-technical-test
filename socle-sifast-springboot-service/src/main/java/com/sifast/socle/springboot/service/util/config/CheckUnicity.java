package com.sifast.socle.springboot.service.util.config;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.Transactional;

import com.sifast.socle.springboot.service.annotations.Unique;

@Transactional
public class CheckUnicity implements ConstraintValidator<Unique, Object> {

    private Class<?> className;

    private String attributName;

    private Session session;

    @Autowired
    @Qualifier("sessionFactory")
    private LocalSessionFactoryBean localSessionFactory;

    private static final int ZERO = 0;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        boolean isUniqueValue;
        if (countValueOccurrences(this.className, this.attributName, value) == ZERO) {
            isUniqueValue = true;
        } else {
            isUniqueValue = false;
        }
        return isUniqueValue;
    }

    public long countValueOccurrences(Class<?> className, String attributName, Object attributValue) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<?> criteria = criteriaBuilder.createQuery(Object.class);
        Root<?> root = criteria.from(className);
        criteria.multiselect(root);
        criteria.where(criteriaBuilder.equal(root.get(attributName), attributValue));
        return session.createQuery(criteria).getResultList().size();
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.session = localSessionFactory.getObject().openSession();
        this.attributName = constraintAnnotation.attributName();
        this.className = constraintAnnotation.className();
    }

}