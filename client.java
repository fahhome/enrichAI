import java.net.*;
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
                System.out.println("From server :" + toHexString(hmsgres));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
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
				dout3.writeUTF("gps request");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				String msgin = din3.readUTF();
               System.out.println("from server :" + msgin);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		  }

		}
	   });

	   gps.start();



	}

}
