package pcsc.datasource.cardAccess.apdu;

public class ClaBuilder {
    private CodeofCla cla = CodeofCla.Iso7816;
    private byte x = (byte)0x00;

    public enum CodeofCla{
        Iso7816((byte)0x00),AppIso7816((byte)0xA0);

        private final byte value;

        CodeofCla(byte value) {
            this.value = value;
        }
    }

}
