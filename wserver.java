import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;





public class wserver {
	public static ArrayList<futuredel> list = new ArrayList <futuredel>();
	public static int l=0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 
		try {
        	
			
		            
			 ServerSocket serverSocket = new ServerSocket(999);//define the port to bind
		      

		          boolean status=true;
		            
		          
		         
		          while(status==true) {
		  			Socket newSocket = serverSocket.accept();
		  			(new difthread(newSocket)).start(); 
		  		}    
		            
		           

		            
		            
		            
		            serverSocket.close();
		        } catch (Exception exception) {
		            exception.printStackTrace();
		        }

		 
		
		
		

	}

	
	
	
}
