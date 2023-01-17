package co.hwan.order.app.item.stock.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class StockDto {

    @Setter
    @Getter
    static public class StockRegisterRequest {
        @NotBlank(message = "item token should not blank")
        String itemToken;
        @Min(value = 1, message = "stock quantity must be greater than 0 ")
        int quantity;
    }

    @Getter
    static public class StockRegisterResponse {
        private final Long id;
        private final String itemToken;
        private final int quantity;
        private final String createdAt;

        @Builder
        public StockRegisterResponse(Long stockId, String itemToken, int quantity, String createdAt) {
            this.id = stockId;
            this.itemToken = itemToken;
            this.quantity = quantity;
            this.createdAt = createdAt;
        }

        /**
         * todo
         *  builder로 교체
         */
        public static StockRegisterResponse of(Long id, String itemToken, int stock, String createdAt) {
           return new StockRegisterResponse(id, itemToken, stock, createdAt);
        }
    }
}
