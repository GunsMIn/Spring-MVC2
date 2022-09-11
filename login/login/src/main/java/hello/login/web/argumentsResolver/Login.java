package hello.login.web.argumentsResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) //타겟
@Retention(RetentionPolicy.RUNTIME) // 실제 동작할 때까지 에노테니션이 남아 있어야하기깨문에
public @interface Login {
}
