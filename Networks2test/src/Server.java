
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.net.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
class Helper implements Runnable 
{

	private String name;
	String path;
	final DataInputStream din;
	final DataOutputStream dout;
	Socket s;



	public Helper(Socket s, String name,DataInputStream din, DataOutputStream dout) {
		this.din = din;
		this.dout = dout;
		this.name = name;
		this.s = s;

		while(Server.name.contains(name)){
			try {
				dout.writeUTF("Username already in use");
				name=din.readUTF();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.name=name;
		Server.name.add(name);
		try {
			this.dout.writeUTF("Welcome");
		} catch (IOException e) {

			e.printStackTrace();
		}
		this.path="./"+name;
		File dir = new File(path);
		dir.mkdir();

	}

	@Override
	public void run() {

		String rec;
		while (true) 
		{
			try
			{


				rec = din.readUTF();



				if(rec.toLowerCase().equals("logout")){

					Server.name.remove(this.name);
					Server.mc.remove(this);
					dout.writeUTF("server_loged");
					dout.close();
					din.close();
					this.s.close();



					break;
				}
				else if(rec.toLowerCase().equals("req")){
					StringTokenizer st;
					Request r=new Request();
					String first=din.readUTF();
					st=new StringTokenizer(first);
					String method=st.nextToken();
					String URL=st.nextToken();
					String version=st.nextToken();
					String localhost=din.readUTF();
					String format=din.readUTF();
					String connection=din.readUTF();
					r.method=method;
					r.version=version;
					r.Connection=connection;
					r.format=format;
					r.localhost=localhost;
					r.client=this;
					r.URL=URL;
					Server.requests.add(r);
					
				}else {
					this.dout.writeUTF(Server.requests.toString());
				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		}
		try
		{

			this.din.close();
			this.dout.close();

		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
public class Server 
{


	static Queue<Request> processing=new LinkedList<>();
	static ArrayList<Helper> mc = new ArrayList<>();
	static HashSet<String> name=new HashSet<>();
	static Queue<Request> requests=new LinkedList<>();
	public static void respond(Request r) throws IOException{

		String URL=r.URL;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		String timestamp=dtf.format(now);
		String Status="";

		if(new File(URL).exists()){
			Status="200 OK";
			r.client.dout.writeUTF("send");

			try {

				StringTokenizer st=new StringTokenizer(URL,"/");
				st.nextToken();
				st.nextToken();

				r.client.dout.writeUTF(r.client.path+"/"+st.nextToken());

				File myFile = new File( URL );
				r.client.dout.writeUTF((long) myFile.length()+"");


				FileInputStream fis = null;

				try {
					fis = new FileInputStream(myFile);
				} catch (FileNotFoundException ex) {
					// Do exception handling
				}


				try {

					int data;
					while ((data = fis.read()) != -1)
					{
						r.client.dout.write(data);

					}

					fis.close();


				} catch (IOException ex) {
					// Do exception handling
				}

			}catch(Exception e){
				e.printStackTrace();
			}

		} else
			Status="404 Not Found";
		if(r.Connection.toLowerCase().equals("close")){

			r.client.dout.writeUTF(Status +" HTTP1.0 \n"+timestamp+"\n"+r.format+"\n"+r.Connection+"\n");
			r.client.dout.writeUTF("logout");
		}else{
			r.client.dout.writeUTF(Status +" HTTP1.0 \n"+timestamp+"\n"+r.format+"\n"+r.Connection+"\n");

		}
		processing.poll();

	}
	

	public static void main(String[] args) throws IOException, InterruptedException 
	{ Socket socket;

	ServerSocket ss = new ServerSocket(6000);
	Thread process = new Thread(new Runnable() 
	{
		@Override
		public void run() {
			while(true){
				if(requests.isEmpty())
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(!requests.isEmpty() ){
					if(processing.isEmpty()){
						//System.out.println(requests);
						Request r=requests.poll();
						processing.add(r);
						System.out.println(r.toString());

						try {
							respond(r);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
	});
	process.start();
	while (true) 
	{
		socket = ss.accept();


		DataInputStream din = new DataInputStream(socket.getInputStream());
		DataOutputStream dout = new DataOutputStream(socket.getOutputStream());


		String name=din.readUTF();

		Helper user = new Helper(socket,name, din, dout);


		Thread thread = new Thread(user);

		mc.add(user);
		thread.start();


	}

	}
}


