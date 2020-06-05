public class GpsRequestPacket {

	  private static byte[] START_BIT = {(byte)0x78 ,(byte)0x78};
		private static byte plength = (byte)0x22;
		private static byte protocol_number = (byte)0x22 ;
		private static byte[] dateandtime ;
		private static byte qgps = (byte)12;
		private static byte[] latitude={(byte)78,(byte)12,(byte)78,(byte)56};
		private static byte[] longitude={(byte)100,(byte)12,(byte)244,(byte)56};
		private static byte speed = (byte)0x00;
		private static byte[] courseandstatus = {(byte)21,(byte)76};
		private static byte[] mcc = {(byte)9,(byte)1};
		private static byte mnc = (byte)22;
		private static byte[] lac = {(byte)25,(byte)26};
		private static byte[] cid = {(byte)25,(byte)26,(byte)27};
		private static byte acc = (byte)1;
		private static byte mode = (byte)0x06;
		private static byte rru = (byte)0x01;
		private static byte[] mileage = {(byte)0x21,(byte)0x22,(byte)0x23,(byte)0x24};
		private static byte[] serial_number = {(byte)0x00,(byte)0x00};
		private static byte[] error_check = {(byte)0x00 , (byte)0x1F} ;
		private static byte[] stop_bit = {(byte)0x0D , (byte)0x0A};

		public static byte[] getSTART_BIT() {
			return START_BIT;
		}

		public static byte getSpeed() {
			return speed;
		}

		public static void setSpeed(byte speed) {
			GpsRequestPacket.speed = speed;
		}

		public static byte[] getCourseandstatus() {
			return courseandstatus;
		}

		public static void setCourseandstatus(byte[] courseandstatus) {
			GpsRequestPacket.courseandstatus = courseandstatus;
		}

		public static byte[] getMcc() {
			return mcc;
		}

		public static void setMcc(byte[] mcc) {
			GpsRequestPacket.mcc = mcc;
		}

		public static byte getMnc() {
			return mnc;
		}

		public static void setMnc(byte mnc) {
			GpsRequestPacket.mnc = mnc;
		}

		public static byte[] getLac() {
			return lac;
		}

		public static void setLac(byte[] lac) {
			GpsRequestPacket.lac = lac;
		}

		public static byte[] getCid() {
			return cid;
		}

		public static void setCid(byte[] cid) {
			GpsRequestPacket.cid = cid;
		}

		public static byte getAcc() {
			return acc;
		}

		public static void setAcc(byte acc) {
			GpsRequestPacket.acc = acc;
		}

		public static byte getMode() {
			return mode;
		}

		public static void setMode(byte mode) {
			GpsRequestPacket.mode = mode;
		}

		public static byte getRru() {
			return rru;
		}

		public static void setRru(byte rru) {
			GpsRequestPacket.rru = rru;
		}

		public static byte[] getMileage() {
			return mileage;
		}

		public static void setMileage(byte[] mileage) {
			GpsRequestPacket.mileage = mileage;
		}

		public static byte[] getDateandtime() {
			return dateandtime;
		}

		public static void setDateandtime(byte[] dateandtime) {
			GpsRequestPacket.dateandtime = dateandtime;
		}

		public static byte getQgps() {
			return qgps;
		}

		public static void setQgps(byte qgps) {
			GpsRequestPacket.qgps = qgps;
		}

		public static byte[] getLatitude() {
			return latitude;
		}

		public static void setLatitude(byte[] latitude) {
			GpsRequestPacket.latitude = latitude;
		}

		public static byte[] getLongitude() {
			return longitude;
		}

		public static void setLongitude(byte[] longitude) {
			GpsRequestPacket.longitude = longitude;
		}

		public static void setSTART_BIT(byte[] sTART_BIT) {
			START_BIT = sTART_BIT;
		}

		public static byte getPlength() {
			return plength;
		}

		public static void setPlength(byte plength) {
			GpsRequestPacket.plength = plength;
		}

		public static byte getProtocol_number() {
			return protocol_number;
		}

		public static void setProtocol_number(byte protocol_number) {
			GpsRequestPacket.protocol_number = protocol_number;
		}

		public static byte[] getSerial_number(){
			return serial_number;
		}

		public static void setSerial_number(byte[] serial_number) {
			GpsRequestPacket.serial_number = serial_number;
		}

		public static byte[] getError_check() {
			return error_check;
		}

		public static void setError_check(byte[] error_check) {
			GpsRequestPacket.error_check = error_check;
		}

		public static byte[] getStop_bit() {
			return stop_bit;
		}

		public static void setStop_bit(byte[] stop_bit) {
			GpsRequestPacket.stop_bit = stop_bit;
		}

		public byte[] toBytes(){
	        byte[]  result = new byte[43];
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
		    result[i++]=speed;
		    for(byte b : courseandstatus){
		    	result[i++] = b;
		    }
		    for(byte b : mcc){
		    	result[i++] = b;
		    }
		    result[i++] = mnc;
		    for(byte b : lac){
		    	result[i++] = b;
		    }
		    for(byte b : cid){
		    	result[i++] = b;
		    }
		    result[i++] = acc;
		    result[i++] = mode;
		    result[i++] = rru;

		    for(byte b : mileage){
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
