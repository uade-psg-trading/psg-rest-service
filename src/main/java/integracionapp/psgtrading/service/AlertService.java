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

    public void sendAlerts() {
        EmailService emailService = new EmailService();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            List<Alert> userAlerts = alertRepository.findByUser(user);

            for (Alert alert : userAlerts) {
                TokenPrice price = tokenPriceRepository.findFirstBySymbolOrderByUpdateTimeDesc(alert.getSymbol());
                if (alert.getOperator() == Alert.Operator.LOWER) {
                    if (price.getPrice() < alert.getAmount()) {
                        emailService.sendEmail(user.getEmail(),
                                "Nueva alerta para " + alert.getSymbol().getSymbol(),
                                "El precio de " + alert.getSymbol().getSymbol() + "ha bajado por debajo de "
                                        + alert.getAmount().toString() + ". Ahora es " + price.getPrice()
                        );
                    }
                } else if (price.getPrice() > alert.getAmount()) {
                    emailService.sendEmail(user.getEmail(),
                            "Nueva alerta para " + alert.getSymbol().getSymbol(),
                            "El precio de " + alert.getSymbol().getSymbol() + "ha superado el valor de "
                                    + alert.getAmount().toString() + ". Ahora es " + price.getPrice()
                    );
                }
            }
        }
    }
}
