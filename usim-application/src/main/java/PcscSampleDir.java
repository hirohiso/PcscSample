import usim.domain.ApplicationTemplate;
import pcsc.datasource.cardAccess.Bcd;
import pcsc.datasource.cardAccess.CardTransfer;
import usim.domain.UsimCommandApduProvider;

import javax.smartcardio.*;
import java.util.Arrays;

public class PcscSampleDir {
    public static void main(String[] args) throws CardException {
        TerminalFactory tf = TerminalFactory.getDefault();
        CardTerminals ct = tf.terminals();
        System.out.println(ct.list().toString());

        CardTerminal terminal = ct.list().get(0);

        Card card = terminal.connect("*");
        System.out.println(card.toString());
        CardTransfer cardTransfer = new CardTransfer(card);

        ATR atr = card.getATR();

        // Send Select Applet command
        ResponseAPDU answer;
        System.out.println("--------");
        System.out.println("SELECT MD");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectMd());
        System.out.println("--------");

        System.out.println("SELECT EF(Iccid)ADF USIM");
        byte[] iccid = {(byte) 0x2F, (byte) 0xE2};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(iccid));
        System.out.println("--------");

        System.out.println("Read Binary EF(Iccid)");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadBinary((byte) 0x0A));
        System.out.println("--------");

        Bcd bcd = Bcd.ofReverseOrder(answer.getData());
        System.out.println("ICCID:" +  bcd.bcdString());

        System.out.println("--------");
        System.out.println("SELECT EF(Dir)ADF USIM");
        byte[] dir = {(byte) 0x2F, (byte) 0x00};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(dir));

        System.out.println("");

        System.out.println("");
        System.out.println("--------");
        System.out.println("Read Record EF(Dir)");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        System.out.println("");
        ApplicationTemplate applicationTemplate = new ApplicationTemplate(answer.getData());
        System.out.println(applicationTemplate.toString());

        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        System.out.println("");
        System.out.println("--------");
        System.out.println("SELECT MD");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectMd());
        System.out.println("--------");

        System.out.println("SELECT USIM");
        byte[] aid = applicationTemplate.getAid();
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectAid(aid));
        System.out.println("--------");

        System.out.println("SELECT MSISDN");
        byte[] msisdn = {(byte) 0x6F, (byte) 0x40};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(msisdn));
        System.out.println("");
        System.out.println("--------");
        System.out.println("Read Record MSISDN");

        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        System.out.println("");
        byte[] test = answer.getData();
        int start = test.length - 11 - 1;
        int end = test.length - 2 - 1;
        Bcd msisdnBcd = Bcd.ofReverseOrder(Arrays.copyOfRange(test, start, end));
        System.out.println("MSISDN:" + msisdnBcd.bcdString());
        System.out.println("--------");

        System.out.println("SELECT IMSI");
        byte[] imsi = {(byte) 0x6F, (byte) 0x07};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(imsi));
        System.out.println("--------");

        System.out.println("Read Binary");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadBinary((byte)0X09));
        Bcd imsiData = Bcd.ofReverseOrder(answer.getData());
        System.out.println("IMSI:" + imsiData.bcdString());
        System.out.println("--------");

        System.out.println("SELECT Emergency Call Codes");
        byte[] eccId = {(byte) 0x6F, (byte) 0xB7};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(eccId));
        System.out.println("");
        System.out.println("--------");
        System.out.println("Read Record Emergency Call Codes");

        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());
        /*
        Emergency Service Category Value (octet 3)
        The meaning of the Emergency Category Value is derived from the following settings (see 3GPP TS 22.101 [8] clause
        10):
        Bit 1 Police
        Bit 2 Ambulance
        Bit 3 Fire Brigade
        Bit 4 Marine Guard
        Bit 5 Mountain Rescue
        Bit 6 manually initiated eCall
        Bit 7 automatically initiated eCall
        Bit 8 is spare and set to "0"
        */

        System.out.println("------------------------");
        System.out.println("SELECT Service Provider Name");
        byte[] spnId = {(byte) 0x6F, (byte) 0x46};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(spnId));
        System.out.println("------------------------");
        System.out.println("Read Binary");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadBinary((byte) 0x11));
        for (byte c : answer.getData()){
            System.out.printf(String.valueOf((char) c));
        }

        System.out.println("");
        System.out.println("------------------------");
        System.out.println("SELECT (pNn)");
        byte[] plmnId = {(byte) 0x6F, (byte) 0xC6};
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getSelectEf(plmnId));
        System.out.println("------------------------");
        System.out.println("Read Binary");
        answer = cardTransfer.rawCommand(UsimCommandApduProvider.getReadRecord());





        // Disconnect the card
        card.disconnect(false);
    }
}
