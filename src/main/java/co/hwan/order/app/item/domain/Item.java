package co.hwan.order.app.item.domain;

import co.hwan.order.app.common.abstractentity.Timestamp;
import co.hwan.order.app.common.exception.InvalidItemStatusException;
import co.hwan.order.app.common.exception.InvalidParamException;
import co.hwan.order.app.common.util.TokenGenerator;
import co.hwan.order.app.item.itemoptiongroup.domain.ItemOptionGroup;
import co.hwan.order.app.item.stock.domain.Stock;
import com.google.common.collect.Lists;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;
import org.apache.commons.lang3.StringUtils;

@ToString
@Getter
@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item extends Timestamp {
    private static final String ITEM_PREFIX = "itm_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemToken;
    private Long partnerId;
    private String itemName;
    private Long itemPrice;

    @Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.PERSIST)
    private List<ItemOptionGroup> itemOptionGroupList = Lists.newArrayList();

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Stock stock;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매준비중"),
        ON_SALE("판매중"),
        END_OF_SALE("판매종료"),
        OUT_OF_STOCK("재고소진");

        private final String description;
    }

    @Builder
    public Item(Long partnerId, String itemName, Long itemPrice) {
        if (partnerId == null) throw new InvalidParamException("Item.partnerId");
        if (StringUtils.isBlank(itemName)) throw new InvalidParamException("Item.itemName");
        if (itemPrice == null) throw new InvalidParamException("Item.itemPrice");

        this.partnerId = partnerId;
        this.itemToken = TokenGenerator.randomCharacterWithPrefix(ITEM_PREFIX);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.status = Status.PREPARE;
        this.stock = new Stock(this.itemToken, 0);
    }

    public void changeOnSale() {
        this.status = Status.ON_SALE;
    }

    public void changeEndOfSale() {
        this.status = Status.END_OF_SALE;
    }

    public boolean availableSales() {
        return this.status == Status.ON_SALE;
    }

    public Stock getStock() {
        if(this.stock == null) {
            Stock stock = new Stock(this.itemToken, 0);
            this.stock = stock;
            return stock;
        } else {
            return this.stock;
        }
    }

    public void addItemOptionGroup(List<ItemOptionGroup> itemOptionGroups) {
        this.itemOptionGroupList = itemOptionGroups;
    }

    public void checkOnSale() {
        if(this.status == Status.END_OF_SALE) {
            throw new InvalidItemStatusException();
        }
    }
}