package co.hwan.order.domain.partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PartnerServiceImpl implements PartnerService {

    private final PartnerStore partnerStore;

    @Override
    public void registerPartner(PartnerCommand partnerCommand) {
        partnerStore.store(partnerCommand.toEntity());
    }
}
