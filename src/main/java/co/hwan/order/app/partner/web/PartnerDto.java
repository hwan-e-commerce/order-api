package co.hwan.order.app.partner.web;

import co.hwan.order.app.partner.domain.Partner;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

public class PartnerDto {
    @Getter
    @Setter
    public static class RegisterRequest {
        private String name;
        private String businessNo;
        private String email;
        @Pattern(regexp = "^[0-9]{3}-[0-9]{3,4}-[0-9]{4}", message = "invalid phone number format")
        @NotBlank(message = "phone number is never empty")
        private String phoneNumber;

        public RegisterRequest(String name, String businessNo, String email, String phoneNumber) {
            this.name = name;
            this.businessNo = businessNo;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public Partner toEntity() {
            return Partner.builder()
                .partnerName(name)
                .businessNo(businessNo)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        }
    }
}
