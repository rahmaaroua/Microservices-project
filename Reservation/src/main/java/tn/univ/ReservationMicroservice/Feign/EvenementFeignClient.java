package tn.univ.ReservationMicroservice.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import tn.univ.ReservationMicroservice.Dto.EvenementDto;

@FeignClient(name = "evenement-service", url = "http://localhost:8082") // ✅ CHANGÉ À 8082
public interface EvenementFeignClient {

    @GetMapping("/Evenement/{id}")
    EvenementDto getEvenementById(@PathVariable("id") int id);
}
