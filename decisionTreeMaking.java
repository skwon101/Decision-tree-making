import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.StringTokenizer;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class decisionTreeMaking {
	public static void main(String[] args) throws IOException {
		String train=args[0];
		String test=args[1];
		BufferedReader br = new BufferedReader(new FileReader(train));
		ArrayList<ArrayList<Integer>> instances=new ArrayList<>();
		ArrayList<String> contents=new ArrayList<>();
		String line=br.readLine();
		line= line.replaceAll("\\s+"," ");
		String[] temp=line.split(" ");
		for(int i=0;i<temp.length;i++)
			contents.add(temp[i]);
		line=br.readLine();
		while(line!=null){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int i=0;i<temp.length;i++)
				data.add(Integer.parseInt(temp[i]));
			instances.add(data);
			line=br.readLine();	
		}
		br.close();
		decisionTree DT=new decisionTree();
		decisionTreeMaking(DT, instances, contents);
		
		System.out.println("----------Decision Tree----------");
		Stack<String> stack=new Stack<>();
		decisionTree tempDT=DT;
		stack.push(tempDT.content+"1");
		stack.push(tempDT.content+"0");
		while(!stack.empty()){
			String node=stack.pop();
			if(!tempDT.content.equals(node.substring(0, node.length()-1))){
				while(tempDT.parent!=null){
					tempDT=tempDT.parent;
					if(tempDT.content.equals(node.substring(0, node.length()-1)))
						break;
				}
			}
			for(int i=0;i<tempDT.level;i++)
				System.out.print("|");
			System.out.print(tempDT.content+" = ");
			if(node.charAt(node.length()-1)=='0'){
				System.out.print("0 : ");
				tempDT=tempDT.zero;
			}
			else if(node.charAt(node.length()-1)=='1'){
				System.out.print("1 : ");
				tempDT=tempDT.one;
			}
			if(tempDT.content=="0"||tempDT.content=="1")
				System.out.println(tempDT.content);
			else{
				System.out.println();
				stack.push(tempDT.content+"1");
				stack.push(tempDT.content+"0");
			}
		}
		double accuracyTrain=accuracy(DT, instances, contents);
		System.out.println("Accuracy on training set ("+instances.size()+
				" instances): "+accuracyTrain+"%");
		
		BufferedReader brTest = new BufferedReader(new FileReader(test));
		ArrayList<ArrayList<Integer>> instancesTest=new ArrayList<>();
		line=brTest.readLine();
		line=brTest.readLine();
		while(line!=null){
			ArrayList<Integer> dataTest=new ArrayList<>();
			temp=line.split("	");
			for(int i=0;i<temp.length;i++)
				dataTest.add(Integer.parseInt(temp[i]));
			instancesTest.add(dataTest);
			line=brTest.readLine();	
		}
		brTest.close();
		double accuracyTest=accuracy(DT, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+accuracyTest+"%");
		/*
		BufferedReader br2 = new BufferedReader(new FileReader("train2.dat"));
		ArrayList<ArrayList<Integer>> instances2=new ArrayList<>();
		ArrayList<String> contents2=new ArrayList<>();
		String[] temp2=new String[13];
		line=br2.readLine();
		line= line.replaceAll("\\s+"," ");
		temp2=line.split(" ");
		for(int i=0;i<temp2.length;i++)
			contents2.add(temp2[i]);
		line=br2.readLine();
		while(line!=null){
			ArrayList<Integer> data=new ArrayList<>();
			temp2=line.split("	");
			data.add(Integer.parseInt(temp2[0]));
			data.add(Integer.parseInt(temp2[1]));
			data.add(Integer.parseInt(temp2[2]));
			data.add(Integer.parseInt(temp2[3]));
			data.add(Integer.parseInt(temp2[4]));
			data.add(Integer.parseInt(temp2[5]));
			data.add(Integer.parseInt(temp2[6]));
			data.add(Integer.parseInt(temp2[7]));
			data.add(Integer.parseInt(temp2[8]));
			data.add(Integer.parseInt(temp2[9]));
			data.add(Integer.parseInt(temp2[10]));
			data.add(Integer.parseInt(temp2[11]));
			data.add(Integer.parseInt(temp2[12]));
			instances2.add(data);
			line=br2.readLine();	
		}
		br2.close();
		decisionTree DT2=new decisionTree();
		decisionTreeMaking(DT2, instances2, contents2);
		
		System.out.println("----------set2----------");
		Stack<String> stack2=new Stack<>();
		tempDT=DT2;
		stack.push(tempDT.content+"1");
		stack.push(tempDT.content+"0");
		while(!stack.empty()){
			String node=stack.pop();
			if(!tempDT.content.equals(node.substring(0, node.length()-1))){
				while(tempDT.parent!=null){
					tempDT=tempDT.parent;
					if(tempDT.content.equals(node.substring(0, node.length()-1)))
						break;
				}
			}
			for(int i=0;i<tempDT.level;i++)
				System.out.print("|");
			System.out.print(tempDT.content+" = ");
			if(node.charAt(node.length()-1)=='0'){
				System.out.print("0 : ");
				tempDT=tempDT.zero;
			}
			else if(node.charAt(node.length()-1)=='1'){
				System.out.print("1 : ");
				tempDT=tempDT.one;
			}
			if(tempDT.content=="0"||tempDT.content=="1")
				System.out.println(tempDT.content);
			else{
				System.out.println();
				stack.push(tempDT.content+"1");
				stack.push(tempDT.content+"0");
			}
		}
		double accuracyTrain2=accuracy(DT2, instances2, contents2);
		System.out.println("Accuracy on training set ("+instances2.size()+
				" instances): "+accuracyTrain2+"%");
		
		BufferedReader brTest2 = new BufferedReader(new FileReader("test2.dat"));
		ArrayList<ArrayList<Integer>> instancesTest2=new ArrayList<>();
		line=brTest2.readLine();
		line=brTest2.readLine();
		while(line!=null){
			ArrayList<Integer> dataTest=new ArrayList<>();
			temp=line.split("	");
			dataTest.add(Integer.parseInt(temp2[0]));
			dataTest.add(Integer.parseInt(temp2[1]));
			dataTest.add(Integer.parseInt(temp2[2]));
			dataTest.add(Integer.parseInt(temp2[3]));
			dataTest.add(Integer.parseInt(temp2[4]));
			dataTest.add(Integer.parseInt(temp2[5]));
			dataTest.add(Integer.parseInt(temp2[6]));
			dataTest.add(Integer.parseInt(temp2[7]));
			dataTest.add(Integer.parseInt(temp2[8]));
			dataTest.add(Integer.parseInt(temp2[9]));
			dataTest.add(Integer.parseInt(temp2[10]));
			dataTest.add(Integer.parseInt(temp2[11]));
			dataTest.add(Integer.parseInt(temp2[12]));
			instancesTest2.add(dataTest);
			line=brTest2.readLine();	
		}
		brTest2.close();
		double accuracyTest2=accuracy(DT2, instancesTest2, contents2);
		System.out.println("Accuracy on test set ("+instancesTest2.size()+
				" instances): "+accuracyTest2+"%");
		System.out.println();
		*/
		/*
		System.out.println("----------Learning Curve----------");
		BufferedReader BR = new BufferedReader(new FileReader("train.dat"));
		ArrayList<ArrayList<Integer>> Instances=new ArrayList<>();
		line=BR.readLine();
		line=BR.readLine();
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC=new decisionTree();
		decisionTreeMaking(DTForLC, Instances, contents);
		double AccuracyTrain=accuracy(DTForLC, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain+"%");
		double AccuracyTest=accuracy(DTForLC, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC2=new decisionTree();
		decisionTreeMaking(DTForLC2, Instances, contents);
		double AccuracyTrain2=accuracy(DTForLC2, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain2+"%");
		double AccuracyTest2=accuracy(DTForLC2, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest2+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC3=new decisionTree();
		decisionTreeMaking(DTForLC3, Instances, contents);
		double AccuracyTrain3=accuracy(DTForLC3, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain3+"%");
		double AccuracyTest3=accuracy(DTForLC3, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest3+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC4=new decisionTree();
		decisionTreeMaking(DTForLC4, Instances, contents);
		double AccuracyTrain4=accuracy(DTForLC4, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain4+"%");
		double AccuracyTest4=accuracy(DTForLC4, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest4+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC5=new decisionTree();
		decisionTreeMaking(DTForLC5, Instances, contents);
		double AccuracyTrain5=accuracy(DTForLC5, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain5+"%");
		double AccuracyTest5=accuracy(DTForLC5, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest5+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC6=new decisionTree();
		decisionTreeMaking(DTForLC6, Instances, contents);
		double AccuracyTrain6=accuracy(DTForLC6, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain6+"%");
		double AccuracyTest6=accuracy(DTForLC6, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest6+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC7=new decisionTree();
		decisionTreeMaking(DTForLC7, Instances, contents);
		double AccuracyTrain7=accuracy(DTForLC7, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain7+"%");
		double AccuracyTest7=accuracy(DTForLC7, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest7+"%");
		System.out.println();
		
		for(int i=0;i<100;i++){
			ArrayList<Integer> data=new ArrayList<>();
			temp=line.split("	");
			for(int j=0;j<temp.length;j++)
				data.add(Integer.parseInt(temp[j]));
			Instances.add(data);
			line=BR.readLine();	
		}
		decisionTree DTForLC8=new decisionTree();
		decisionTreeMaking(DTForLC8, Instances, contents);
		double AccuracyTrain8=accuracy(DTForLC8, Instances, contents);
		System.out.println("Accuracy on training set ("+Instances.size()+
				" instances): "+AccuracyTrain8+"%");
		double AccuracyTest8=accuracy(DTForLC8, instancesTest, contents);
		System.out.println("Accuracy on test set ("+instancesTest.size()+
				" instances): "+AccuracyTest8+"%");
		System.out.println();*/
	}
	
	public static void decisionTreeMaking(decisionTree DT, 
			ArrayList<ArrayList<Integer>> instances, ArrayList<String> contents){
		double entropy=entropy(instances,contents);
		DT.zero=new decisionTree();
		DT.one=new decisionTree();
		DT.zero.parent=DT;
		DT.one.parent=DT;
		DT.zero.level=DT.level+1;
		DT.one.level=DT.level+1;
		
		String content=topIG(entropy,instances,contents);
		int index=contents.indexOf(content);
		ArrayList<ArrayList<Integer>> One=new ArrayList<>();
		ArrayList<ArrayList<Integer>> Zero=new ArrayList<>();
		for(int i=0;i<instances.size();i++){
			if(instances.get(i).get(index)==0)
				Zero.add(instances.get(i));
			else if(instances.get(i).get(index)==1)
				One.add(instances.get(i));
		}
		double portionOfZero=(double)Zero.size()/
				(double)(Zero.size()+One.size());
		double entropyOfZero=entropy(Zero, contents);
		double portionOfOne=(double)One.size()/
				(double)(Zero.size()+One.size());
		double entropyOfOne=entropy(One, contents);
		double IG=entropy-portionOfZero*entropyOfZero-portionOfOne*entropyOfOne;
		if(IG==0){
			int numOfZero=0;
			for(int i=0;i<instances.size();i++){
				if(instances.get(i).get(contents.indexOf("class"))==0)
					numOfZero++;
			}
			int numOfOne=instances.size()-numOfZero;
			DT.one=null;
			DT.zero=null;
			if(numOfOne>numOfZero)
				DT.content="1";
			else{
				DT.content="0";
			}
		}
		else{
			DT.content=content;
			decisionTreeMaking(DT.zero, Zero, contents);
			decisionTreeMaking(DT.one, One, contents);
		}
	}
	
	public static double entropy(ArrayList<ArrayList<Integer>> instances, 
			ArrayList<String> contents){
		double entropy;
		double numOfTotal=instances.size();
		double numOfZero=0;
		for(int i=0;i<numOfTotal;i++){
			if(instances.get(i).get(contents.indexOf("class"))==0)
				numOfZero++;
		}
		double numOfOne=numOfTotal-numOfZero;
		if(numOfZero==0||numOfOne==0)
			entropy=0;
		else{
			entropy=-(numOfZero/numOfTotal)*
					(Math.log10(numOfZero/numOfTotal)/Math.log10(2))
					-(numOfOne/numOfTotal)*
					(Math.log10(numOfOne/numOfTotal)/Math.log10(2));
		}
		return entropy;
	}
	
	public static String topIG(double entropy, 
			ArrayList<ArrayList<Integer>> instances, ArrayList<String> contents){
		double topIG=0;
		int indexOfTopIG=0;
		for(int j=0;j<contents.size()-1;j++){
			ArrayList<ArrayList<Integer>> One=new ArrayList<>();
			ArrayList<ArrayList<Integer>> Zero=new ArrayList<>();
			int index=j;
			for(int i=0;i<instances.size();i++){
				if(instances.get(i).get(index)==0)
					Zero.add(instances.get(i));
				else if(instances.get(i).get(index)==1)
					One.add(instances.get(i));
			}
			double portionOfZero=(double)Zero.size()/
					(double)(Zero.size()+One.size());
			double entropyOfZero=entropy(Zero, contents);
			double portionOfOne=(double)One.size()/
					(double)(Zero.size()+One.size());
			double entropyOfOne=entropy(One, contents);
			double IG=entropy-portionOfZero*entropyOfZero-portionOfOne*entropyOfOne;
			//System.out.println(IG);
			if(IG>topIG){
				topIG=IG;
				indexOfTopIG=index;
			}
		}
		//System.out.println("---------");
		return contents.get(indexOfTopIG);
	}
	
	public static double accuracy(decisionTree DT, 
			ArrayList<ArrayList<Integer>> instances, ArrayList<String> contents){
		double correct=0;
		int result=0;
		for(int i=0;i<instances.size();i++){
			decisionTree tempTree=DT;
			while(tempTree!=null){
				if(tempTree.content=="0"||tempTree.content=="1"){
					result=Integer.parseInt(tempTree.content);
					break;
				}
				else{
					int way=instances.get(i).get(contents.indexOf(tempTree.content));
					if(way==1)
						tempTree=tempTree.one;
					else
						tempTree=tempTree.zero;
				}
			}
			if(instances.get(i).get(contents.indexOf("class"))==result)
					correct++;
			//System.out.println(correct);
		}
		double accuray=correct/(double)instances.size()*100;
		return accuray;
	}
}
