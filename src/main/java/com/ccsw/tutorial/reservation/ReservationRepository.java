package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.reservation.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * @author ccsw
 *
 */
public interface ReservationRepository extends CrudRepository<Reservation, Long>, JpaSpecificationExecutor<Reservation> {

    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    Page<Reservation> findAll(Specification<Reservation> spec, Pageable pageable);
}
