package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.reservation.model.Reservation;
import com.ccsw.tutorial.reservation.model.ReservationDto;
import com.ccsw.tutorial.reservation.model.ReservationSearchDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ccsw
 *
 */
@Tag(name = "Reservation", description = "API of Reservation")
@RequestMapping(value = "/reservation")
@RestController
@CrossOrigin(origins = "*")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ModelMapper mapper;

    /**
     * Método para recuperar un listado paginado de {@link Reservation}
     *
     * @param dto dto de búsqueda
     * @return {@link Page} de {@link ReservationDto}
     */
    @Operation(summary = "Find page", description = "Method that return a page of Reservations")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<ReservationDto> findPage(@RequestBody ReservationSearchDto dto) {

        Page<Reservation> page = this.reservationService.findPage(dto);

        return new PageImpl<>(
                page.getContent().stream().map(
                        e -> mapper.map(e, ReservationDto.class)
                ).collect(Collectors.toList()),
                page.getPageable(),
                page.getTotalElements());
    }

    /**
     * Método para crear o actualizar un {@link Reservation}
     *
     * @param id PK de la entidad
     * @param dto datos de la entidad
     */
    @Operation(summary = "Save or Update", description = "Method that saves or updates a Reservation")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public void save(@PathVariable(name = "id", required = false) Long id, @RequestBody ReservationDto dto) {

        this.reservationService.save(id, dto);
    }

    /**
     * Método para eliminar un {@link Reservation}
     *
     * @param id PK de la entidad
     */
    @Operation(summary = "Delete", description = "Method that deletes a Reservation")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) throws Exception {

        this.reservationService.delete(id);
    }

    /**
     * Recupera un listado de reservas {@link Reservation}
     *
     * @return {@link List} de {@link ReservationDto}
     */
    @Operation(summary = "Find", description = "Method that return a list of Reservations")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ReservationDto> findAll() {

        List<Reservation> reservations = this.reservationService.findAll();

        return reservations.stream().map(
                e -> mapper.map(e, ReservationDto.class)
        ).collect(Collectors.toList());
    }

}
