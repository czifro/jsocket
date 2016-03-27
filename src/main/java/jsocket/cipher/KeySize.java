package jsocket.cipher;

/**
 * @author Will Czifro
 */
public enum KeySize {

    BIT_LENGTH_1024(1024),
    BIT_LENGTH_2048(2048),
    BIT_LENGTH_4096(4096);

    private int size;

    KeySize(int value) {
        size = value;
    }

    public int toInt() {
        return size;
    }
}
