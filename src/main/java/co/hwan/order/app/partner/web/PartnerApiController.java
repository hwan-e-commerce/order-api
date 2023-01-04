package co.hwan.order.app.partner.web;

import co.hwan.order.app.partner.service.PartnerService;
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

    private final PartnerService partnerService;

    @PostMapping
    public ResponseEntity<?> regisPartner(@RequestBody @Valid PartnerDto.RegisterRequest registerRequest) {
        partnerService.registerPartner(registerRequest);
        return ResponseEntity.created(URI.create("/api/partner")).build();
    }
}
