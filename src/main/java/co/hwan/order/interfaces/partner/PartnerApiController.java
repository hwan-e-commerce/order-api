package co.hwan.order.interfaces.partner;

import co.hwan.order.application.partner.PartnerFacade;
import co.hwan.order.interfaces.partner.PartnerDto.RegisterRequest;
import java.net.URI;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RequestMapping("/api/partner")
@RequiredArgsConstructor
@RestControllerAdvice
public class PartnerApiController {

    private final PartnerFacade partnerFacade;

    @PostMapping
    public ResponseEntity<?> regisPartner(@RequestBody @Valid RegisterRequest registerRequest) {
        partnerFacade.registPartner(registerRequest.toCommand());
        return ResponseEntity.created(URI.create("/api/partner")).build();
    }
}
