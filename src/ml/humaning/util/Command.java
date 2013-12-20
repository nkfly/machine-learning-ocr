package ml.humaning.util;

import java.io.IOException;
import java.io.InputStream;

public class Command {
	Process process;
	StringBuffer outBuffer;
	public Command(){
		
	}
	
	public void call(String command) throws IOException, InterruptedException{
		process = Runtime.getRuntime().exec(command);
		outBuffer = new StringBuffer();
		int nextChar;
		process.waitFor();
		InputStream outStream = process.getInputStream();
		while( (nextChar = outStream.read()) != -1 )
		   {
		    outBuffer.append((char)nextChar);
		   }
		
	}
	
	public String getStdout(){
		return outBuffer.toString();
	}
	

}
