package ml.humaning.util;

import java.io.IOException;
import java.io.InputStream;

public class Command {
	StringBuffer outBuffer;
	String command = "";
	public Command(){
		
	}
	
	public void add(String param){
		command = param + " ";	
	}
	
	private void call(Process process) throws InterruptedException, IOException{
		outBuffer = new StringBuffer();
		int nextChar;
		process.waitFor();
		InputStream outStream = process.getInputStream();
		while( (nextChar = outStream.read()) != -1 )
		   {
		    outBuffer.append((char)nextChar);
		   }
	}
	
	public void call(String command) throws IOException, InterruptedException{
		call(Runtime.getRuntime().exec(command));
		
	}
	
	public void call(String [] command) throws IOException, InterruptedException{
		call(Runtime.getRuntime().exec(command));		
	}
	
	public String getStdout(){
		return outBuffer.toString();
	}
	
	@Override
	public String toString(){
		return command;
		
	}
	

}
