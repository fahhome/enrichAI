public class GPSRequestPacket {

    private static byte[] START_BIT = {(byte)0x78 ,(byte)0x78};
	private static byte plength = (byte)0x22;
	private static byte protocol_number = (byte)0x22 ;
	private static byte[] dateandtime ;
	private static byte qgps = (byte)12;
	private static byte[] latitude={(byte)78,(byte)12,(byte)78,(byte)56};
	private static byte[] longitude={(byte)100,(byte)12,(byte)244,(byte)56};
	private static byte[] serial_number = {(byte)0x00,(byte)0x00};
	private static byte[] error_check = {(byte)0x00 , (byte)0x1F} ;
	private static byte[] stop_bit = {(byte)0x0D , (byte)0x0A};

	public static byte[] getSTART_BIT() {
		return START_BIT;
	}

	public static byte[] getDateandtime() {
		return dateandtime;
	}

	public static void setDateandtime(byte[] dateandtime) {
		GPSRequestPacket.dateandtime = dateandtime;
	}

	public static byte getQgps() {
		return qgps;
	}

	public static void setQgps(byte qgps) {
		GPSRequestPacket.qgps = qgps;
	}

	public static byte[] getLatitude() {
		return latitude;
	}

	public static void setLatitude(byte[] latitude) {
		GPSRequestPacket.latitude = latitude;
	}

	public static byte[] getLongitude() {
		return longitude;
	}

	public static void setLongitude(byte[] longitude) {
		GPSRequestPacket.longitude = longitude;
	}

	public static void setSTART_BIT(byte[] sTART_BIT) {
		START_BIT = sTART_BIT;
	}

	public static byte getPlength() {
		return plength;
	}

	public static void setPlength(byte plength) {
		GPSRequestPacket.plength = plength;
	}

	public static byte getProtocol_number() {
		return protocol_number;
	}

	public static void setProtocol_number(byte protocol_number) {
		GPSRequestPacket.protocol_number = protocol_number;
	}

	public static byte[] getSerial_number(){
		return serial_number;
	}

	public static void setSerial_number(byte[] serial_number) {
		GPSRequestPacket.serial_number = serial_number;
	}

	public static byte[] getError_check() {
		return error_check;
	}

	public static void setError_check(byte[] error_check) {
		GPSRequestPacket.error_check = error_check;
	}

	public static byte[] getStop_bit() {
		return stop_bit;
	}

	public static void setStop_bit(byte[] stop_bit) {
		GPSRequestPacket.stop_bit = stop_bit;
	}

	public byte[] toBytes(){
        byte[]  result = new byte[25];
	    int i = 0;
	    for(byte b : START_BIT){
	    	result[i++] = b;
	    }
	    result[i++] = plength;
	    result[i++] = protocol_number;
	    for(byte b : dateandtime){
	    	result[i++] = b;
	    }
	    result[i++]=qgps;
	    for(byte b : latitude){
	    	result[i++] = b;
	    }
	    for(byte b : longitude){
	    	result[i++] = b;
	    }
	    for(byte b : serial_number){
	    	result[i++] = b;
	    }
	    for(byte b : error_check)
	    	result[i++] = b;
	    for(byte b : stop_bit)
	    	result[i++] = b;
		return result;
	}

}
