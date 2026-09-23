package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.reservation.model.Reservation;
import com.ccsw.tutorial.reservation.model.ReservationDto;
import com.ccsw.tutorial.reservation.model.ReservationSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

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

        return this.reservationRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Reservation> findPage(ReservationSearchDto dto) {

        return this.reservationRepository.findAll(dto.getPageable().getPageable());
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
