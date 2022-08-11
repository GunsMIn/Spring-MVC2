package hello.itemservice.domain.item;

import lombok.Data;
/*타입 검증
가격, 수량에 문자가 들어가면 검증 오류 처리
필드 검증
상품명: 필수, 공백X
가격: 1000원 이상, 1백만원 이하
수량: 최대 9999
특정 필드의 범위를 넘어서는 검증
가격 * 수량의 합은 10,000원 이상*/
@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
