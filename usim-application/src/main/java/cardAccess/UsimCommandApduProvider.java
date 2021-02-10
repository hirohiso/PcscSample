package cardAccess;

import javax.smartcardio.CommandAPDU;

public class UsimCommandApduProvider {
    public static CommandAPDU getSelectMd(){
        return new CommandAPDU(0x00, 0xA4, 0x00, 0x0C);
    }

    public static CommandAPDU getSelectEf(byte[] ef){
        return new CommandAPDU(0x00, 0xA4, 0x00, 0x04, ef);
    }

    public static CommandAPDU getReadRecord(){
        byte[] array = {(byte) 0x00, (byte) 0xB2, (byte) 0x00, (byte) 0x02, (byte) 0x00};
        return new CommandAPDU(array);
    }

    public static CommandAPDU getSelectAid(byte[] aid){
        return new CommandAPDU(0x00, 0xA4, 0x04, 0x04, aid);
    }

    public static CommandAPDU getReadBinary(byte length){
        byte[] array = {(byte) 0x00, (byte) 0xB0, (byte) 0x00, (byte) 0x00, length};
        return new CommandAPDU(array);
    }

}