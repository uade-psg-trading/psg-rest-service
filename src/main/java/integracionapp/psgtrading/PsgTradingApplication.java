package integracionapp.psgtrading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PsgTradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsgTradingApplication.class, args);
	}

}
