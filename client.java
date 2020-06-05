import java.net.*;
import java.io.*;

public class client{


	public static void main(String[] args)   throws Exception {
		// TODO Auto-generated method stub

	     String msgin = "" , msgout = "Login request";

       Socket s = new Socket("localhost",4999);

       DataInputStream din = new DataInputStream(s.getInputStream());
       DataOutputStream dout = new DataOutputStream(s.getOutputStream());

       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	   dout.writeUTF(msgout);

	   msgin = din.readUTF();

	   System.out.println("Received login response from server :" + msgin);

	   Thread.sleep(2000);

	   Socket s2 = new Socket("localhost",5000);

	   Thread hb = new Thread(new Runnable(){
	   DataInputStream din2 = new DataInputStream(s2.getInputStream());
	   DataOutputStream dout2 = new DataOutputStream(s2.getOutputStream());
		@Override
		public void run() {

		  while(true){
			try {

				dout2.writeUTF("hbr");
			} catch (IOException e) {
				// TODO Auto-generated catch block
        System.out.println("exception");
				e.printStackTrace();
			}

			try {
				String msgin = din2.readUTF();
        System.out.println("From server :" + msgin);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				Thread.sleep(8000);
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
