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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/validation/v2/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    //이 클래스안에 있는 어떠한 컨트롤러가 호출될때마다 적용된다.
    @InitBinder
    public void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(itemValidator);
    }


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



    /*타임리프 스프링 검증 오류 통합 기능
        타임리프는 스프링의 BindingResult 를 활용해서 편리하게 검증 오류를 표현하는 기능을 제공한다.
        #fields : #fields 로 BindingResult 가 제공하는 검증 오류에 접근할 수 있다.
        th:errors : 해당 필드에 오류가 있는 경우에 태그를 출력한다. th:if 의 편의 버전이다.
        th:errorclass : th:field 에서 지정한 필드에 오류가 있으면 class 정보를 추가한다.*/

    //검증 버전 1 (버전이 올라갈수록 기능추가)
    //@PostMapping("/add")                   //bindingResult는 항상 검증 대상 뒤에 위치시킨다.
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
           bindingResult.addError(new FieldError("item","itemName","상품 이름은 필수 입니다"));
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000까지 허용합니다"));
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다"));
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){                 //globalError는 적어줄 필드명이 없다.
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

    ///////잘못된 값 입력 유지 검증 추가(버전 2)
    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Model model) {

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,null,null,"상품 이름은 필수 입니다"));
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000  ){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,null,null,"가격은 1,000 ~ 1,000,000까지 허용합니다"));
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,null,null,"수량은 최대 9,999 까지 허용합니다"));
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item",null,null, "가격 * 수량의 합은10000원 이상이어야 합니다. 현재값 :" + resultPrice +"원"));
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


    // 나타낼 오류메세지를 errors.properties에 변수로 담아 두었고 그전에 application.properties에서 등록해줘야 하다.
    // spring.messages.basename=messages.errors 이렇게 등록해줘야하고
    // 4번째는 메세지를 넣기위해 문자배열로 5번째는 값을 바인딩해주기위해서 Object[] 배열로 값을 넣어준다.
    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){                                                                               //배열이라서 첫번째 배열을 찾지못하면 두번째 배열로간다
            bindingResult.addError(new FieldError("item","itemName",item.getItemName(),false,new String[]{"required.item.itemName","required.default"},null,null));
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() > 1000000  ){
            bindingResult.addError(new FieldError("item","price",item.getPrice(),false,new String[]{"range.item.price"},new Object[]{1000,10000},null));
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            bindingResult.addError(new FieldError("item","quantity",item.getQuantity(),false,new String[]{"max.item.quantity"},new Object[]{9999}, null ));
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                bindingResult.addError(new ObjectError("item",new String[]{"totalPriceMin"},new Object[]{10000,resultPrice}, null));
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

    //rejectValue를 사용
    //@PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

        //검증 로직
        if(!StringUtils.hasText(item.getItemName())){
            bindingResult.rejectValue("itemName","required");
        }
        if (item.getPrice()==null || item.getPrice() < 1000 || item.getPrice() >1000000){
            bindingResult.rejectValue("price","range",new Object[]{1000,10000},null);
        }
        if(item.getQuantity()==null || item.getQuantity()>=9999){
            bindingResult.rejectValue("quantity","max",new Object[]{9999},null);
        }
        //특정 필드가 아닌 복합 룰 검증
        if(item.getQuantity()!=null & item.getQuantity()!=null){
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice<10000){
                //오브젝트 에러이기 때문에 필드명은 안적어주어도된다. 메소드는 reject()이다.
                bindingResult.reject("totalPriceMin",new Object[]{10000,resultPrice},null);
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


    //검증로직을 빼준 코드
   //@PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

       /* if(itemValidator.supports(item)){

        }*/

        itemValidator.validate(item,bindingResult);
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


    //이 버전은 31번째줄 먼저 제작
    @PostMapping("/add")    //@Validated 를 넣어주어야한다.  그래야 Item에대해서 자동으로 검증해준다.
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {

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






}

