package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    /*타입 검증
    가격, 수량에 문자가 들어가면 검증 오류 처리
    필드 검증
    상품명: 필수, 공백X
    가격: 1000원 이상, 1백만원 이하
    수량: 최대 9999
    특정 필드의 범위를 넘어서는 검증
    가격 * 수량의 합은 10,000원 이상*/

    //bindingResult를 사용하지 않은 에러 처리
    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes,Model model) {
        //검증 오류 결과를 보관
        Map<String, String> errors = new HashMap<>();
        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            /*StringUtils.hasText->값이 있을 경우에는 true를 반환 공백이나 NULL이 들어올 경우에는 false를 반환*/
            errors.put("itemName", "상품 이름은 필수 입니다 ");
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000  ){
            errors.put("price", "가격은 1,000 ~ 1,000,000까지 허용합니다");
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다");
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                errors.put("globalError", "가격 * 수량의 합은10000원 이상이어야 합니다. 현재값 :" + resultPrice +"원");
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if(!errors.isEmpty()){
            log.info("errors={}",errors);
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }

        //비지니스 로직(errors에 안걸린 성공로직)
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }



    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }


}

