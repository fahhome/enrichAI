import java.net.*;
import java.nio.ByteBuffer;

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


		 public static void appendStrToFile(String fileName,
                 String str)
           {
             try {
                   BufferedWriter out = new BufferedWriter(
                   new FileWriter(fileName, true));
                   out.write(str);
                   out.close();
                 }
             catch (IOException e) {
                 System.out.println("exception occoured" + e);
                }
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
					 System.out.println("from client healthcheck packet:" + toHexString(hmsgreq));
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
  					  //msgin2 = din3.readUTF();
  					  byte[] gmsgreq = new byte[43];
  					  din3.readFully(gmsgreq, 0 , 43);
  			          System.out.println("from client gps packet : " + toHexString(gmsgreq));
  			          for(int i = 4; i<=9 ; i++){
						 int val = Byte.toUnsignedInt(gmsgreq[i]);
					      appendStrToFile("gpstracker.txt"," " + val + " ");
			           }
                     appendStrToFile("gpstracker.txt",'\n'  + "Latitude is:" + '\n');
			         byte[] bytes = new byte[4];
			         int j = 0;
				     for(int i= 10 ; i<=13;i++)
				     bytes[j++] = gmsgreq[i] ;

			         double result = ByteBuffer.wrap(bytes).getInt()/1800000;
			         appendStrToFile("gpstracker.txt"," " + result);
			         appendStrToFile("gpstracker.txt",'\n'  + "longitude is:" + '\n');

			         j = 0;
			         for(int i= 14 ; i<=17;i++)
			         bytes[j++] = gmsgreq[i] ;
			         result = ByteBuffer.wrap(bytes).getInt()/1800000;
                     appendStrToFile("gpstracker.txt"," " + result + '\n');

  				  } catch (IOException e) {
  					// TODO Auto-generated catch block
  					e.printStackTrace();
  				  }catch(Exception e){

  					  System.out.println("Some exception occured at gps response ");

  				  }

  				}

  			}

            });

          sgps.start();


     }

}
