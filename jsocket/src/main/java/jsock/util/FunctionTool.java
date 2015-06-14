package jsock.util;

import jsock.enums.FunctionType;

import java.util.function.Function;

/**
 * Created by czifro on 6/13/15. Used to create and run functions for sanitizing a string.
 * @author Will Czifro
 * @version 0.1.0
 */
public class FunctionTool {

    /**
     * Tests each character in a string with predicate to sift out characters
     * @param s   String to sift
     * @param f   Function for sifting
     * @return    A string that has been processed by the function argument
     */
    public static String runFunction(String s, Function<String, String> f){
        return f.apply(s);
    }

    /**
     * Creates a function the will sanitize a string as per value of FunctionType enum
     * @param type  Specifies what type of sanitization function to create
     * @return      A function that will sanitize a string
     */
    public static Function<String, String> sanitizationFunction(FunctionType type){
        switch (type)
        {
            case ONLY_NULLS:
                return removeNullCharSanitization();
            case NON_ASCII:
                break;
            case NON_UNICODE:
                break;
        }
        return null;
    }

    /**
     * Returns a function that removes sanitization from a string
     * @return
     */
    private static Function<String, String> removeNullCharSanitization()
    {
        return new Function<String, String>() {
            @Override
            public String apply(String s) {
                String temp = "";
                for (char c : s.toCharArray()){
                    if (c == '\0'){
                        continue;
                    }
                    temp += c;
                }

                return temp;
            }
        };
    }
}
