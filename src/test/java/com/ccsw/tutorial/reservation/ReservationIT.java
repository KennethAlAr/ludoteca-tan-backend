package com.ccsw.tutorial.reservation;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.reservation.model.ReservationDto;
import com.ccsw.tutorial.reservation.model.ReservationSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/reservation";

    public static final Long DELETE_RESERVATION_ID = 6L;
    public static final Long MODIFY_RESERVATION_ID = 3L;
    public static final Long NEW_RESERVATION_GAME_ID = 2L;
    public static final Long NEW_RESERVATION_CLIENT_ID = 2L;
    public static final LocalDate NEW_RESERVATION_START_DATE = LocalDate.parse("2026-09-15");
    public static final LocalDate NEW_RESERVATION_END_DATE = LocalDate.parse("2026-09-25");

    private static final int TOTAL_RESERVATIONS = 7;
    private static final int PAGE_SIZE = 5;

    private static final Long EXISTS_GAME = 2L;
    private static final Long NOT_EXISTS_GAME = 0L;
    private static final Long EXISTS_CLIENT = 2L;
    private static final Long NOT_EXISTS_CLIENT = 0L;
    private static final LocalDate EXISTS_DATE = LocalDate.parse("2026-09-12");
    private static final LocalDate NOT_EXISTS_DATE = LocalDate.parse("2026-01-01");

    public static final LocalDate START_DATE_AFTER_END_DATE = LocalDate.parse("2026-09-26");
    public static final LocalDate END_DATE_OVER_MAX_DURATION = LocalDate.parse("2026-09-30");
    public static final Long CONFLICT_WITH_RESERVATION_GAME_ID = 2L;
    public static final Long CONFLICT_WITH_RESERVATION_CLIENT_ID = 6L;
    public static final LocalDate CONFLICT_WITH_GAME_RESERVATION_START_DATE = LocalDate.parse("2026-09-10");
    public static final LocalDate CONFLICT_WITH_GAME_RESERVATION_END_DATE = LocalDate.parse("2026-09-15");
    public static final LocalDate CONFLICT_WITH_CLIENT_RESERVATION_START_DATE = LocalDate.parse("2026-09-15");
    public static final LocalDate CONFLICT_WITH_CLIENT_RESERVATION_END_DATE = LocalDate.parse("2026-09-25");

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<ReservationDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<ReservationDto>>() {
    };

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(TOTAL_RESERVATIONS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnRemainingResults() {

        int elementsCount = TOTAL_RESERVATIONS - PAGE_SIZE;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(TOTAL_RESERVATIONS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findWithoutFiltersShouldReturnAllReservations() {

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(null);
        searchDto.setClientId(null);
        searchDto.setDate(null);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(TOTAL_RESERVATIONS, response.getBody().getTotalElements());
    }

    @Test
    public void findExistsGameShouldReturnReservations() {

        int reservationWithFilter = 1;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(EXISTS_GAME);
        searchDto.setClientId(null);
        searchDto.setDate(null);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findExistsClientShouldReturnReservations() {

        int reservationWithFilter = 1;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(null);
        searchDto.setClientId(EXISTS_CLIENT);
        searchDto.setDate(null);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findExistsDateShouldReturnReservations() {

        int reservationWithFilter = 3;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(null);
        searchDto.setClientId(null);
        searchDto.setDate(EXISTS_DATE);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findExistsGameAndClientAndDateShouldReturnReservations() {

        int reservationWithFilter = 1;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(EXISTS_GAME);
        searchDto.setClientId(EXISTS_CLIENT);
        searchDto.setDate(EXISTS_DATE);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsGameShouldReturnEmpty() {

        int reservationWithFilter = 0;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(NOT_EXISTS_GAME);
        searchDto.setClientId(null);
        searchDto.setDate(null);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {

        int reservationWithFilter = 0;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(null);
        searchDto.setClientId(NOT_EXISTS_CLIENT);
        searchDto.setDate(null);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsDateShouldReturnEmpty() {

        int reservationWithFilter = 0;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(null);
        searchDto.setClientId(null);
        searchDto.setDate(NOT_EXISTS_DATE);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsGameAndExistsClientShouldReturnEmpty() {

        int reservationWithFilter = 0;

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setGameId(NOT_EXISTS_GAME);
        searchDto.setClientId(EXISTS_CLIENT);
        searchDto.setDate(null);
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(reservationWithFilter, response.getBody().getContent().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewReservation() {

        long newReservationId = TOTAL_RESERVATIONS + 1;
        long newReservationSize = TOTAL_RESERVATIONS + 1;

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto dto = new ReservationDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(NEW_RESERVATION_START_DATE);
        dto.setEndDate(NEW_RESERVATION_END_DATE);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newReservationSize));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(newReservationSize, response.getBody().getTotalElements());

        ReservationDto reservation = response.getBody().getContent().stream().filter(item -> item.getId().equals(newReservationId)).findFirst().orElse(null);
        assertNotNull(reservation);
        assertEquals(NEW_RESERVATION_GAME_ID, reservation.getGame().getId());
        assertEquals(NEW_RESERVATION_CLIENT_ID, reservation.getClient().getId());
        assertEquals(NEW_RESERVATION_START_DATE, reservation.getStartDate());
        assertEquals(NEW_RESERVATION_END_DATE, reservation.getEndDate());
    }

    @Test
    public void saveWithExistsIdShouldModifyReservation() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto dto = new ReservationDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(NEW_RESERVATION_START_DATE);
        dto.setEndDate(NEW_RESERVATION_END_DATE);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_RESERVATION_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(TOTAL_RESERVATIONS, response.getBody().getTotalElements());

        ReservationDto reservation = response.getBody().getContent().stream().filter(item -> item.getId().equals(MODIFY_RESERVATION_ID)).findFirst().orElse(null);
        assertNotNull(reservation);
        assertEquals(NEW_RESERVATION_GAME_ID, reservation.getGame().getId());
        assertEquals(NEW_RESERVATION_CLIENT_ID, reservation.getClient().getId());
        assertEquals(NEW_RESERVATION_START_DATE, reservation.getStartDate());
        assertEquals(NEW_RESERVATION_END_DATE, reservation.getEndDate());
    }

    @Test
    public void saveWithEndDateBeforeStartDateShouldReturnBadRequest() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto dto = new ReservationDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(START_DATE_AFTER_END_DATE);
        dto.setEndDate(NEW_RESERVATION_END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithDurationOver14DaysShouldReturnBadRequest() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(NEW_RESERVATION_CLIENT_ID);

        ReservationDto dto = new ReservationDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(NEW_RESERVATION_START_DATE);
        dto.setEndDate(END_DATE_OVER_MAX_DURATION);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void saveWithOverlappingGameReservationShouldReturnConflict() {

        GameDto gameDto = new GameDto();
        gameDto.setId(NEW_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CONFLICT_WITH_RESERVATION_CLIENT_ID);

        ReservationDto dto = new ReservationDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(CONFLICT_WITH_GAME_RESERVATION_START_DATE);
        dto.setEndDate(CONFLICT_WITH_GAME_RESERVATION_END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void saveWithOverlappingClientReservationShouldReturnConflict() {

        GameDto gameDto = new GameDto();
        gameDto.setId(CONFLICT_WITH_RESERVATION_GAME_ID);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(CONFLICT_WITH_RESERVATION_CLIENT_ID);

        ReservationDto dto = new ReservationDto();
        dto.setGame(gameDto);
        dto.setClient(clientDto);
        dto.setStartDate(CONFLICT_WITH_CLIENT_RESERVATION_START_DATE);
        dto.setEndDate(CONFLICT_WITH_CLIENT_RESERVATION_END_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void modifyWithNotExistsIdShouldThrowError() {

        long reservationId = TOTAL_RESERVATIONS + 1;

        ReservationDto dto = new ReservationDto();
        dto.setStartDate(NEW_RESERVATION_START_DATE);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + reservationId, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteWithExistsIdShouldDeleteReservation() {

        long newReservationSize = TOTAL_RESERVATIONS - 1;

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + DELETE_RESERVATION_ID, HttpMethod.DELETE, null, Void.class);

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setPageable(new PageableRequest(0, TOTAL_RESERVATIONS));

        ResponseEntity<ResponsePage<ReservationDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response.getBody());
        assertEquals(newReservationSize, response.getBody().getTotalElements());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowError() {

        long deleteReservationId = TOTAL_RESERVATIONS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteReservationId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
