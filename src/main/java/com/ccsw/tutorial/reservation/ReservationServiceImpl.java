package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.reservation.model.Reservation;
import com.ccsw.tutorial.reservation.model.ReservationDto;
import com.ccsw.tutorial.reservation.model.ReservationSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    GameService gameService;

    @Autowired
    ClientService clientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Reservation get(Long id) {

        return this.reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la reserva"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Reservation> findPage(ReservationSearchDto dto) {

        ReservationSpecification gameSpec = new ReservationSpecification(new SearchCriteria("game.id", ":", dto.getGameId()));

        ReservationSpecification clientSpec = new ReservationSpecification(new SearchCriteria("client.id", ":", dto.getClientId()));

        ReservationSpecification startDateSpec = new ReservationSpecification(new SearchCriteria("startDate", "<=", dto.getDate()));

        ReservationSpecification endDateSpec = new ReservationSpecification(new SearchCriteria("endDate", ">=", dto.getDate()));

        Specification<Reservation> spec = gameSpec.and(clientSpec).and(startDateSpec).and(endDateSpec);

        return this.reservationRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, ReservationDto dto) {

        Reservation reservation;

        if (id == null) {
            reservation = new Reservation();
        } else {
            reservation = this.get(id);
        }

        BeanUtils.copyProperties(dto, reservation, "id", "game", "client");

        reservation.setGame(gameService.get(dto.getGame().getId()));
        reservation.setClient(clientService.get(dto.getClient().getId()));

        this.reservationRepository.save(reservation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if (this.get(id) == null) {
            throw new Exception("Not exists");
        }

        this.reservationRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Reservation> findAll() {

        return (List<Reservation>) this.reservationRepository.findAll();
    }
}
