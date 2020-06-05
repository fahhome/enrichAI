import java.net.*;
import java.io.*;
import java.time.LocalDateTime;

public class gpslocationclient{


	public static void main(String[] args)   throws Exception {
		// TODO Auto-generated method stub
       String msgin = "";
	     GpsRequestPacket gp = new GpsRequestPacket();
       Socket s = new Socket("localhost",4999);
       DataInputStream din = new DataInputStream(s.getInputStream());
       DataOutputStream dout = new DataOutputStream(s.getOutputStream());
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	     dout.writeUTF("78 78 11 01 03 51 60 80 80 77 92 88 22 03 32 01 01 AA 53 36 0D 0A");
	     msgin = din.readUTF();
	     System.out.println("Received login response from server :" + msgin);
	     Thread.sleep(2000);
	     s.close();
       Thread.sleep(2000);
       Socket s2 = new Socket("localhost",5000);
       DataOutputStream dout2 = new DataOutputStream(s2.getOutputStream());
       Thread t = new Thread(new Runnable(){
         public void run(){

            while(true){
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

							 byte[] message = gp.toBytes();

              try{
                 Thread.sleep(4000);
                 dout2.write(message);
               }catch (Exception e) {
                 System.out.println("Exception occured");
               }
            }

         }
       });
       t.start();
	}

}
