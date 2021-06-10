package coms.glqdlt.ex.springevent;

import coms.glqdlt.ex.springevent.payment.PaymentEventPublisher;
import coms.glqdlt.ex.springevent.ship.ShipEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author glqdlt
 */
@SpringBootApplication
public class SpringEventApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SpringEventApplication.class, args);
    }

    @Autowired
    private ShipEventPublisher shipEventPublisher;

    @Autowired
    private PaymentEventPublisher paymentEventPublisher;

    @Override
    public void run(String... args) throws Exception {
        shipEventPublisher.aaa();
        paymentEventPublisher.aaa();
    }
}
