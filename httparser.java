import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class httparser {
	
	
	private String command;
	private String Path;
	private String Version;
	private String Response;
	private int contentlength;
	private BufferedReader in;
	private String from;
	private String to;
	private String subject;
	private String smtpserver;
	private String message;
	private String future;
	
	httparser (String Input,BufferedReader in)
	
	{
		
		this.in=in;
		check(Input);
		
		
		
		
	}
	
	
	



	public void check(String fline)
	{
		
		
		
		
		
		
		
		if (Character.isWhitespace(fline.charAt(0))) {
		      
		     badreq();
		return ;     
		
		}
		
		
		
		
		
		
		
		String[] result = fline.split("\\s");
		 if (result.length != 3) {
		      badreq();
		      return;
		    }
		
		command=result[0];
		Path=result[1];
		Version=result[2];
		
		if (!Version.equals("HTTP/1.1")&&!Version.equals("HTTP/1.0"))
		{        badreq();
	      return;                }
		
		
		if (!command.equals("GET")&&!command.equals("POST"))
		{
			badreq();
			return;
			
		}
		
		
		if (!Path.equals("/")&&!Path.equals("/index.html")&&!Path.equals("/status"))
		{
		
		notfound();
		return ;
		}
		
		try{
			
		 while ( !fline.equals("")) {
         	
             fline= in.readLine();
             
             String[] t=fline.split(":");
             if (t[0].equals("Content-Length"))
             {
            	
            	 contentlength=Integer.parseInt(t[1].trim());
            	 
             }
            
            
              }
		 
		 
		  if (Post()==true)
          {
        	   
        	   String operands="" ;
        	   char[] vessel;
        	   
        	  for(int i=0;i<contentlength;i++){
        		  
        		  vessel=Character.toChars(in.read());
        		  
        		 operands=operands+Character.toString(vessel[0]);
        		    
        		}
        	 
        	  
        	parsepostvars( operands);
        	  System.out.println( operands);
        	  System.out.println(from);
        	  System.out.println(to);
        	  System.out.println(subject);
        	  System.out.println(smtpserver);
        	  System.out.println(message);
        	  System.out.println(future);
        	  
        	  if (future==null)
        	  
        	  {sendmail s=new sendmail(from,to,subject,smtpserver,message);}
        	  else{
        		futuredel x=new futuredel(from,to,subject,smtpserver,message,future);
        		
        		
        		
        		wserver.list.add(x);
        		
        		x.start();
        		  
        	  }
        	  
        	  
        	  
        	   }
		
		 
		 
		 
		 
		 
		 
		 
		
		}
		catch (IOException e) {
			System.out.println(e.toString());
			return;
		}	
		
	
		
		
		
		
		
		if (Path.equals("/status"))
		{
			status();
			
		}
		else
			{ok();}
		
		
		
		
		
	}
		
	
	public void parsepostvars(String bigstring) throws UnsupportedEncodingException
	{
		String[] vessel;
		String[] vessel1;

		vessel=bigstring.split("&");
		
		vessel1=vessel[0].split("=");
		if (vessel1.length==2)
		{from=URLdecoder(vessel1[1]);}
		else {from=null;}
		
		vessel1=vessel[1].split("=");
		if (vessel1.length==2)
		{to=URLdecoder(vessel1[1]);}
		else {to=null;}
                
		vessel1=vessel[2].split("=");
		if (vessel1.length==2)
		{subject=URLdecoder(vessel1[1]);}
		else {subject=null;}
		
                vessel1=vessel[3].split("=");
		if (vessel1.length==2)
		{smtpserver=URLdecoder(vessel1[1]);}
		else {smtpserver=null;}
		
                vessel1=vessel[4].split("=");
		if (vessel1.length==2)
		{future=URLdecoder(vessel1[1]);}
		else {future=null;}
		
		
		 vessel1=vessel[5].split("=");
			if (vessel1.length==2)
			{message=URLdecoder(vessel1[1]);}
			else {message=null;}
		
		
		
		
	}
	
	
	
	public String from()
	{
		
		return from;
	}
	
	public String to()
	{
		
		return to;
	}
	
	
	public String subject()
	{
		
		return subject;
	}
	
	
	public String smtpserver()
	{
		
		return smtpserver;
	}
	
	
	
	public String message()
	{
		
		return message;
	}
	
	
	
	
	
	
	
	public boolean  Post()
	{
		
		
		
		if (command.equals("POST"))
		{
			
			Response="HTTP/1.1 201 CREATED\r\n";
			
			return true ;
		}
	
		return false ;
		
		
		
	}
	
	
	
	
	public void notfound()
	{
		Response="HTTP/1.0 404 Not Found\r\n"+
	     "Content-Type: text/html\r\n\r\n"+
		"<html><body><h1><b>404 File Not Found</b></h1></body></html>\r\n";		
		
	}
	
	
	
	public void ok()
	{
		
		Response="HTTP/1.1 200 OK\r\n" +
    		    "Content-Type: text/html;charset=iso-8859-15\r\n\r\n" +
    		    "<html><head></head><body><form action=\"\" name=\"contactform\" method=\"post\"><table width=\"400px\"><tr><td valign=\"top\"><label for=\"from\">From:</label></td><td valign=\"top\"><input  type=\"text\" name=\"from\" maxlength=\"80\" size=\"30\"></td></tr><tr>"+
    		    "<td valign=\"top\">"
    		   +"<label for=\"to\">To:</label>"+
 "</td> <td valign='top'> <input  type='text' name='to' maxlength='80' size='30'> </td> </tr><tr> <td valign=\"top\"> "+
 "<label for=\"subject\">Subject:</label> </td> <td valign=\"top\">  <input  type=\"text\" name=\"subject\" maxlength=\"80\" size=\"30\">  </td> "+
 "</tr><tr>  <td valign=\"top\"> <label for=\"smtpserver\">SMTP Server:</label> </td> <td valign=\"top\">  <input  type=\"text\" name=\"smtpserver\" maxlength=\"80\" size=\"30\">"+ 
 "</td> </tr><tr>  <td valign=\"top\"> <label for=\"future\">After how many seconds to send :</label> </td> <td valign=\"top\">  <input  type=\"text\" name=\"future\" maxlength=\"80\" size=\"30\">"+
 "</td> </tr> <tr> <td valign=\"top\">  <label for=\"message\">Message</label> </td> <td valign=\"top\"> <textarea  name=\"message\" maxlength=\"1000\" cols=\"25\" rows=\"6\"></textarea>"+ 
 "</td> </tr><tr> <td> <input type=\"submit\" value=\"Send\">   <input type=\"reset\" value=\"Reset\">  </td> </tr> </table></form></body></html>\r\n";
		
	}
		
	public void status()
	{
		
		
		
		
		
		Response="HTTP/1.1 200 OK\r\n" +
    		    "Content-Type: text/html;charset=iso-8859-15\r\n\r\n"+
				"<html><head>Status page</head><body>";
		
		
		for (int i=0; i<wserver.list.size();i++)
		{
			Response=Response+"<p>From: "+wserver.list.get(i).from+"<br>To: "+
		""+wserver.list.get(i).to+"<br>Subject: "+wserver.list.get(i).subject+
		"<br>Submission time: "+wserver.list.get(i).currentdate.getTime()+"<br>"+
		"Sending time: "+wserver.list.get(i).fdate.getTime()+"</p>";
			
		}
		
		
		Response=Response+"</body></html>";
		
		
	}
	
	
	
	
	
	public void badreq()
		{
			
			Response="HTTP/1.1 400 BAD REQUEST\r\n"+
					"Content-Type: text/html\r\n\r\n"+
					"<html><body><h1><b>400 BAD REQUEST</b></h1></body></html>\r\n";		
					
			
		}
		
		
		
		
public String resp()

{
	return Response;
}
	
	
	
	public String c()
	{
		return command;
		
	}
	
	
	public String  p()
	{
		return Path;
		
	}
	
	
	public String  v()
	{
		return Version;
		
	} 
	
	
    public String URLdecoder (String bagasakos)
    {
        
        if (bagasakos.contains("%25"))
        {
            bagasakos=bagasakos.replace("%25","%");                
        }
        
        if (bagasakos.contains("%40"))
        {
            bagasakos=bagasakos.replace("%40","@");                
        }
        
        if (bagasakos.contains("%3B"))
        {
            bagasakos=bagasakos.replace("%3B",";");                
        }
        
        if (bagasakos.contains("%3F"))
        {
            bagasakos=bagasakos.replace("%3F","?");                
        }
        
        if (bagasakos.contains("%2F"))
        {
            bagasakos=bagasakos.replace("%2F","/");                
        }
        
        if (bagasakos.contains("%3A"))
        {
            bagasakos=bagasakos.replace("%3A",":");                
        }
        
        if (bagasakos.contains("%23"))
        {
            bagasakos=bagasakos.replace("%23","#");                
        }
        
        if (bagasakos.contains("%26"))
        {
            bagasakos=bagasakos.replace("%26","&");                
        }
        
        if (bagasakos.contains("%3D"))
        {
            bagasakos=bagasakos.replace("%3D","=");                
        }
                    
        if (bagasakos.contains("%24"))
        {
            bagasakos=bagasakos.replace("%24","$");                
        }
        
        if (bagasakos.contains("%2C"))
        {
            bagasakos=bagasakos.replace("%2C",",");                
        }
        
        if ((bagasakos.contains("%20"))||(bagasakos.contains("+")))
        {
            bagasakos=bagasakos.replace("%20"," ");     
            bagasakos=bagasakos.replace("+"," ");
        }
        
        if (bagasakos.contains("%2B"))
        {
            bagasakos=bagasakos.replace("%2B","+");                
        }
        
        if (bagasakos.contains("%3C"))
        {
            bagasakos=bagasakos.replace("%3C","<");                
        }
        
        if (bagasakos.contains("%3E"))
        {
            bagasakos=bagasakos.replace("%3E",">");                
        }
        
        if (bagasakos.contains("%7E"))
        {
            bagasakos=bagasakos.replace("%7E","~");                
        }
        
        if (bagasakos.contains("%21"))
        {
            bagasakos=bagasakos.replace("%21","!");                
        }
        
        if (bagasakos.contains("%5E"))
        {
            bagasakos=bagasakos.replace("%5E","^");                
        }
        
        if (bagasakos.contains("%28"))
        {
            bagasakos=bagasakos.replace("%28","(");                
        }
        
        if (bagasakos.contains("%29"))
        {
            bagasakos=bagasakos.replace("%29",")");                
        }
        
        if (bagasakos.contains("%5C"))
        {
            bagasakos=bagasakos.replace("%5C","\\");                
        }
        
        if (bagasakos.contains("%7C"))
        {
            bagasakos=bagasakos.replace("%7C","|");                
        }
        
        if (bagasakos.contains("%27"))
        {
            bagasakos=bagasakos.replace("%27","'");                
        }
        
        if (bagasakos.contains("%22"))
        {
            bagasakos=bagasakos.replace("%22","\"");                
        }
        
        if (bagasakos.contains("%60"))
        {
            bagasakos=bagasakos.replace("%60","`");                
        }
        
        if (bagasakos.contains("%5B"))
        {
            bagasakos=bagasakos.replace("%5B","[");                
        }
        
        if (bagasakos.contains("%5D"))
        {
            bagasakos=bagasakos.replace("%5D","]");                
        }
        
        if (bagasakos.contains("%7B"))
        {
            bagasakos=bagasakos.replace("%7B","{");                
        }
        
        if (bagasakos.contains("%7D"))
        {
            bagasakos=bagasakos.replace("%7D","}");                
        }
             
        if (bagasakos.contains("%0D%0A"))
        {
            bagasakos=bagasakos.replace("%0D%0A","\r\n");                
        }
        
        if (bagasakos.contains("%E4"))
        {
            bagasakos=bagasakos.replace("%E4","ä");                
        }
        
        if (bagasakos.contains("%E5"))
        {
            bagasakos=bagasakos.replace("%E5","å");                
        }
        
        if (bagasakos.contains("%F6"))
        {
            bagasakos=bagasakos.replace("%F6","ö");                
        }
        
        if (bagasakos.contains("%C4"))
        {
            bagasakos=bagasakos.replace("%C4","Ä");                
        }
        
        if (bagasakos.contains("%C5"))
        {
            bagasakos=bagasakos.replace("%C5","Å");                
        }
        
        if (bagasakos.contains("%D6"))
        {
            bagasakos=bagasakos.replace("%D6","Ö");                
        }
        
        
        
        
        
        
        return bagasakos;
        }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
