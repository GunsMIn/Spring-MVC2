package hello.itemservice.web.validation.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
/*타입 검증
가격, 수량에 문자가 들어가면 검증 오류 처리
필드 검증
상품명: 필수, 공백X
가격: 1000원 이상, 1백만원 이하
수량: 최대 9999
특정 필드의 범위를 넘어서는 검증
가격 * 수량의 합은 10,000원 이상*/
@Data
public class ItemSaveForm {

    //등록할 때는 id가 필요 없다

    @NotNull
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 10000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;
}
