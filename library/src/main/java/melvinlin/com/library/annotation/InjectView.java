package melvinlin.com.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) //該註解作用在什麼之上： 類、方法、屬性等等
@Retention(RetentionPolicy.RUNTIME) //在JVM運行時通過反射獲取註解的值
public @interface InjectView {
    int value();
}
