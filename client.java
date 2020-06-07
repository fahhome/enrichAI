import java.net.*;
import java.time.LocalDateTime;
import java.io.*;
import javax.xml.bind.DatatypeConverter;

public class client{

	  static int timeouts = 0;
	  static int reboots = 0;

	  public static String toHexString(byte[] array) {
		    return DatatypeConverter.printHexBinary(array);
		}

		public static byte[] toByteArray(String s) {
		    return DatatypeConverter.parseHexBinary(s);
		}

  	public static void main(String[] args)   throws Exception {
		// TODO Auto-generated method stub

	   String msgin = "" , msgout = "Login request";

	   LoginRequestPacket lrp = new LoginRequestPacket();
	   HeartbeatRequestPacket hrp = new HeartbeatRequestPacket();
	   GpsRequestPacket gp = new GpsRequestPacket();
     Socket s = new Socket("localhost",4999);

     DataInputStream din = new DataInputStream(s.getInputStream());
     DataOutputStream dout = new DataOutputStream(s.getOutputStream());

     BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	   dout.write(lrp.ToBytes());

	   byte[] lmsgresponse = new byte[10];
	   din.readFully(lmsgresponse, 0, 10);

	   System.out.println("Received login response from server :" + toHexString(lmsgresponse));

	   Thread.sleep(2000);

	   Socket s2 = new Socket("localhost",5000);

	   Thread hb = new Thread(new Runnable(){
	   DataInputStream din2 = new DataInputStream(s2.getInputStream());
	   DataOutputStream dout2 = new DataOutputStream(s2.getOutputStream());
		@Override
		public void run() {

		  while(true){
			try {
			    Thread.sleep(2000);
				dout2.write(hrp.ToBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
                System.out.println("exception");
				e.printStackTrace();
			}catch(Exception e){
				System.out.println("some exception from heart beat request");
			}

			try {
				//String msgin = din2.readUTF();
				byte[] hmsgres = new byte[10];
				din2.readFully(hmsgres,0,10);
                System.out.println("From server healthcheck response :" + toHexString(hmsgres));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				//Increase the sequence number for the next packet
				byte[] prev = hrp.getInfo_Serial_number();
				byte b1 = hrp.getInfo_Serial_number()[1];
				byte[] b1arr = new byte[1];
				b1arr[0] = b1;
				String s = toHexString(b1arr);

				int hexdig = Integer.parseInt(s,16);
				hexdig++;
				byte afteradd = (byte) hexdig;
				prev[1] = afteradd;
				hrp.setInfo_Serial_number(prev);
				try {
				 Thread.sleep(2000);
				 } catch (InterruptedException e) {
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				}
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }

		}

	   });

	   hb.start();

       Socket s3 = new Socket("localhost",5001);

	   Thread gps = new Thread(new Runnable(){
	   DataInputStream din3 = new DataInputStream(s3.getInputStream());
	   DataOutputStream dout3 = new DataOutputStream(s3.getOutputStream());
		@Override
		public void run() {
			// TODO Auto-generated method stub
		  while(true){
			try {
				LocalDateTime now = LocalDateTime.now();

				 byte year = (byte)now.getYear();
         byte month = (byte)now.getMonthValue();
         byte day = (byte)now.getDayOfMonth();
         byte hour = (byte)now.getHour();
         byte minute = (byte)now.getMinute();
         byte second = (byte)now.getSecond();
         byte[] infoc = new byte[6];

				 int i = 0;

				 infoc[i++]=year;
				 infoc[i++]=month;
				 infoc[i++]=day;
				 infoc[i++]=hour;
				 infoc[i++]=minute;
				 infoc[i++]=second;

				gp.setDateandtime(infoc);
				Thread.sleep(4000);
				dout3.write(gp.toBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(Exception e){
				System.out.println("some exception occured at gps packet request");
			}

		  }

		}
	   });
	   gps.start();
	}

}
