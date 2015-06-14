/*

    Copyright (C) 2015  Czifro Development

    This file is part of the jsock.net package

    The jsock.net package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.net package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.net package.  If not, see <http://www.gnu.org/licenses/>.

 */


package jsock.net;

import jsock.util.JsonTool;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;

/**
 * Created by czifro on 1/16/15. ObjectSocket is designed to read/write structured data on the stream.
 * @author William Czifro
 * @version 0.2.0
 */

public class ObjectSocket extends MessageSocket {

    private String failedJson = null;

    /**
     * Wraps around a Socket connection and opens I/O streams
     *
     * @param conn java.net.Socket that is wrapped around
     * @throws java.io.IOException Throws IOException if I/O streams cannot be opened
     */
    public ObjectSocket(Socket conn) throws IOException {
        super(conn);
    }

    /**
     * Receives JSONString and attempts convert to Object, if conversion fails, use recover_failed_json() to get failed message
     *
     * @param type Class type JSONString should be converted to.
     * @return     returns null if conversion JSONString to Object failed
     */
    public Object recv_object(Class<?> type)
    {
        String s_size = recv_msg();
        send_msg("Ok");
        String json = recv_large_msg(Integer.parseInt(s_size));
        Object obj = JsonTool.fromJson(json, type);
        if (obj == null)
            failedJson = JsonTool.failedJson;
        return obj;
    }

    /**
     * Returns JSONString that could not be converted to Object
     *
     * @return      returns null if no new failedJson exists
     * @deprecated  replaced by JsonTool.failedJson
     */
    public String recover_failed_json(){
        String t = failedJson;
        failedJson = null;
        return t;
    }

    /**
     * Receive JSON representation of an object
     *
     * @return   JSONString
     */
    public String recv_object_asString()
    {
        String s_size = recv_msg();
        send_msg("Ok");
        String json = recv_large_msg(Integer.parseInt(s_size));
        return json;
    }

    /**
     * Converts Object to a JSONString and sends it
     *
     * @param obj   Object to be sent
     * @param type  Class type of obj
     * @throws java.io.IOException Throws IOException if conversion fails
     */
    public void send_object(Object obj, Type type) throws IOException {
        String json = null;

        json = JsonTool.toJson(obj, type);

        send_msg(Integer.toString(json.getBytes().length));
        recv_msg();
        send_msg(json);
    }

}

