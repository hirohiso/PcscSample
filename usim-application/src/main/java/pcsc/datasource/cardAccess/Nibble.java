package pcsc.datasource.cardAccess;

/**
 * ニブル
 *
 * 4bitを表現する
 */
public class Nibble {
    private final short value;

    public Nibble(short value) {
        if(value > 0xF || value < 0){
            throw new IllegalArgumentException();
        }
        this.value = value;
    }
}
