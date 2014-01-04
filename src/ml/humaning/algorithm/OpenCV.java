package ml.humaning.algorithm;

import java.io.BufferedReader;

import java.io.FileReader;
import java.util.ArrayList;



import ml.humaning.util.InstancesHelper;
import weka.core.Instance;
import weka.core.Instances;

public class OpenCV extends Algorithm{

	private String ansSheetName = "./data/opencv.test.ans";
	@Override
	public String getName() {
		
		return "opencv";
	}

	@Override
	public void train(Instances trainData) throws Exception { }

	@Override
	public int predict(Instance inst) throws Exception {
		return 0;
	}
	
	public ArrayList<Integer> predict(Instances data) throws Exception {
		
		BufferedReader in = new BufferedReader(new FileReader(ansSheetName));
		
		ArrayList<Integer> ans = new ArrayList<Integer>();
		Integer val;
		
		while (in.ready()) {
			  val = new Integer(in.readLine());
			  ans.add(new Integer(val.intValue()-1));
		}
		in.close();
		
		return ans;
	}
	
	protected ArrayList<Integer> predictCV(int nFold, int fold) throws Exception {
		/* class i | 0 <= i <12 */
		
		//Read answer sheet
		BufferedReader in = new BufferedReader(new FileReader(ansSheetName));
		
		ArrayList<Integer> ans = new ArrayList<Integer>();
		Integer val;
		
		while (in.ready()) {
			  val = new Integer(in.readLine());
			  ans.add(new Integer(val.intValue()-1));
		}
		in.close();
		int size = ans.size();
		
		
		return (ArrayList<Integer>)ans.subList(fold * size/nFold , (fold + 1) * size/nFold);
		
		
		
	}
	
}
