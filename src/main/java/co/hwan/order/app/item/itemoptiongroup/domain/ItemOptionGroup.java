package co.hwan.order.app.item.itemoptiongroup.domain;

import co.hwan.order.app.common.abstractentity.Timestamp;
import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.itemoption.domain.ItemOption;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "item_option_groups")
public class ItemOptionGroup extends Timestamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
    private Integer ordering;
    private String itemOptionGroupName;

    @OneToMany(mappedBy = "itemOptionGroup", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<ItemOption> itemOptionList = new ArrayList<>();

    @Builder
    public ItemOptionGroup(
        Item item,
        Integer ordering,
        String itemOptionGroupName
    ) {
        this.item = item;
        this.ordering = ordering;
        this.itemOptionGroupName = itemOptionGroupName;
    }

    public void addOptions(List<ItemOption> itemOptions) {
        this.itemOptionList = itemOptions;
    }
}
