package tn.univ.ReservationMicroservice.Feign;

import org.springframework.stereotype.Component;

@Component
public class EvenementFallback implements EvenementFeignClient {

    @Override
    public EvenementDto getEvenementById(int id) {
        EvenementDto dto = new EvenementDto();
        dto.setNom("Evenement indisponible");
        dto.setDescription("Service Evenement en panne (fallback activé)");
        return dto;
    }


}
