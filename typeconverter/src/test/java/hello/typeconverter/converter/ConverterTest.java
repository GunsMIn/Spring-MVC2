package hello.typeconverter.converter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ConverterTest {

    @Test
    public void stringToInteger() throws Exception {
        //given
        StringToIntegerConverter converter = new StringToIntegerConverter();
        Integer convert = converter.convert("10");
        Assertions.assertThat(convert).isEqualTo(10);
    }
    @Test
    public void integerToString() throws Exception {
        //given
        IntegerToStringConaverter conaverter = new IntegerToStringConaverter();
        String convert = conaverter.convert(10);
        Assertions.assertThat("10").isEqualTo(convert);

    }


}
