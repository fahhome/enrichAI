import java.net.*;
import java.io.*;
import javax.xml.bind.DatatypeConverter;
public class heartbeatserver {

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
          System.out.println("Reading the contents of the packet...");
          byte[] message = new byte[22];
          din.readFully(message, 0, 22);
          System.out.println(toHexString(message));
          byte[] messageout = lrp.ToBytes();
          Thread.sleep(3000);
          dout.write(messageout);
          dout.flush();
          s.close();
          //Opening port for healthcheck packets
          ss2 = new ServerSocket(5000);
          s2 = ss2.accept();
          din2 = new DataInputStream(s2.getInputStream());
          dout2 = new DataOutputStream(s2.getOutputStream());
          Thread hb =  new Thread(new Runnable(){
		    	@Override
	    		public void run() {
				// TODO Auto-generated method stub
		    	  while(true){
				   	int expectedpacket = 0;
		   		  String msgin2 = null;
				   try {
						 //din2.flush();
						 int length = 0;
             length = din2.readInt();
             System.out.println("Input message length is :" + length);

             if(length > 0){
               System.out.println("Reading the contents of the heartbeat request packet...");
               byte[] message = new byte[length];
               din2.readFully(message, 0, message.length);

               byte b1 = message[11];
               int b1val = Byte.toUnsignedInt(b1);
							 System.out.println(b1val);
               if(b1val <= 255  && b1val != expectedpacket){
								   System.out.println("packets between " + b1val + "and " + expectedpacket + "are lost");
							 }
							 else{
                   byte b2 = message[10];
									 int high = b1 >= 0 ? b1 : 256 + b1;
                   int low =  b2 >= 0 ? b2 : 256 + b2;
                   int res = low | (high << 8);

									 if(res != expectedpacket)
									    System.out.println("lost packets...");
							 }
               System.out.println(toHexString(message));
             }
			  	  } catch (IOException e) {
					     // TODO Auto-generated catch block
				  	   e.printStackTrace();
				     }
		          try {
              Thread.sleep(2000);
							 byte[] msgout2 = hrp.ToBytes();
				    	dout2.write(msgout2);
				      }catch (IOException e) {
					    // TODO Auto-generated catch block
			  		  e.printStackTrace();
						 }catch (Exception e){
                 System.out.println("Exception occured");
						 }
		          try {
					     dout2.flush();
				      }catch (IOException e) {
					      // TODO Auto-generated catch block
					    e.printStackTrace();
				        }
								expectedpacket++;
			        }

			      }

          });
          hb.start();
     }

}
