
import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Client 
{

	public static void main(String args[]) throws UnknownHostException, IOException 
	{
		InetAddress address = InetAddress.getByName("localhost");
		Socket socket = new Socket(address, 6000);
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
		DataInputStream din = new DataInputStream(socket.getInputStream());

		Scanner sc = new Scanner(System.in);
		String name=sc.nextLine();
		try {
			dout.writeUTF(name);
		} catch (IOException e) {
			e.printStackTrace();
		}

		Test frame=new Test();
		frame.createFrame(name);
		Thread write = new Thread(new Runnable() 
		{
			@Override
			public void run() {
				while (true) {
					if(!frame.sent)
						try {
							Thread.sleep(1);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					if(frame.sent){
						String msg=frame.message;

						String URL="./docRoot/"+msg;

						try {


							if (frame.closed){
								dout.writeUTF("req");
								dout.writeUTF("GET "+URL+ " HTTP1.0");
								dout.writeUTF("yasmeen.com");
								StringTokenizer st=new StringTokenizer(msg,".");
								st.nextToken();
								dout.writeUTF(st.nextToken());
								dout.writeUTF("close");
								break;
							}
							dout.writeUTF("req");
							dout.writeUTF("GET "+URL+ " HTTP1.0");
							dout.writeUTF("yasmeen.com");
							StringTokenizer st=new StringTokenizer(msg,".");
							st.nextToken();
							dout.writeUTF(st.nextToken());
							dout.writeUTF("Keep-alive");


						} 
						catch (IOException e) {
							e.printStackTrace();
						}

					}
					frame.sent=false;
				}
			}
		});
		Thread read = new Thread(new Runnable() 
		{
			@Override
			public void run() {

				while (true) {

					try {
						String msg = din.readUTF();
						if(msg.equals("send")){

							String path=din.readUTF();


							System.out.println(path);

							long length=Long.parseLong(din.readUTF());

							File outfile =new File(path);





							FileOutputStream fos = null;
							BufferedOutputStream bos = null;
							try {
								fos = new FileOutputStream( outfile );
								int data;
								for (long i=0;i<length;i++)
								{
									data=din.read();
									fos.write(data);
								}

								System.out.println(length);


								System.out.println("out");


							} catch (IOException ex) {
								// Do exception handling
							}

						}
						else if(msg.equals("logout")){
							dout.writeUTF("logout");

							msg = din.readUTF();
							if(msg.equals("server_loged")){
								System.out.println("connection closed");

								socket.close();

								break;
							}
						}else{

							frame.output.append(msg);
							frame.output.append("\n");
							System.out.println(msg);
						}} catch (IOException e) {
							//System.out.println("error");
							e.printStackTrace();
						}
				}
			}
		});

		read.start();
		write.start();

	}
}

