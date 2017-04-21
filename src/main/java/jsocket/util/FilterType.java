package jsocket.util;

/**
 * Enum containing different lambda functions used for filtering strings
 * @author Will Czifro
 */
public enum FilterType {
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

    private IFilter func;

    FilterType(IFilter func) {
        this.func = func;
    }

    public IFilter getFunc() {
        return func;
    }
}
