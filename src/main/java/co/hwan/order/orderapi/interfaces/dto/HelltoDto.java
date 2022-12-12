package co.hwan.order.orderapi.interfaces.dto;

import lombok.Setter;

@Setter
public class HelltoDto {
    private String message;

    @Override
    public String toString() {
        return ""+message;
    }
}
