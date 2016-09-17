package util;

import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.poi.util.IntegerField;

import problem.definition.State;

public class Load {

	private ArrayList<String> instanceFile;

	public Load() {
		super();
		instanceFile = new ArrayList<String>();
		// TODO Auto-generated constructor stub
	}

	public ArrayList<String> getInstanceFile() {
		return instanceFile;
	}

	public void setInstanceFile(ArrayList<String> instanceFile) {
		this.instanceFile = instanceFile;
	}

	public boolean findEndElement(String lines){
		return lines.indexOf("EOF") != -1;
	}

	public boolean loadFile(String pathFile) throws IOException{
		boolean load = false;
		LineNumberReader line = new LineNumberReader(new FileReader(pathFile));
		String cad = new String();
		instanceFile = new ArrayList<String>();
		instanceFile.clear();

		while(!findEndElement(cad))
		{
			cad = line.readLine();
			if(cad != null){
				instanceFile.add(cad);
				load = true;
			}
			else{
				load = false;
				break;
			}
		}
		line.close();
		return load;
	}

	public State loadSolutions(String cad){	
		State solution = new State();	
		StringTokenizer tool = new StringTokenizer(cad, ",");

//		int run = Integer.valueOf(tool.nextToken());// para anita frente true
		int codeLenght = Integer.valueOf(tool.nextToken());

		for (int i = 0; i < codeLenght; i++) 
		{
			String  element = tool.nextToken();
			solution.getCode().add(element);
		}

		ArrayList<Double> evaluations = new ArrayList<Double>();
		int cantFO = Integer.valueOf(tool.nextToken());

		for(int j = 0; j < cantFO; j++ )
			evaluations.add(Double.valueOf(tool.nextToken()));

		solution.setEvaluation(evaluations);

		return solution;
	}
	public State loadSolutionsA(String cad){	
		State solution = new State();	
		StringTokenizer tool = new StringTokenizer(cad, ",");

		int run = Integer.valueOf(tool.nextToken());// para anita
		int codeLenght = Integer.valueOf(tool.nextToken());

		for (int i = 0; i < codeLenght; i++) 
		{
			String  element = tool.nextToken();
			solution.getCode().add(element);
		}

		ArrayList<Double> evaluations = new ArrayList<Double>();
		int cantFO = Integer.valueOf(tool.nextToken());

		for(int j = 0; j < cantFO; j++ )
			evaluations.add(Double.valueOf(tool.nextToken()));

		solution.setEvaluation(evaluations);

		return solution;
	}
	public ArrayList<State> createRepository(){
		ArrayList<State> solutionsList = new ArrayList<State>();

		for(int i = 1; i < instanceFile.size(); i++)
			solutionsList.add(loadSolutions(instanceFile.get(i)));

		return solutionsList;
	}	

	public ArrayList<Auxiliar> testing() {
		Auxiliar auxiliar = new Auxiliar();
		ArrayList<Auxiliar> solutionsList = new ArrayList<Auxiliar>();
		int run, run1;

		for (int i = 0; i < instanceFile.size(); i++) {

			if(i == 0) {
				auxiliar.setRun(i);
			State state = new State(loadSolutionsA(instanceFile.get(i)));
				auxiliar.getListFCurrentTotal().add(state);

			}
			else {
				StringTokenizer tool1 = new StringTokenizer(instanceFile.get(i - 1), ",");
				StringTokenizer tool = new StringTokenizer(instanceFile.get(i), ",");
				run = Integer.valueOf(tool.nextToken());// para anita
				run1 = Integer.valueOf(tool1.nextToken());

				if (run == run1) 
					auxiliar.getListFCurrentTotal().add(loadSolutionsA(instanceFile.get(i)));

				else {
					
					Auxiliar temp = new Auxiliar();
					temp.getListFCurrentTotal().addAll(auxiliar.getListFCurrentTotal());
					temp.setRun(run1);
					solutionsList.add(temp);
					auxiliar.getListFCurrentTotal().clear();
					auxiliar.setRun(run);
					auxiliar.getListFCurrentTotal().add(loadSolutionsA(instanceFile.get(i)));
				}
				if (i==instanceFile.size()-1) {
					Auxiliar temp = new Auxiliar();
					temp.getListFCurrentTotal().addAll(auxiliar.getListFCurrentTotal());
					temp.setRun(run1);
					solutionsList.add(temp);
				}
			}
		}
		return solutionsList;
	}


}
