package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.common.criteria.SpecificationUtils;
import com.ccsw.tutorial.reservation.model.Reservation;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ReservationSpecification implements Specification<Reservation> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public ReservationSpecification(SearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        Path<?> path = SpecificationUtils.getPath(root, criteria.getKey());
        if (criteria.getValue() != null) {
            if (criteria.getOperation().equalsIgnoreCase(":")) {
                if (path.getJavaType() == String.class) {
                    return builder.like(builder.lower(path.as(String.class)), "%" + criteria.getValue().toString().toLowerCase() + "%");
                } else {
                    return builder.equal(path, criteria.getValue());
                }
            } else if (criteria.getOperation().equalsIgnoreCase(">=")) {
                return builder.greaterThanOrEqualTo(path.as(LocalDate.class), (LocalDate) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<=")) {
                return builder.lessThanOrEqualTo(path.as(LocalDate.class), (LocalDate) criteria.getValue());
            }
        }
        return null;
    }
}
