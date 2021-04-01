package pcsc.datasource.cardAccess.apdu;

public class SelectCommandBuilder {
    private final byte cla = (byte)0x00;
    private final byte ins = (byte)0xa4;
    private  byte p1;
    private  byte p2;
    private byte[] data;


    public byte[] build(){
        BinaryData result = BinaryData.EMPTY;
        result.append(BinaryData.of(cla));
        result.append(BinaryData.of(ins));
        result.append(BinaryData.of(p1));
        result.append(BinaryData.of(p2));
        result.append(BinaryData.of(data));
        return null;
    }

}
