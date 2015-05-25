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

import jsock.enums.StringToolType;

/**
 * Created by czifro on 1/20/15. StringCleaner can be used to clean a string of certain characters
 * @author William Czifro
 * @version 0.1.0
 */
public class StringTool {

    /**
     * Removes null characters (\0) from a string
     * @param s String to be cleaned
     * @return
     */
    private static String removeNullChars(String s)
    {
        String temp = "";
        for (char c : s.toCharArray()){
            if (c == '\0'){
                continue;
            }
            temp += c;
        }

        return temp;
    }

    /**
     * Takes a string and removes certain characters from it
     * @param s     String to be cleaned
     * @param type  Specifies which character preset to remove
     * @return      Cleansed string
     */
    public static String cleanString(String s, StringToolType type)
    {
        switch (type)
        {
            case ONLY_NULLS:
                return removeNullChars(s);
            case NON_ASCII:
                break;
            case NON_UNICODE:
                break;
        }
        return null;
    }
}
