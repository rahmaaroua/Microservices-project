package tn.univ.ReservationMicroservice;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableScheduling
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableDiscoveryClient
public class ReservationMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationMicroserviceApplication.class, args);
    }

}