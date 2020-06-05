import java.util.ArrayList;
import java.util.List;

public class LoginRequestPacket {

	private static byte[] START_BIT = {(byte)0x78 ,(byte)0x78};

	private static byte plength = (byte)0x11 ;

	private static byte protocol_number = (byte)0x01 ;

	private static byte[] info_content={(byte)0x03,(byte)0x51,(byte)0x60,
			(byte)0x80,(byte)0x80,(byte)0x77,(byte)0x92,(byte)0x88,(byte)0x22,
			(byte)0x03,(byte)0x32,(byte)0x01};

	private static byte[] Info_Serial_number = {(byte)0x01, (byte)0xAA};
	private static byte[] error_check = {(byte)0x53,(byte)0x36};
	private static byte[] stop_bit = {(byte)0x0D,(byte)0x0A};

	public static byte[] getSTART_BIT() {
		return START_BIT;
	}

	public static byte[] getInfo_Serial_number() {
		return Info_Serial_number;
	}

	public static void setInfo_Serial_number(byte[] info_Serial_number) {
		Info_Serial_number = info_Serial_number;
	}

	public static void setSTART_BIT(byte[] sTART_BIT) {
		START_BIT = sTART_BIT;
	}

	public static byte getPlength() {
		return plength;
	}

	public static void setPlength(byte plength) {
		LoginRequestPacket.plength = plength;
	}

	public static byte getProtocol_number() {
		return protocol_number;
	}

	public static void setProtocol_number(byte protocol_number) {
		LoginRequestPacket.protocol_number = protocol_number;
	}

	public byte[] ToBytes(){

	   // List<Byte> result = new ArrayList<Byte>();

	    byte[]  result = new byte[22];

	    int i = 0;

	    for(byte b : START_BIT){
	    	result[i++] = b;
	    }

	    result[i++] = plength;
	    result[i++] = protocol_number;

	    for(byte b : info_content){
	    	result[i++] = b;
	    }

	    for(byte b : Info_Serial_number){
	    	result[i++] = b;
	    }

	    for(byte b : error_check)
	    	result[i++] = b;

	    for(byte b : stop_bit)
	    	result[i++] = b;

		return result;

	}


}
