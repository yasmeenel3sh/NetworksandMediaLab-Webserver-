import java.net.Socket;


public class Request {
String URL;
String format;
String Connection;
Helper client;
String method;
String localhost;
String version;
public Request(){
	
}
public Request(String method,String URL,String version,String localhost,String format,String Connection,Helper client){
	this.method=method;
	this.URL=URL;
	this.format=format;
	this.Connection=Connection;
	this.client=client;
	this.version=version;
	this.localhost=localhost;
}

public String toString(){
	return this.method+" "+this.URL+" "+this.version+"\n"+this.localhost+"\n"+this.format+ "\n"+this.Connection;
}
}
