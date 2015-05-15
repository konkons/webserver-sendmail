import java.io.*;
import java.net.Socket;




public class difthread extends Thread {
	
	private Socket newSocket;
	private httparser reqhand;
	

	public difthread(Socket newSocket) {
		this.newSocket = newSocket;
	}

	
	public void run(){
		
		
		
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			in = new BufferedReader(new InputStreamReader(newSocket.getInputStream()));
			out = new PrintWriter(newSocket.getOutputStream());
			String string = null;
			string=in.readLine();
			
			reqhand =new httparser(string,in);            
            String response = reqhand.resp();
            out.print(response);

            			
		} catch (IOException e) {
			System.out.println(e.toString());
			return;
		}	
		//Closing the socket
		try {
			out.close();
			in.close();
			newSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return;
		
		
	}
}