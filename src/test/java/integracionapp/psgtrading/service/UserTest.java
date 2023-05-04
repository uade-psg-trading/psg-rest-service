package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserTest {

    @Mock
    private Tenant mockTenant;

    @Mock
    private Symbol mockSymbol;

    @InjectMocks
    private User user;

    @Test
    void testSetAndGetId() {
        // Arrange
        long expectedId = 123;

        // Act
        user.setId(expectedId);
        long actualId = user.getId();

        // Assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void testSetAndGetTenant() {
        // Arrange
        Tenant expectedTenant = mockTenant;

        // Act
        user.setTenant(expectedTenant);
        Tenant actualTenant = user.getTenant();

        // Assert
        assertEquals(expectedTenant, actualTenant);
    }

    @Test
    void testSetAndGetFirstName() {
        // Arrange
        String expectedFirstName = "John";

        // Act
        user.setFirstName(expectedFirstName);
        String actualFirstName = user.getFirstName();

        // Assert
        assertEquals(expectedFirstName, actualFirstName);
    }

    @Test
    void testSetAndGetLastName() {
        // Arrange
        String expectedLastName = "Doe";

        // Act
        user.setLastName(expectedLastName);
        String actualLastName = user.getLastName();

        // Assert
        assertEquals(expectedLastName, actualLastName);
    }

    @Test
    void testSetAndGetEmail() {
        // Arrange
        String expectedEmail = "john.doe@example.com";

        // Act
        user.setEmail(expectedEmail);
        String actualEmail = user.getEmail();

        // Assert
        assertEquals(expectedEmail, actualEmail);
    }

    @Test
    void testSetAndGetPassword() {
        // Arrange
        String expectedPassword = "mypassword";

        // Act
        user.setPassword(expectedPassword);
        String actualPassword = user.getPassword();

        // Assert
        assertEquals(expectedPassword, actualPassword);
    }

    @Test
    void testSetAndGetDni() {
        // Arrange
        Integer expectedDni = 12345678;

        // Act
        user.setDni(expectedDni);
        Integer actualDni = user.getDni();

        // Assert
        assertEquals(expectedDni, actualDni);
    }

    @Test
    void testSetAndGetLocation() {
        // Arrange
        Location expectedLocation = mock(Location.class);

        // Act
        user.setLocation(expectedLocation);
        Location actualLocation = user.getLocation();

        // Assert
        assertEquals(expectedLocation, actualLocation);
    }

    @Test
    void testSetAndGetExternalIdentifier() {
        // Arrange
        String expectedExternalIdentifier = "abcd1234";

        // Act
        user.setExternalIdentifier(expectedExternalIdentifier);
        String actualExternalIdentifier = user.getExternalIdentifier();

        // Assert
        assertEquals(expectedExternalIdentifier, actualExternalIdentifier);
    }

    @Test
    void testSetAndGetTransactions() {
        // Arrange
        Set<Transaction> expectedTransactions = new HashSet<>();
        expectedTransactions.add(mock(Transaction.class));

        // Act
        user.setTransactions(expectedTransactions);
        Set<Transaction> actualTransactions = user.getTransactions();

        // Assert
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    void testSetAndGetBalances() {
        // create mock objects

        // create Balance objects
        Balance balance1 = new Balance();
        balance1.setAmount(100.0);
        balance1.setSymbol(mockSymbol);
        balance1.setUser(user);

        Balance balance2 = new Balance();
        balance2.setAmount(200.0);
        balance2.setSymbol(mockSymbol);
        balance2.setUser(user);

        // add balances to user
        Set<Balance> balances = new HashSet<>();
        balances.add(balance1);
        balances.add(balance2);
        user.setBalances(balances);

        // verify that balances were set correctly
        assertEquals(balances, user.getBalances());
    }
}
