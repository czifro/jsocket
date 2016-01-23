package jsocket.util;

import java.util.function.Function;

/**
 * Enum containing different lambda functions used for filtering strings
 * @author Will Czifro
 */
public enum FilterFunctionType {
    NULL_CHARS((s) -> {
        String str = "";
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c == '\0')
                continue;
            str += c;
        }
        return str;
    })
    ;

    private Function<String, String> func;

    FilterFunctionType(Function<String, String> func) {
        this.func = func;
    }

    public Function<String, String> getFunc() {
        return func;
    }
}
