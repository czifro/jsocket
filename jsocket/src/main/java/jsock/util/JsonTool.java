package jsock.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by czifro on 6/13/15. Used to convert an object to and from JSON
 * @author Will Czifro
 * @version 0.1.0
 */
public class JsonTool {

    public static String failedJson;

    public static Object fromJson(String json, Class<?> t)
    {
        Gson gson = new Gson();
        Object obj = null;
        try {
            obj = gson.fromJson(json, t);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            failedJson = json;
        }

        return obj;
    }

    public static String toJson(Object obj, Type type)
    {
        Gson gson = new Gson();

        String json = null;

        json = gson.toJson(obj, type);

        return json;
    }
}
