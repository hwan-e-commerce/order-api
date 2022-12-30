package co.hwan.order.domain.partner;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@Getter
@Builder
@RequiredArgsConstructor
public class PartnerCommand {

    private final String partnerName;
    private final String businessNo;
    private final String email;
    private final String phoneNumber;

    public Partner toEntity() {
        return Partner.builder()
            .partnerName(partnerName)
            .businessNo(businessNo)
            .phoneNumber(phoneNumber)
            .email(email)
            .build();
    }
}
