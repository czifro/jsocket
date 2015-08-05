package jsock.util;

import jsock.enums.StringToolType;

/**
 * Created by czifro on 1/20/15. StringTool can be used to clean a string of certain characters
 * @author William Czifro
 * @version 0.2.0
 * @deprecated Consider switching to FunctionTool
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
     * @deprecated  Replaced by sanitizationFunction(FunctionType)
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
