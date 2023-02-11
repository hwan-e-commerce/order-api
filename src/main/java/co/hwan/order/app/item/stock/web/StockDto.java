package co.hwan.order.app.item.stock.web;

import co.hwan.order.app.item.stock.domain.Stock;
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
        Integer quantity;

        public StockRegisterRequest(String itemToken, Integer quantity) {
            this.itemToken = itemToken;
            this.quantity = quantity;
        }

        public Stock toEntity() {
            return new Stock(
                this.itemToken,
                this.quantity
            );
        }
    }

    @Getter
    static public class StockRegisterResponse {
        private final String itemToken;
        private final Integer quantity;
        private final String createdAt;

        @Builder
        public StockRegisterResponse(
            String itemToken,
            Integer quantity,
            String createdAt
        ) {
            this.itemToken = itemToken;
            this.quantity = quantity;
            this.createdAt = createdAt;
        }
        public static StockRegisterResponse of(String itemToken, Integer stock, String createdAt) {
           return new StockRegisterResponse(itemToken, stock, createdAt);
        }
    }
}
