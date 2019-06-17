package melvinlin.com.library;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;


public class ListenerInvocationHandler implements InvocationHandler {

    //無參數點擊處理
    //點擊的阻尼事件??

    private Object target; // 需要攔截的對象，比如 Activity/Fragment
    private HashMap<String, Method> map = new HashMap<>();

    public ListenerInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 獲取需要攔截的方法名
        String methodName = method.getName(); // 假如是onClick
        method = map.get(methodName);
        if (method != null) {
            return method.invoke(target, args);
        }
        return null;
    }

    /**
     * 添加攔截方法
     * @param methodName 本應該執行的onClick方法，進行攔截
     * @param method 去執行開發者自定義的方法
     */
    public void addMethods(String methodName, Method method) {
        map.put(methodName, method);
    }
}
