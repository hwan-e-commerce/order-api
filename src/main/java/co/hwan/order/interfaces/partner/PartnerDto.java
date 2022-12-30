package co.hwan.order.interfaces.partner;

import co.hwan.order.domain.partner.PartnerCommand;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
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

        @Builder
        private RegisterRequest(String name, String businessNo, String email, String phoneNumber) {
            this.name = name;
            this.businessNo = businessNo;
            this.email = email;
            this.phoneNumber = phoneNumber;
        }

        public static RegisterRequest of(String name, String businessNo, String email, String phoneNumber) {
            return RegisterRequest.builder()
                .name(name)
                .businessNo(businessNo)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        }

        public PartnerCommand toCommand() {
            return PartnerCommand.builder()
                .partnerName(name)
                .businessNo(businessNo)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        }
    }
}
