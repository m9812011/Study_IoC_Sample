package melvinlin.com.library.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * OnClick註解的父類
 */
@Target(ElementType.ANNOTATION_TYPE) //作用在註解之上
@Retention(RetentionPolicy.RUNTIME)
public @interface EventBase {

    // 1.setXXXXListener
    String listenerSetter();

    // 2.new View.OnXXXXListener 監聽對象
    Class<?> listenerType();

    // 3.回調最終執行方法OnXXXX()
    String callBackListener();
}
