package co.hwan.order.infrastructure.partner;

import co.hwan.order.common.exception.InvalidParamException;
import co.hwan.order.domain.partner.Partner;
import co.hwan.order.domain.partner.PartnerStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PartStoreImpl implements PartnerStore {

    private final PartnerRepository partnerRepository;

    @Override
    public void store(Partner partner) {
        if (StringUtils.isEmpty(partner.getPartnerToken())) throw new InvalidParamException("partner.getPartnerToken()");
        if (StringUtils.isEmpty(partner.getPartnerName())) throw new InvalidParamException("partner.getPartnerName()");
        if (StringUtils.isEmpty(partner.getBusinessNo())) throw new InvalidParamException("partner.getBusinessNo()");
        if (StringUtils.isEmpty(partner.getEmail())) throw new InvalidParamException("partner.getEmail()");
        if (partner.getStatus() == null) throw new InvalidParamException("partner.getStatus()");
        partnerRepository.save(partner);
    }
}
