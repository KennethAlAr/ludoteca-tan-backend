package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.reservation.model.Reservation;
import com.ccsw.tutorial.reservation.model.ReservationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationTest {

    public static final Long EXISTS_RESERVATION_ID = 1L;
    public static final Long NOT_EXISTS_RESERVATION_ID = 0L;
    public static final Long NEW_RESERVATION_GAME_ID = 2L;
    public static final Long NEW_RESERVATION_CLIENT_ID = 2L;
    public static final LocalDate NEW_RESERVATION_START_DATE = LocalDate.parse("2026-09-15");
    public static final LocalDate NEW_RESERVATION_END_DATE = LocalDate.parse("2026-09-25");
    public static final LocalDate START_DATE_AFTER_END_DATE = LocalDate.parse("2026-09-26");
    public static final LocalDate END_DATE_OVER_MAX_DURATION = LocalDate.parse("2026-09-30");

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private GameService gameService;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Test
    public void getExistsReservationIdShouldReturnReservation() {

        Reservation reservation = mock(Reservation.class);
        when(reservation.getId()).thenReturn(EXISTS_RESERVATION_ID);
        when(reservationRepository.findById(EXISTS_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        Reservation reservationResponse = reservationService.get(EXISTS_RESERVATION_ID);

        assertNotNull(reservationResponse);

        assertEquals(EXISTS_RESERVATION_ID, reservationResponse.getId());
    }

    @Test
    public void getNotExistsReservationIdShouldReturnNotFound() {

        when(reservationRepository.findById(NOT_EXISTS_RESERVATION_ID)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> reservationService.get(NOT_EXISTS_RESERVATION_ID));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    public void saveNotExistsReservationIdShouldInsert() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGame(gameDto);
        reservationDto.setClient(clientDto);
        reservationDto.setStartDate(NEW_RESERVATION_START_DATE);
        reservationDto.setEndDate(NEW_RESERVATION_END_DATE);

        Game game = new Game();
        game.setId(NEW_RESERVATION_GAME_ID);

        Client client = new Client();
        client.setId(NEW_RESERVATION_CLIENT_ID);

        when(gameService.get(NEW_RESERVATION_GAME_ID)).thenReturn(game);
        when(clientService.get(NEW_RESERVATION_CLIENT_ID)).thenReturn(client);

        ArgumentCaptor<Reservation> reservation = ArgumentCaptor.forClass(Reservation.class);

        reservationService.save(null, reservationDto);

        verify(reservationRepository).save(reservation.capture());

        assertEquals(NEW_RESERVATION_GAME_ID, reservation.getValue().getGame().getId());
        assertEquals(NEW_RESERVATION_CLIENT_ID, reservation.getValue().getClient().getId());
        assertEquals(NEW_RESERVATION_START_DATE, reservation.getValue().getStartDate().toString());
        assertEquals(NEW_RESERVATION_END_DATE, reservation.getValue().getEndDate().toString());
    }

    @Test
    public void saveExistsReservationIdShouldUpdate() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGame(gameDto);
        reservationDto.setClient(clientDto);
        reservationDto.setStartDate(NEW_RESERVATION_START_DATE);
        reservationDto.setEndDate(NEW_RESERVATION_END_DATE);

        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(EXISTS_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        reservationService.save(EXISTS_RESERVATION_ID, reservationDto);

        verify(reservationRepository).save(reservation);
    }

    @Test
    public void saveWithEndDateBeforeStartDateShouldReturnBadRequest() {
        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGame(gameDto);
        reservationDto.setClient(clientDto);
        reservationDto.setStartDate(START_DATE_AFTER_END_DATE);
        reservationDto.setEndDate(NEW_RESERVATION_END_DATE);

        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(EXISTS_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> reservationService.save(EXISTS_RESERVATION_ID, reservationDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void saveWithDurationOver14DaysShouldReturnBadRequest() {
        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGame(gameDto);
        reservationDto.setClient(clientDto);
        reservationDto.setStartDate(NEW_RESERVATION_START_DATE);
        reservationDto.setEndDate(END_DATE_OVER_MAX_DURATION);

        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(EXISTS_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> reservationService.save(EXISTS_RESERVATION_ID, reservationDto));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    public void saveWithOverlappingGameReservationShouldReturnConflict() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGame(gameDto);
        reservationDto.setClient(clientDto);
        reservationDto.setStartDate(NEW_RESERVATION_START_DATE);
        reservationDto.setEndDate(NEW_RESERVATION_END_DATE);

        Game game = new Game();
        game.setId(NEW_RESERVATION_GAME_ID);

        Client client = new Client();
        client.setId(NEW_RESERVATION_CLIENT_ID);

        when(gameService.get(NEW_RESERVATION_GAME_ID)).thenReturn(game);
        when(clientService.get(NEW_RESERVATION_CLIENT_ID)).thenReturn(client);

        when(reservationRepository.existsOverlappingReservationByGame(NEW_RESERVATION_GAME_ID, NEW_RESERVATION_START_DATE, NEW_RESERVATION_END_DATE)).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> reservationService.save(null, reservationDto));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    public void saveWithOverlappingClientReservationShouldReturnConflict() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setGame(gameDto);
        reservationDto.setClient(clientDto);
        reservationDto.setStartDate(NEW_RESERVATION_START_DATE);
        reservationDto.setEndDate(NEW_RESERVATION_END_DATE);

        Game game = new Game();
        game.setId(NEW_RESERVATION_GAME_ID);

        Client client = new Client();
        client.setId(NEW_RESERVATION_CLIENT_ID);

        when(gameService.get(NEW_RESERVATION_GAME_ID)).thenReturn(game);
        when(clientService.get(NEW_RESERVATION_CLIENT_ID)).thenReturn(client);

        when(reservationRepository.existsOverlappingReservationByClient(NEW_RESERVATION_CLIENT_ID, NEW_RESERVATION_START_DATE, NEW_RESERVATION_END_DATE)).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> reservationService.save(null, reservationDto));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }

    @Test
    public void deleteExistsReservationIdShouldDelete() throws Exception {

        Reservation reservation = mock(Reservation.class);
        when(reservationRepository.findById(EXISTS_RESERVATION_ID)).thenReturn(Optional.of(reservation));

        reservationService.delete(EXISTS_RESERVATION_ID);

        verify(reservationRepository).deleteById(EXISTS_RESERVATION_ID);
    }
}
