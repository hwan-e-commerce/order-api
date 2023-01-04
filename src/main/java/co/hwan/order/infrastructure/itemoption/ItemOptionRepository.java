package co.hwan.order.infrastructure.itemoption;

import co.hwan.order.domain.item.itemoption.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> { }
