package co.hwan.order.app.partner.repository;

import co.hwan.order.app.partner.domain.Partner;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Optional<Partner> findByPartnerToken(String partnerToken);
}
