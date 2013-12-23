package ml.humaning.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reader {

	public static Point [] readPoints(String fileName) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line;

		ArrayList <Point> tempList = new ArrayList<Point>();
		while((line = reader.readLine()) != null){
			tempList.add(new Point(line));
		}

		Point [] data = new Point[tempList.size()];
		data = tempList.toArray(data);

		reader.close();
		return data;

	}

}
