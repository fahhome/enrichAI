import java.net.*;
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

	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub

		  String msgin = "" , msgout = "";
          ss = new ServerSocket(4999);
          s = ss.accept();
          din = new DataInputStream(s.getInputStream());
          dout = new DataOutputStream(s.getOutputStream());

          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          msgin = din.readUTF();

          System.out.println("login request from client : " + msgin);

          msgout = "login response";
          dout.writeUTF(msgout);
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
				String msgin2 = null;
				   try {
					 msgin2 = din2.readUTF();
					 System.out.println("from client :" + msgin2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		         String  msgout2 = "heartbeat response";

		          try {
					dout2.writeUTF(msgout2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
