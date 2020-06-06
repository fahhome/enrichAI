import java.net.*;

import javax.xml.bind.DatatypeConverter;

import java.io.*;
public class server {

	static ServerSocket ss ;
	static Socket s ;
	static DataInputStream din;
	static DataOutputStream dout;

	static ServerSocket ss2 ;
	static Socket s2 ;
	static DataInputStream din2;
	static DataOutputStream dout2;


	static ServerSocket ss3 ;
	static Socket s3 ;
	static DataInputStream din3;
	static DataOutputStream dout3;


	  public static String toHexString(byte[] array) {
		    return DatatypeConverter.printHexBinary(array);
		}

		public static byte[] toByteArray(String s) {
		    return DatatypeConverter.parseHexBinary(s);
		}

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		  LoginResponsePacket lrp = new LoginResponsePacket();
		  HeartbeatResponsePacket hrp = new HeartbeatResponsePacket();
		  String msgin = "" , msgout = "";
          ss = new ServerSocket(4999);
          s = ss.accept();
          din = new DataInputStream(s.getInputStream());
          dout = new DataOutputStream(s.getOutputStream());

          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          //msgin = din.readUTF();

          byte[] lmsgrequest = new byte[22];
   	      din.readFully(lmsgrequest, 0, 22);

          System.out.println("login request from client : " + toHexString(lmsgrequest));

          //msgout = "login response";
          Thread.sleep(2000);
          dout.write(lrp.ToBytes());
          dout.flush();

          s.close();

          ss2 = new ServerSocket(5000);
          s2 = ss2.accept();

          din2 = new DataInputStream(s2.getInputStream());

          dout2 = new DataOutputStream(s2.getOutputStream());


          ss3 = new ServerSocket(5001);
          s3 = ss3.accept();

          din3 = new DataInputStream(s3.getInputStream());

          dout3 = new DataOutputStream(s3.getOutputStream());



          Thread shb =  new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
			while(true){
				//String msgin2 = null;
				   try {
					 byte[] hmsgreq = new byte[16];
					 din2.readFully(hmsgreq,0,16);
					 System.out.println("from client :" + toHexString(hmsgreq));
				}catch (IOException e) {
					e.printStackTrace();
				}
		        // String  msgout2 = "heartbeat response";
		          try {
		        	Thread.sleep(2000);
					dout2.write(hrp.ToBytes());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(Exception e){
					System.out.println("some exception while writing heart beat response from adapter");
				}

		          try {
					dout2.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			}

          });

        shb.start();
          Thread sgps =  new Thread(new Runnable(){

  			@Override
  			public void run() {
  				// TODO Auto-generated method stub

  				while(true){
  				String msgin2 = null;
  				   try {
  					 msgin2 = din3.readUTF();
  			          System.out.println("from client : " + msgin2);

  				} catch (IOException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  		         String  msgout2 = "gps response";

  		          try {
  					dout3.writeUTF(msgout2);
  				} catch (IOException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}

  		          try {
  					dout3.flush();
  				} catch (IOException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				}
  				}

  			}

            });

          sgps.start();


     }

}
