package co.hwan.order.infrastructure.partner;

import co.hwan.order.domain.partner.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Long> {

}
