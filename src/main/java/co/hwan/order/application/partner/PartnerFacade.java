package co.hwan.order.application.partner;

import co.hwan.order.domain.partner.PartnerCommand;
import co.hwan.order.domain.partner.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerFacade {

    private final PartnerService partnerService;

    public void registPartner(PartnerCommand partnerCommand) {
        partnerService.registerPartner(partnerCommand);
    }
}
