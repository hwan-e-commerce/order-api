package co.hwan.order.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import co.hwan.order.app.item.domain.Item;
import co.hwan.order.app.item.repository.ItemRepository;
import co.hwan.order.app.partner.domain.Partner;
import co.hwan.order.app.partner.repository.PartnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private PartnerRepository partnerRepository;

    private Partner savedPartner;
    private Item savedTestItem01;

    @BeforeEach
    void setUp() {
        Partner partner = Partner.builder()
            .phoneNumber("010-123-1234")
            .email("test@mail.com")
            .partnerName("Brand C")
            .businessNo("123402313")
            .build();

         savedPartner = partnerRepository.save(partner);

        Item testItem01 = Item.builder()
            .partnerId(savedPartner.getId())
            .itemPrice(12000L)
            .itemName("T shirt-02").build();

        savedTestItem01 = itemRepository.save(testItem01);
    }

    @AfterEach
    void clear() {
        partnerRepository.deleteById(savedPartner.getId());
        itemRepository.deleteById(savedTestItem01.getId());
    }

    @Test
    void lock_test() {
        System.out.println("============ delete test start ============");
        Item item = itemRepository.findWithPessimisticLockByItemToken(savedTestItem01.getItemToken()).get();
        assertThat(item).isNotNull();
        System.out.println("============ delete test done ============");
    }
}
