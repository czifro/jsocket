package net.jsock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by czifro on 1/16/15.
 * @author William Czifro
 * @version 0.1.0
 * ObjectSocket is designed to read/write structured data on the stream
 */
public class ObjectSocket extends MessageSocket {

    private JSONObject jObj;
    private JSONParser parser;
    private ObjectMapper objectMapper;
    private String failedJson = null;

    public ObjectSocket(Socket conn) throws IOException {
        super(conn);

        parser = new JSONParser();
        objectMapper = new ObjectMapper();
    }

    /**
     * Receives JSONString and attempts convert to Object, if conversion fails, use recover_failed_json() to get failed message
     *
     * @return Object, returns null if conversion JSONString -> Object failed
     */
    public Object recv_object()
    {
        String s_size = recv_msg();
        send_msg("Ok");
        String json = recv_all_msg(Integer.parseInt(s_size));
        try {
            return parser.parse(json);
        } catch (ParseException e) {
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
    public void send_object(Object obj) {
        ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(obj);

        send_msg(Integer.toString(json.getBytes().length));
        recv_msg();
        send_msg(json);
    }

}
