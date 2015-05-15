import java.util.Calendar;
import java.util.Date;

public class futuredel extends Thread {

	public String from;
	public String to;
	public String subject;
	public String smtpserver;
	public String message;
	public Calendar fdate;
	public boolean objstatus=true;
	public Calendar currentdate;
	
	futuredel(String from,String to,String subject,String smtpserver,String message,String future)
	{
		this.from=from;
		this.to=to;
		this.subject=subject;
		this.smtpserver=smtpserver;
		this.message=message;
		currentdate=Calendar.getInstance();
		fdate=Calendar.getInstance();
		fdate.add(Calendar.SECOND, Integer.parseInt(future));
		
	}
	
	public void run ()
	
	{System.out.println("mesa sto threadidi");
		while (true)
		
		{  Calendar ctime=Calendar.getInstance();
			if (fdate.getTime().equals(ctime.getTime())||fdate.getTime().before(ctime.getTime()))
			{
				sendmail s=new sendmail(from,to,subject,smtpserver,message);
				if (s.mstatus==true)
				{
					sendmail status=new sendmail("noreply@ik2213.lab",from,"Mail successfully sent",smtpserver,"Mail successfully sent to: "+to);
					objstatus=false;
					for (int j=0;j<wserver.list.size();j++)
					{
						if (wserver.list.get(j).objstatus==false)
						{
							wserver.list.remove(j);
						}
						
					}
					break;
				}
				
				if (s.mstatus==false)
				{
					
					sendmail status=new sendmail("noreply@ik2213.lab",from,"Mail failed to sent",smtpserver,"Mail failed to sent to"+to);		
					objstatus=false;
					
					for (int j=0;j<wserver.list.size();j++)
					{
						if (wserver.list.get(j).objstatus==false)
						{
							wserver.list.remove(j);
						}
						
					}
					
					
					
					
					
					break;
				}
					
					
				
				
			}
			
		}
		
		return ;
	
	}
	
	
	
	
	
	
}
