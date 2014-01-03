package ml.humaning.test;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Vector;

import javax.imageio.ImageIO;



public class TestModel {
	public static void main(String [] argv){
		
		try{
			PrintWriter writer = new PrintWriter("correct.out", "UTF-8");
			
			
			for (int z =1 ; z<=12;z++){
				BufferedImage image = ImageIO.read(new File(z+".jpg"));
			
				int dimension = 105*105;
			
				int width = image.getWidth();
				int height = image.getHeight();
				int[][] result = new int[height][width];
				
				 writer.print(z);
			      for (int row = 0; row < height; row++ ) {
			    	 
			         for (int col = 0; col < width; col++) {
			            result[row][col] =  image.getRGB(col, row);
			            
			            if(result[row][col]< -10){
			            	writer.print(" "+ (row*105+col+1)+":" + 1);
			            	System.out.println("["+row+","+ col+"]"+ result[row][col]);
			            }
			            
			         }
			         
			      }
			      writer.println();
			}
			writer.close();
		      
		      
		}catch(Exception e){
			
		}
		
	}
}
