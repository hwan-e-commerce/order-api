package co.hwan.order.app.stock.web;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
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

        private StockRegisterResponse(Long id, String itemToken, int stock, String createdAt) {
            this.id = id;
            this.itemToken = itemToken;
            this.quantity = stock;
            this.createdAt = createdAt;
        }

        public static StockRegisterResponse of(Long id, String itemToken, int stock, String createdAt) {
           return new StockRegisterResponse(id, itemToken, stock, createdAt);
        }
    }
}
