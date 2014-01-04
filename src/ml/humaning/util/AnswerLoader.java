package ml.humaning.util;

import java.util.ArrayList;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class AnswerLoader {

	public static ArrayList<Integer> load(ArrayList<Integer> array, String path) throws IOException {
		int k;
		String line;

		FileInputStream fs = new FileInputStream(path);
		BufferedReader in = new BufferedReader(new InputStreamReader(fs));

		while((line = in.readLine()) != null) {
			k = Integer.parseInt(line) - 1;
			array.add(k);
		}

		return array;
	}
}
