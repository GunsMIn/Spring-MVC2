package hello.itemservice.domain.item;

import lombok.Data;

import java.util.List;

@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean open; // 판매여부
    private List<String> regions; // 등록 지역 (view 단에서 멀티체크박스로 만들거라서 List<T>로 만들어주었다
    private ItemType itemType;// enum (BOOK,FOOD,ETC)
    private String deliveryCode; //자바 객체 (FAST:빠른 배송, NOMAL :일반 배송, SLOW : 느린 배송)

    public Item() {;}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
