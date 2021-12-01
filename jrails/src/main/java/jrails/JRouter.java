package jrails;
import java.util.*;
import java.lang.reflect.*;
import java.util.Map;

public class JRouter {

    private HashMap<String, String> map = new HashMap<String, String>();
    public void addRoute(String verb, String path, Class clazz, String method) {
        map.put(verb+path, clazz.getName()+"#"+method);
    }

    // Returns "clazz#method" corresponding to verb+URN
    // Null if no such route
    public String getRoute(String verb, String path) {
        return map.get(verb+path);
    }

    // Call the appropriate controller method and
    // return the result
    public Html route(String verb, String path, Map<String, String> params) {
        if(!map.containsKey(verb+path))throw new UnsupportedOperationException();

        String value = getRoute(verb,path);
        String[] contents = value.split("#");
        try {
            Class cls = Class.forName(contents[0]);       
            Method m = cls.getMethod(contents[1], Map.class);
            return (Html)m.invoke(null, params); 
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
