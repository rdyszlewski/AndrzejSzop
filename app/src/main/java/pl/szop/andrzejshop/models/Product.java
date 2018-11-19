package pl.szop.andrzejshop.models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Product {

    public Map<String, Object> getValues() throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> result = new HashMap<>();
        Class clazz = getClass();
        for (Method method : clazz.getMethods()){
            String methodName = method.getName();
            if(methodName.contains("get") && !methodName.equals("getClass") && !methodName.equals("getId") && !methodName.equals("getValues")
                    && !methodName.equals("getValue")){

                String fieldName = methodName.substring(3);
                Object value = getValue(method);
                result.put(fieldName, value);
            }
        }

        return result;
    }

    private Object getValue(Method method) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(this, null);
    }

    public Object getValue(String fieldName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // change first letter to capital
        String name = Character.toString(fieldName.charAt(0)).toUpperCase()+fieldName.substring(1);
        Method method = getClass().getMethod("get" + name, null);
        return getValue(method);
    }
}
