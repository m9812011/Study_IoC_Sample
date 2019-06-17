package melvinlin.com.library;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import melvinlin.com.library.annotation.ContentView;
import melvinlin.com.library.annotation.EventBase;
import melvinlin.com.library.annotation.InjectView;

/**
 * 注入管理類
 */
public class InjectManager {

    public static void inject(Activity activity) {
        injectLayout(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    /**
     * 佈局的注入
     *
     * @param activity
     */
    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 獲取類之上的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 獲取這個注解的值
            int layoutId = contentView.value();
            // 第一種方式：
            //activity.setContentView(layoutId);
            try {
                Method setContentView = clazz.getMethod("setContentView", int.class);
                // 執行 setContentView(R.layout.activity_main)方法
                setContentView.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 控件的注入
     *
      * @param activity
     */
    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 類的所有屬性
        // Field[] fields = clazz.getFields();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // 獲取屬性的注解
            InjectView injectView = field.getAnnotation(InjectView.class);
            if (injectView != null) {
                int viewId = injectView.value();
                try {
                    Method findViewById = clazz.getMethod("findViewById", int.class);
                    // 執行findViewById(R.id.tv)方法，但沒有賦值
                    Object view = findViewById.invoke(activity, viewId);
                    //TODO: 設定訪問權限，private
                    field.setAccessible(true);
                    // 屬性的值賦給控件，在當前的MainActivity
                    field.set(activity, view);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    /**
     * 事件的注入
     *
     * @param activity
     */
    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        // 獲取類的所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 拿到每個方法所有注解
            Annotation[] annotations = method.getAnnotations();
            // 遍歷注解
            for (Annotation annotation : annotations) {
                // 獲取OnClick注解上的注解類型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (annotationType != null) {
                    //TODO: 獲取EventBase注解，事件的3個重要成員封裝
                    EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                    if (eventBase != null) {
                        String listenerSetter = eventBase.listenerSetter();
                        Class<?> listenerType = eventBase.listenerType();
                        // 本應該執行的回調方法“onClick”，進行攔截
                        String callBackListener = eventBase.callBackListener();

                        try {
                            // 通過annotationType獲取OnClick注解的value方法
                            Method valueMethod = annotationType.getDeclaredMethod("value");
                            // 執行value方法，獲取注解的值(數組)
                            int[] viewIds = (int[]) valueMethod.invoke(annotation);

                            //TODO: AOP面向切面
                            ListenerInvocationHandler handler = new ListenerInvocationHandler(activity);
                            handler.addMethods(callBackListener, method);
                            // 代理
                            Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(),
                                    new Class[]{listenerType}, handler);

                            for (int viewId : viewIds) {
                                // 獲取當前activity的view控件(賦值)
                                View view = activity.findViewById(viewId);
                                // 獲取指定的方法，比如(setXXXListener)
                                Method setter = view.getClass().getMethod(listenerSetter, listenerType);
                                // 執行方法
                                setter.invoke(view, listener);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }



    }

}
