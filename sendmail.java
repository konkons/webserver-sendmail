import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class sendmail {
	
	private String from="konkon@kth.se";
	private String to="asd@kth.se";
	private String subject;
	private String smtpserver;
	private String message;
	public boolean mstatus=false;
	
	
	sendmail(String from,String to,String subject,String smtpserver,String message)
	{
		this.from=from;
		this.to=to;
		this.subject=subject;
		this.smtpserver=smtpserver;
		this.message=message;
		HaveAchatwithServer();
		
	}
	
	
	public void HaveAchatwithServer() 
	
	{

		BufferedReader in = null;
		BufferedWriter out = null;
		String host =smtpserver;
		
		if (smtpserver==null)
		{
			try {
				host=findip(to);
				String [] vessel=host.split("\\s");
				host=vessel[1];
				
				
			} catch (NamingException e) {
				
				e.printStackTrace();
			}
			
		}
		
		try{
	  Socket socket=new Socket(InetAddress.getByName(host),25);
	  in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	  String v;
	  v=in.readLine();
	  System.out.println(v);
		out.write("HELO "+host);
		System.out.println("HELO "+smtpserver);
		out.newLine();
		out.flush();
		v=in.readLine();
		
		System.out.println(v);
		System.out.println("MAIL FROM:"+"<"+from+">"+"\r\n");
		out.write("MAIL FROM:"+"<"+from +">"+"\r\n");
		out.flush();
		v=in.readLine();
		System.out.println(v);
		if (!v.equals("250 2.1.0 Ok")) {
			System.out.println("FROM error");
			socket.close();
			return ;
		}
	  
	  out.write("RCPT TO: " + "<" + to + ">");
	  out.newLine();
		out.flush();
	  v=in.readLine();
	  System.out.println(v);
	  if (!v.equals("250 2.1.5 Ok")) {
			System.out.println("RCPT error");
			socket.close();
			return ;
		}
		
		out.write("DATA");
		out.newLine();
		out.flush();
		out.write("MIME-Version: 1.1\r\n");
		out.write("Content-Type: text/plain\r\n");
		out.write("Content-Transfer-Encoding: quoted-printable\r\n");
		v=in.readLine();
		System.out.println(v);
		
		
		out.write("From: " +from+ "\r\n");
		out.write("To: " + to + "\r\n");
		out.write("Subject: =?ISO-8859-1?Q?" + quotedPrintable(subject) + "?=\r\n");
		out.write("\r\n"); 
		
		
		out.write(quotedPrintable(message)+"\r\n");
		out.write("\r\n.\r\n");
		out.flush();
		v=in.readLine();
		System.out.println(v);
		if (!(v.indexOf("250 2.0.0 Ok") != -1)) {
			System.out.println("Error in Body");
			socket.close();
			return ;
			}
		mstatus=true;
		
		out.write("QUIT");
		out.newLine();
		out.flush();
		in.close();
		out.close();
		
		
		
	  socket.close();
		
		
		
		}catch (Exception exception) {
            exception.printStackTrace();
        }
	
	
		
	}
	
	
	public String findip(String hostname) throws NamingException 
	{ 

		int pos = hostname.indexOf('@');
		String domain = hostname.substring(++pos);

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(domain, new String[] { "MX" });
		Attribute attr = attrs.get("MX");

		if ((attr == null) || (attr.size() == 0)) {
			attrs = ictx.getAttributes(domain, new String[] { "A" });
			attr = attrs.get("A");
		}
		if (attr == null)
			return "Couldn't find server";
		return (String) (attr.get());
	}
	
	
	
	private static String quotedPrintable(String msg) {
		
		int linesize =0;
		StringBuffer vessel = new StringBuffer();
		for(int i = 0; i < msg.length(); i++) {	
			char c = msg.charAt(i);

			//Tab and Space
			if((c == 9 || c == 32)) {
				vessel.append('=');
				vessel.append(toHex(c));
				linesize += 3;
			}
			//This part will remain the same
			else if((c >= 33 && c <= 60) || c==62 || (c >= 64 && c <= 94) || (c >= 96 && c<=126)) {
				vessel.append(c);
				linesize++;
			}

			else {
				vessel.append('=');
				vessel.append(toHex(c));
				linesize += 3;
			}
			//Max characters in one line should be 76 RFC 2047
			if(linesize == 76) {
				vessel.append("\r\n");
				linesize = 0;
			}
		}
		return vessel.toString();
		
	}
	
	
	
	
	
	
	
		private static String toHex(char c) {
			String hex = Integer.toHexString((int)c).toUpperCase();
			if(hex.length()==1) {
				hex = "0" + hex;
			}
			return hex;
		}
	
	
	
	
	
	
	
	

}