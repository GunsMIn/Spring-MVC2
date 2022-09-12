package hello.typeconverter.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.test.context.TestPropertySource;

public class ConversionServiceTest {

    @Test
    void conversionServic(){
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToIntegerConverter());
        conversionService.addConverter(new IntegerToStringConaverter());
        conversionService.addConverter(new IpPortToStringConverter());
        conversionService.addConverter(new StringToIpPortConverter());

        Integer result = conversionService.convert("10", Integer.class);
        System.out.println("result = " + result);
        Assertions.assertThat(10).isEqualTo(result);
    }
}
