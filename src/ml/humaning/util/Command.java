package ml.humaning.util;

import java.io.IOException;
import java.io.InputStream;

public class Command {
	StringBuffer outBuffer;
	Process process;
	public Command(){

	}

	public void call(String command) throws IOException, InterruptedException{
		process = Runtime.getRuntime().exec(command);
		process.waitFor();

	}

	public void call(String [] command) throws IOException, InterruptedException{
		process = Runtime.getRuntime().exec(command);
		process.waitFor();

	}

	public String getStdout() throws IOException{
		outBuffer = new StringBuffer();
		int nextChar;
		InputStream outStream = process.getInputStream();
		while( (nextChar = outStream.read()) != -1 )
		   {
		    outBuffer.append((char)nextChar);
		   }
		return outBuffer.toString();
	}

//	private class ThreadWorker implements Runnable{
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//
//		}
//
//	}




}
