package co.hwan.order.app.item.itemoption.domain;

import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.common.abstractentity.Timestamp;
import co.hwan.order.app.item.itemoptiongroup.domain.ItemOptionGroup;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item_options")
public class ItemOption extends Timestamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_option_group_id")
    private ItemOptionGroup itemOptionGroup;
    private Integer ordering;
    private String itemOptionName;
    private Long itemOptionPrice;

    @Builder
    public ItemOption(
        ItemOptionGroup itemOptionGroup,
        Integer ordering,
        String itemOptionName,
        Long itemOptionPrice
    ) {
        if (itemOptionGroup == null) throw new InvalidParamException("ItemOption.itemOptionGroup");
        if (ordering == null) throw new InvalidParamException("ItemOption.ordering");
        if (StringUtils.isBlank(itemOptionName)) throw new InvalidParamException("ItemOption.itemOptionName");
        if (itemOptionPrice == null) throw new InvalidParamException("ItemOption.itemOptionPrice");

        this.itemOptionGroup = itemOptionGroup;
        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
        this.itemOptionPrice = itemOptionPrice;
    }
}
