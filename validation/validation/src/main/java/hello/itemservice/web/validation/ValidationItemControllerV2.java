package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v2/addForm";
    }

    /*타입 검증
    가격, 수량에 문자가 들어가면 검증 오류 처리
    필드 검증
    상품명: 필수, 공백X
    가격: 1000원 이상, 1백만원 이하
    수량: 최대 9999
    특정 필드의 범위를 넘어서는 검증
    가격 * 수량의 합은 10,000원 이상*/

    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
           bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다"));
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000  ){
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000까지 허용합니다"));
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다"));
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은10000원 이상이어야 합니다. 현재값 :" + resultPrice +"원"));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if(bindingResult.hasErrors()){
            log.info("errors={}",bindingResult);
            //bindingResult는 자동으로 view에 같이 넘어간다.
            return "validation/v2/addForm";
        }

        //비지니스 로직(errors에 안걸린 성공로직)
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }



    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v2/items/{itemId}";
    }




    /* @PostMapping("/add")
    public String add2(@ModelAttribute Item item,RedirectAttributes redirectAttributes,Model model){
        Map<String, String> errors = new HashMap<>();
        if(!StringUtils.hasText(item.getItemName())){
            errors.put("itemName", "상품명을 추가해주세요");
        }
        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>1000000){
            errors.put("price", "가격을 정확하게~");
        }
        if (item.getQuantity()==null||item.getQuantity()>9999){
            errors.put("quantity","수량을 정확하게~");
        }
        if(item.getQuantity()!=null && item.getPrice()!=null){
            errors.put("globalError", "가격과 수량의 곱은 100000원이상이어야한다");
        }

        if(!errors.isEmpty()){
            log.info("에러내용 ={}",errors);
            model.addAttribute("errors", errors);
            return "validation/v1/addForm";
        }


        //구현 성공 로직
       Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }*/

}
