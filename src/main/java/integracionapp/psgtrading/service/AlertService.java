package integracionapp.psgtrading.service;

import integracionapp.psgtrading.model.Alert;
import integracionapp.psgtrading.model.TokenPrice;
import integracionapp.psgtrading.model.User;
import integracionapp.psgtrading.repository.AlertRepository;
import integracionapp.psgtrading.repository.TokenPriceRepository;
import integracionapp.psgtrading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private TokenPriceRepository tokenPriceRepository;


    @Autowired
    private EmailService emailService;


    public void sendAlerts() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Alert> userAlerts = alertRepository.findByUser(user);

            for (Alert alert : userAlerts) {
                TokenPrice price = tokenPriceRepository.findFirstBySymbolOrderByUpdateTimeDesc(alert.getSymbol());
                String symbol = alert.getSymbol().getSymbol();
                String emailSubject = "Nueva alerta para " + symbol;
                Double currentPrice = price.getPrice();
                Double alertAmount = alert.getAmount();

                if (alert.getOperator() == Alert.Operator.LOWER) {
                    if (price.getPrice() < alert.getAmount()) {
                        String emailMessage = "El precio de " + symbol + " ha bajado por debajo de " + alertAmount + ". Ahora es " + currentPrice;
                        emailService.sendEmail(user.getEmail(), emailSubject, emailMessage);
                    }
                } else if (price.getPrice() > alert.getAmount()) {
                    String emailMessage = "El precio de " + symbol + " ha superado el valor de " + alertAmount + ". Ahora es " + currentPrice;
                    emailService.sendEmail(user.getEmail(), emailSubject, emailMessage);
                }
            }
        }
    }
}
