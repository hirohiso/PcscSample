package usim.domain;

import pcsc.datasource.cardAccess.apdu.BinaryData;

import java.util.Arrays;
import java.util.Optional;

public class ApplicationTemplate {
    private final byte[] aid;
    private final Optional<byte[]> label;


    public ApplicationTemplate(byte[] value) {
        //Application template tag = '61'
        if (value[0] != 0x61) {
            throw new IllegalArgumentException();
        }
        //Length of the application template = '03'-'7F'
        int lengthAppTemplate = value[1];

        //Application Identifier tag = '4F'
        if (value[2] != 0x4F) {
            throw new IllegalArgumentException();
        }

        //AID length = '01'-'10'
        int aidLength = value[3];

        //AID value. For 3G applications, see TS 101 220 [3]
        byte[] aidValue = Arrays.copyOfRange(value,4,4 + aidLength);

        //Application label tag = '50'
        if (value[(4 + aidLength)] != 0x50) {
            throw new IllegalArgumentException();
        }
        //Application label length
        int appLabelLength = value[(5 + aidLength)];

        //Application label value
        byte[] label = Arrays.copyOfRange(value,6 + aidLength,6 + aidLength + appLabelLength);

        this.aid = aidValue;
        this.label = Optional.of(label);
    }

    @Override
    public String toString() {
        return "ApplicationTemplate{" +
                "aid=" + BinaryData.of(this.aid).toString() +
                ", label=" +  new String(label.get()) +
                '}';
    }

    public byte[] getAid(){
        return this.aid;
    }
}
