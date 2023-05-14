package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.Alert;
import integracionapp.psgtrading.model.Symbol;
import integracionapp.psgtrading.model.TokenPrice;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.AlertRepository;
import integracionapp.psgtrading.repository.TokenPriceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AlertService {
    private final AlertRepository alertRepository;
    private final TokenPriceRepository tokenPriceRepository;
    private final EmailService emailService;

    public void sendAlerts() {
        List<Alert> allActiveAlerts = alertRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Alert alert : allActiveAlerts) {
            LocalDateTime sentTime = alert.getSentTime();
            if (sentTime != null && sentTime.toLocalDate().equals(currentDate)) {
                continue;
            }
            User user = alert.getUser();
            TokenPrice price = tokenPriceRepository.findFirstBySymbolOrderByUpdateTimeDesc(alert.getSymbol());
            String symbol = alert.getSymbol().getSymbol();
            String emailSubject = "Nueva alerta para " + symbol;
            Double currentPrice = price.getPrice();
            Double alertAmount = alert.getAmount();
            boolean sendEmail = false;
            String emailMessage = null;

            if (alert.getOperator() == Alert.Operator.LOWER) {
                if (price.getPrice() < alert.getAmount()) {
                    emailMessage = "El precio de " + symbol + " ha bajado por debajo de " + alertAmount + ". Ahora es " + currentPrice;
                    sendEmail = true;
                }
            } else if (price.getPrice() > alert.getAmount()) {
                emailMessage = "El precio de " + symbol + " ha superado el valor de " + alertAmount + ". Ahora es " + currentPrice;
                sendEmail = true;
            }
            if (sendEmail) {
                emailService.sendEmail(user.getEmail(), emailSubject, emailMessage);
                alert.setSentTime(LocalDateTime.now());
                alertRepository.save(alert);
            }
        }
    }
    public List<Alert> getCoinAlert(Symbol symbol, User user){
        return alertRepository
                .findBySymbolAndUser(symbol, user);
    }
}
