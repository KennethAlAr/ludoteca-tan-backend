package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.reservation.model.Reservation;
import com.ccsw.tutorial.reservation.model.ReservationDto;
import com.ccsw.tutorial.reservation.model.ReservationSearchDto;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface ReservationService {

    /**
     * Recupera un {@link Reservation} a través de su ID
     *
     * @param id PK de la entidad
     * @return {@link Reservation}
     */
    Reservation get(Long id);

    /**
     * Método para recueprar un listado paginado de {@link Reservation}
     *
     * @param dto dto de la búsqueda
     * @return {@link Page} de {@link Reservation}
     */
    Page<Reservation> findPage(ReservationSearchDto dto);

    /**
     * Recupera las reservas filtrando opcionalmente por juego, cliente y/o fechas de inicio y final de reserva
     *
     * @param title título del juego
     * @param idClient PK del cliente
     * @param startDate fecha de inicio de la reserva
     * @param endDate fecha de final de la reserva
     * @return {@link List} de {@link Reservation}
     */
    /*List<Reservation> find(String title, Long idClient, LocalDate startDate, LocalDate endDate);*/

    /**
     * Guarda o modifica una reserva, dependiendo de si el identificador está o no informado
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    void save(Long id, ReservationDto dto);

    /**
     * Método para crear o actualizar un {@link Reservation}
     *
     * @param id PK de la entidad
     */
    void delete(Long id) throws Exception;

    /**
     * Recupera un listado de reservas {@link Reservation}
     *
     * @return {@link List} de {@link Reservation}
     */
    List<Reservation> findAll();
}
