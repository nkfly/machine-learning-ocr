package ml.humaning.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Preprocess {
	/*  */
	/* public static int[] max_boundary = {105,0,0,105}; */
	/*  */
	/*  */
	/* private Dimension[] dimensionArray; */
	/* private int[] cropsize = new int[4]; */
	/* private Point point; */
	/*  */
	/* public Preprocess(Point point) { */
	/* 	this.point= point;  */
	/* 	this.dimensionArray =  point.getDimensionArray(); */
	/* 	 */
	/* 	//Crop(); */
	/* 	depthVector(); */
	/* 	 */
	/* 	 */
	/* } */
	/* public int getZodiac(){ */
	/* 	return point.getZodiac(); */
	/* } */
	/*  */
	/* public int[] depthVector(){ */
	/* 	 */
	/* 	int[][] depth_pixel = new int[8][54]; */
	/* 	 */
	/* 	for(int i = 0; i< depth_pixel.length; i++){ */
	/* 		 */
	/* 		for(int j = 0; j < depth_pixel[0].length; j++){ */
	/* 			depth_pixel[i][j] = 53; */
	/* 		} */
	/* 		 */
	/* 	} */
	/* 	 */
	/* 	for(Dimension d : dimensionArray){ */
	/* 		int pixel = d.getDimension(); */
	/* 		int region = point.getRegion(pixel); */
	/* 		 */
	/* 		int row = (pixel-1) / 105; */
	/* 		int col = (pixel-1) % 105; */
	/* 		 */
	/* 		if(region==1){  //0,7 */
	/* 			depth_pixel[0][col] = row; */
	/* 			depth_pixel[7][row] = col; */
	/* 			 */
	/* 		}else if(region==2){  //1,2 {col >52 } */
	/* 			 */
	/* 			depth_pixel[1][col-52] = row; */
	/* 			 */
	/* 			if(col<105) */
	/* 			depth_pixel[2][row] = 105 - col; */
	/* 			 */
	/* 		}else if(region==3){  //3,4 { row> 52 } */
	/* 			if(row-52<54)  */
	/* 				depth_pixel[6][row-52] = col; */
	/* 			 */
	/* 			if(row<105) */
	/* 				depth_pixel[5][col] = 105 - row; */
	/* 			 */
	/* 		}else if(region==4){  //5,6  { row> 52, col >52} */
	/* 			if(col<105 && row-52<54) */
	/* 				depth_pixel[3][row-52] = 105 - col; */
	/* 			if(row<105) */
	/* 				depth_pixel[4][col-52] = 105 - row; */
	/* 		} */
	/* 		 */
	/* 	} */
	/* 	 */
	/* 	int[] depth_vector = new int[8]; */
	/* 	 */
	/* 	for(int i = 0; i< depth_pixel.length; i++){ */
	/* 		depth_vector[i] = 0; */
	/* 		for(int j = 0; j < depth_pixel[0].length; j++){ */
	/* 			depth_vector[i] += depth_pixel[i][j]; */
	/* 		} */
	/* 	} */
	/* 	 */
	/* 	return depth_vector; */
	/* } */
	/*  */
	/* public void Crop() { */
	/* 	 */
	/* 	 */
	/* 	if(dimensionArray.length==0){  return; } */
	/* 	int top = (dimensionArray[0].getDimension()-1) / 105; */
	/* 	 */
	/* 	int bottom = (dimensionArray[dimensionArray.length-1].getDimension()-1) / 105; */
	/* 	 */
	/* 	int left = 105;  */
	/* 	int right = 0; */
	/* 	 */
	/* 	for(Dimension d : dimensionArray){ */
	/* 		int pos = d.getDimension() % 105; */
	/* 		 */
	/* 		 */
	/* 		if( pos < left ){ */
	/* 			left = pos; */
	/* 		} */
	/* 		if( pos > right ){ */
	/* 			right = pos; */
	/* 		} */
	/* 	} */
	/* 	System.out.println(top); */
	/* 	 */
	/* 	if(top< max_boundary[0]) */
	/* 		max_boundary[0] =top; */
	/* 	 */
	/* 	if(right > max_boundary[1]) */
	/* 		max_boundary[1] =right; */
	/* 	 */
	/* 	if(bottom > max_boundary[2]) */
	/* 		max_boundary[2] =bottom; */
	/* 	 */
	/* 	if(left < max_boundary[3]) */
	/* 		max_boundary[3] =left; */
	/* } */
	/*  */
	/* public static void cutThreshold(String input, String output, double threshold) throws IOException{ */
	/* 	 */
	/* 	BufferedReader br = new BufferedReader(new FileReader(input)); */
	/* 	String line; */
	/* 	BufferedWriter bw = new BufferedWriter(new FileWriter(output)); */
	/* 	while((line = br.readLine()) != null){ */
	/* 		Point p = new Point(line); */
	/* 		bw.write(p.cutThreshold(threshold)+"\n"); */
	/* 	} */
	/* 	 */
	/* 	br.close(); */
	/* 	bw.close(); */
	/* 	 */
	/* 	 */
	/* } */
	/*  */
}
