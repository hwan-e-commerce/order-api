package co.hwan.order.domain.partner;

import co.hwan.order.common.exception.EntityNotFoundException;
import co.hwan.order.infrastructure.partner.PartnerRepository;
import co.hwan.order.interfaces.partner.PartnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public void registerPartner(PartnerDto.RegisterRequest request) {
        Partner partner = request.toEntity();
        partnerRepository.save(partner);
    }

    public Partner findPartnerByToken(String partnerToken) {
        return partnerRepository.findByPartnerToken(partnerToken).orElseThrow(EntityNotFoundException::new);
    }
}
