/*

    Copyright (C) 2015  Czifro Development

    This file is part of the jsock.util package

    The jsock.util package is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    The jsock.util package is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with the jsock.util package.  If not, see <http://www.gnu.org/licenses/>.

 */

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

    public static <T> T fromJson(String json, Class<T> t)
    {
        Gson gson = new Gson();
        T obj = null;
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
