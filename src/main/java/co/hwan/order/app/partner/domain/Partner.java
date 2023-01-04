package co.hwan.order.app.partner.domain;

import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.common.util.TokenGenerator;
import co.hwan.order.app.common.abstractentity.Timestamp;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "partners")
public class Partner extends Timestamp {
    private static final String PREFIX_PARTNER = "ptn_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String partnerToken;
    private String partnerName;
    private String phoneNumber;
    private String businessNo;
    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    private enum Status {
        ENABLE("활성화"), DISABLE("비활성화");
        private final String description;
    }

    @Builder
    public Partner(String partnerName, String businessNo, String phoneNumber, String email) {
        if (StringUtils.isEmpty(partnerName)) throw new InvalidParamException("empty partnerName");
        if (StringUtils.isEmpty(businessNo)) throw new InvalidParamException("empty businessNo");
        if (StringUtils.isEmpty(phoneNumber)) throw new InvalidParamException("empty partner phone number");
        if (StringUtils.isEmpty(email)) throw new InvalidParamException("empty email");

        this.partnerToken = TokenGenerator.randomCharacterWithPrefix(PREFIX_PARTNER);
        this.partnerName = partnerName;
        this.businessNo = businessNo;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = Status.ENABLE;
    }

    public void enable() {
        this.status = Status.ENABLE;
    }

    public void disable() {
        this.status = Status.DISABLE;
    }
}

