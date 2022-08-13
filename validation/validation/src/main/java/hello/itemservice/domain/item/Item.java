package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.parsers.SAXParser;

/*타입 검증
가격, 수량에 문자가 들어가면 검증 오류 처리
필드 검증
상품명: 필수, 공백X
가격: 1000원 이상, 1백만원 이하
수량: 최대 9999
특정 필드의 범위를 넘어서는 검증
가격 * 수량의 합은 10,000원 이상*/
@Data
//오브젝트 오류
//@ScriptAssert(lang="javascript",script ="_this.price * _this.quantity >= 10000")
public class Item {


    private Long id;


    private String itemName;


    private Integer price;


    private Integer quantity;

    /*@NotBlank : 빈값 + 공백만 있는 경우를 허용하지 않는다.
    @NotNull : null 을 허용하지 않는다.
    @Range(min = 1000, max = 1000000) : 범위 안의 값이어야 한다.
    @Max(9999) : 최대 9999까지만 허용한다*/

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
