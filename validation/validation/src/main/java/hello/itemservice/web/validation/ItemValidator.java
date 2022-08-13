package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component //스프링 빈에 등록하기위해서
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        // item == clazz
        // item == subItem
    }

    @Override
    public void validate(Object target, Errors errors) { // Errors는 bindingResult의 부모 클래스이다.
        //다운 캐스팅 해주어야한다 인터페이스자페가 Object target로 되어있기때문데
        Item item = (Item) target;

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            errors.rejectValue("itemName","required");
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() >1000000){
            errors.rejectValue("price","range",new Object[]{1000,10000},null);
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            errors.rejectValue("quantity","max",new Object[]{9999},null);
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                //오브젝트 에러이기 때문에 필드명은 안적어주어도된다. 메소드는 reject()이다.
                errors.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
            }
        }

    }
}
