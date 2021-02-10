package cardAccess;

public class Bcd {
    private final byte[] value;

    private Bcd(byte[] value) {
        this.value = value;
    }

    public static Bcd of(byte[] value) {
        return new Bcd(value);
    }

    public static Bcd ofReverseOrder(byte[] value) {
        byte[] temp = new byte[value.length];
        for (int i = 0; i < value.length; i++) {
            byte low = (byte) (value[i] >>> 4 & 0x0F);
            byte high = (byte) (value[i] << 4 & 0xF0);
            byte b = (byte) (low | high);
            temp[i] = b;
        }
        return of(temp);
    }

    public String bcdString() {
        StringBuilder sb = new StringBuilder();
        for (byte b : value) {
            byte left = (byte) (b >>> 4 & 0x0F);
            if (left < 10) {
                sb.append(String.format("%01x", left));
            }

            byte right = (byte) (b & 0x0F);
            if (right < 10) {
                sb.append(String.format("%01x", right));
            }
        }
        return sb.toString();
    }
}
