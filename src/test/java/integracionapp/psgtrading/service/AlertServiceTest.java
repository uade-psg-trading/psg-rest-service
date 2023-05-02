package integracionapp.psgtrading.service;

import integracionapp.psgtrading.controller.AlertController;
import integracionapp.psgtrading.dto.AlertDTO;
import integracionapp.psgtrading.dto.GenericDTO;
import integracionapp.psgtrading.dto.JWTObjectDTO;
import integracionapp.psgtrading.model.Alert;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.AlertRepository;
import integracionapp.psgtrading.repository.SymbolRepository;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class AlertServiceTest {

    @InjectMocks
    private Alert alert;

    @Mock
    private Symbol symbol;

    @Mock
    private User user;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private SymbolRepository symbolRepository;
    @MockBean
    private AlertRepository alertRepository;
    @MockBean
    private JwtService jwtService;

    @Test
    void testGettersAndSetters() {
        Long alertId = 1L;
        Double amount = 2.5;
        Alert.Operator operator = Alert.Operator.LOWER;
        LocalDateTime updateTime = LocalDateTime.now();

        alert.setAlertId(alertId);
        alert.setAmount(amount);
        alert.setOperator(operator);
        alert.setUpdateTime(updateTime);
        alert.setSymbol(symbol);
        alert.setUser(user);

        assertEquals(alertId, alert.getAlertId());
        assertEquals(amount, alert.getAmount());
        assertEquals(operator, alert.getOperator());
        assertEquals(updateTime, alert.getUpdateTime());
        assertEquals(symbol, alert.getSymbol());
        assertEquals(user, alert.getUser());
    }


    @Test
    void testGetUserAlerts() {
        User mockUser = Mockito.mock(User.class);
        JWTObjectDTO mockJwtObjectDTO = Mockito.mock(JWTObjectDTO.class);
        List<Alert> mockAlerts = new ArrayList<>();

        when(jwtService.decodeJWT(anyString())).thenReturn(mockJwtObjectDTO);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(alertRepository.findByUser(mockUser)).thenReturn(mockAlerts);

        AlertController alertController = new AlertController(userRepository, jwtService, alertRepository,
                symbolRepository);
        ResponseEntity<GenericDTO<List<Alert>>> response = alertController.getUserAlerts("Bearer token");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAlerts, response.getBody().getData());
    }

    @Test
    void testCreateAlert() {
        User mockUser = Mockito.mock(User.class);
        Symbol mockSymbol = Mockito.mock(Symbol.class);
        JWTObjectDTO mockJwtObjectDTO = Mockito.mock(JWTObjectDTO.class);
        Alert mockAlert = Mockito.mock(Alert.class);

        AlertDTO alertDTO = new AlertDTO();
        alertDTO.setToken("PSG");
        alertDTO.setAmount(2.0);
        alertDTO.setOperator("LOWER");

        when(jwtService.decodeJWT(anyString())).thenReturn(mockJwtObjectDTO);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
        when(symbolRepository.findBySymbol("PSG")).thenReturn(mockSymbol);
        when(alertRepository.save(any(Alert.class))).thenReturn(mockAlert);

        AlertController alertController = new AlertController(userRepository, jwtService, alertRepository,
                symbolRepository);
        ResponseEntity<GenericDTO<Alert>> response = alertController.createAlert("Bearer token", alertDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser, response.getBody().getData().getUser());
        assertEquals(mockSymbol, response.getBody().getData().getSymbol());
        assertEquals(2.0, response.getBody().getData().getAmount());
        assertEquals("LOWER", response.getBody().getData().getOperator().name());
    }


}
