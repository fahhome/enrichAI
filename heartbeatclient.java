import java.net.*;
import java.io.*;
import javax.xml.bind.DatatypeConverter;

public class heartbeatclient{


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

       while(true){

        System.out.println(" Reboot :" + reboots);
        if(timeouts >= 3){
           timeouts = 0;
           reboots++;
           System.out.println("Time to restart the server");
           Thread.sleep(10000);
        }

       LoginRequestPacket lrp = new LoginRequestPacket();
       HeartbeatRequestPacket hrp = new HeartbeatRequestPacket();
       System.out.println(lrp.getProtocol_number());

	     String msgin = "" , msgout = "Login request";

       Socket s = new Socket("localhost",4999);

       s.setSoTimeout(30000);

       DataInputStream din = new DataInputStream(s.getInputStream());
       DataOutputStream dout = new DataOutputStream(s.getOutputStream());

       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

       byte[] message = lrp.ToBytes();

       Thread.sleep(2000);
       dout.write(message);

       byte[] messagein = new byte[10];
        try{
        din.readFully(messagein, 0, 10);
        System.out.println(toHexString(messagein));
      }catch (SocketTimeoutException e) {
        timeouts++;
        System.out.println("Timeout occured in Login");
        continue;
      }
      catch (Exception e) {
        System.out.println("Some other exception occured");
      }
	     Thread.sleep(2000);
	     Socket s2 = new Socket("localhost",5000);
       s2.setSoTimeout(5000);
	     Thread hb = new Thread(new Runnable(){
	     DataInputStream din2 = new DataInputStream(s2.getInputStream());
	     DataOutputStream dout2 = new DataOutputStream(s2.getOutputStream());
		   @Override
		   public void run() {
			// TODO Auto-generated method stub
		   while(true){
			 try {
        byte[] hbmessage = hrp.ToBytes();
        int l  = hbmessage.length;
        try {
         Thread.sleep(2000);
         } catch (InterruptedException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
        } // So that the next line executes after the din.readInt() at server
        dout2.flush();
 	      dout2.writeInt(l);
        dout2.flush();
        try {
 				Thread.sleep(2000);
 		  	} catch (InterruptedException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			 }
        dout2.write(hbmessage);
        dout2.flush();
			 } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 	}

			 try {
          byte[] msgin = new byte[10];
          din2.readFully(msgin, 0, 10);
				  System.out.println("from server heartbeat response :" + toHexString(msgin));
       }catch (SocketTimeoutException e) {
          System.out.println("Timeout occured in HeartbeatResponse");
          timeouts++;
          if(timeouts >=3)
            break;
       } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
      }catch (Exception e) {

      }

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
		   }

		  }


	   });

	   hb.start();
     hb.join();
	}

 }

}
