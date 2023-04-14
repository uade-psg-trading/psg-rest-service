package integracionapp.psgtrading.service;

import integracionapp.psgtrading.dto.PaymenMethod;
import integracionapp.psgtrading.exception.CustomRuntimeException;
import integracionapp.psgtrading.model.Card;
import integracionapp.psgtrading.model.Income;
import integracionapp.psgtrading.model.Location;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentServiceTest {
    @Autowired
    private PaymentService paymentService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void creditCardPayment_OK(){
        Income payment = new Income("4456",200.30, LocalDate.now(),
                PaymenMethod.CREDIT_CARD,"jwick@mail.com");
        Card creditCard = new Card("4456",12,02,"234","Bankcity","",
                "J Wick");
        String email = "jhon@email.com";
        String firstName = "Jhon";
        String lastName = "Wick";
        Integer dni = 11111;
        String pass = "password";
        Location location = new Location("Argentina","Buenos Aires"
                ,"CABA","1188","Av siempre viva 123");
        User mockUser = new User(firstName,lastName,email,pass,dni,location);
        when(userRepository.findByEmailIgnoreCase(any())).thenReturn(Optional.of(mockUser));
        when(userRepository.save(any(User.class))).thenReturn(any(User.class));
        paymentService.creditCardPayment(payment, creditCard);
        Assertions.assertNotNull(mockUser.getBalances());
        Assertions.assertNotNull(mockUser.getBalances());
    }

    @Test
    void creditCardPayment_InvalidCreditCard(){
        User mockUser = new User();
        Income payment = new Income();
        Card creditCard = new Card();
        when(userRepository.findByEmailIgnoreCase(any()))
                .thenReturn(Optional.of(mockUser));
        Assertions.assertThrows(CustomRuntimeException.class,
                ()->paymentService.creditCardPayment(payment, creditCard));
    }
    }
