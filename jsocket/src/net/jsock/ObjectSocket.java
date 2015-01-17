package net.jsock;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;

/**
 * Created by czifro on 1/16/15.
 * @author William Czifro
 * @version 0.1.0
 * ObjectSocket is designed to read/write structured data on the stream
 */
public class ObjectSocket extends MessageSocket {

    private Gson gson;
    private String failedJson = null;

    public ObjectSocket(Socket conn) throws IOException {
        super(conn);

        gson = new Gson();
    }

    /**
     * Receives JSONString and attempts convert to Object, if conversion fails, use recover_failed_json() to get failed message
     *
     * @return Object, returns null if conversion JSONString -> Object failed
     */
    public Object recv_object(Class<?> type)
    {
        String s_size = recv_msg();
        send_msg("Ok");
        String json = recv_all_msg(Integer.parseInt(s_size));
        try {
            return gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            failedJson = json;
        }
        return null;
    }

    /**
     * Returns JSONString that could not be converted to Object
     *
     * @return failedJson, returns null if no new failedJson exists
     */
    public String recover_failed_json(){
        String t = failedJson;
        failedJson = null;
        return t;
    }

    public String recv_object_asString()
    {
        String s_size = recv_msg();
        send_msg("Ok");
        String json = recv_all_msg(Integer.parseInt(s_size));
        return json;
    }

    /**
     * Converts Object to a JSONString and sends it
     *
     * @param obj
     */
    public void send_object(Object obj, Type type) throws IOException {
        String json = null;

        json = gson.toJson(obj, type);

        send_msg(Integer.toString(json.getBytes().length));
        recv_msg();
        send_msg(json);
    }

}
