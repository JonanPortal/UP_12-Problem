

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.Match;

import evolutionary_algorithms.complement.CrossoverType;
import evolutionary_algorithms.complement.MutationType;
import evolutionary_algorithms.complement.ReplaceType;
import evolutionary_algorithms.complement.SelectionType;
import jxl.read.biff.BiffException;
import local_search.acceptation_type.Dominance;
import local_search.complement.StopExecute;
import local_search.complement.TabuSolutions;
import local_search.complement.UpdateParameter;
import metaheurictics.strategy.Strategy;
import metaheuristics.generators.*;
import problem.definition.ObjetiveFunction;
import problem.definition.Problem;
import problem.definition.State;
import problem.definition.Problem.ProblemType;
import problem.extension.MetricasMultiobjetivo;
import problem.extension.TypeSolutionMethod;
import util.Auxiliar;
import util.Load;
import util.MetricsMO;
import util.SaveTxt;


public class Tester {

	Problem problem;
	int countmaxIterations = 50000;
	int operatornumber = 1;
	int ejecuciones = 30;
	ArrayList<String> instanceFile;
	MetricasMultiobjetivo metrica = new MetricasMultiobjetivo();

	public String pathFile = "results//"; 

	public void configUP_12Problem(){
		// Inicialización y Asignación de las clases del problema
		this.problem = new Problem();
		TypeSolutionMethod solutionmethod = TypeSolutionMethod.MultiObjetivoPuro;
		UP_12MutationOperator operator = new UP_12MutationOperator();
		UP_12ObjetiveFunction1 objFunction1 = new UP_12ObjetiveFunction1();
		UP_12ObjetiveFunctionPares objFunctionPares = new UP_12ObjetiveFunctionPares();
		objFunction1.setTypeProblem(ProblemType.Maximizar);
		objFunction1.setWeight((float) 0.3);
		objFunctionPares.setTypeProblem(ProblemType.Maximizar);
		objFunctionPares.setWeight((float) 0.7);
		UP_12Codification codification = new UP_12Codification();
		ArrayList<ObjetiveFunction>listObjFunt= new ArrayList<ObjetiveFunction>();
		listObjFunt.add(objFunction1);
		listObjFunt.add(objFunctionPares);
		this.problem.setCodification(codification);
		this.problem.setFunction(listObjFunt);
		this.problem.setOperator(operator);
		this.problem.setTypeProblem(ProblemType.Maximizar);
		this.problem.setTypeSolutionMethod(solutionmethod);
		this.problem.setState(new State());
		Strategy.getStrategy().validate=true;
	}

	public void EC() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		configUP_12Problem();
		ArrayList<Double>averages=new ArrayList<Double>();
		//Configurar el algoritmo, dentro de BiCIAM
		for(int i =0;i<ejecuciones;i++){
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			//			Strategy.getStrategy().saveFreneParetoMonoObjetivo=true;
			Strategy.getStrategy().saveListBestStates=true;

			//			Strategy.getStrategy().validate=true;
			//			Strategy.getStrategy().posibleValidateNumber=1;

			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.HillClimbing);
			double average=CalcularMedia(Strategy.getStrategy().listBest);
			averages.add(average);
		}
		SaveTxt.writeFileTxtAverage(pathFile + "FP/", "EC-FP" + ".txt", averages);
		averages.clear();
		Strategy.destroyExecute();
	}
	public double CalcularMedia(List<State> listBest){
		double sum = 0;
		for (int i = 0; i < listBest.size(); i++) {

			double element = listBest.get(i).getEvaluation().get(0);
			sum = sum + element;
		}
		double media = sum/listBest.size();
		return media;
	}
	protected void ECMOR() throws Exception{
		configUP_12Problem();
		for (int i = 0; i < ejecuciones; i++) {
			//Configurar el algoritmo, dentro de BiCIAM
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			MultiobjectiveHillClimbingRestart.sizeNeighbors=2;
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.MultiobjectiveHillClimbingRestart);
			SaveTxt.writeFileTxt(pathFile + "file/", "ECMOR_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	
	protected void ECMOD() throws Exception{
		configUP_12Problem();
		for (int i = 0; i < ejecuciones; i++) {
			//Configurar el algoritmo, dentro de BiCIAM
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			MultiobjectiveHillClimbingDistance.sizeNeighbors=2;
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.MultiobjectiveHillClimbingDistance);
			SaveTxt.writeFileTxt(pathFile + "file/", "ECMOD_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	
	public void ECMO() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, BiffException {
		configUP_12Problem();
		for(int i =0;i<ejecuciones;i++){
			//Configurar el algoritmo, dentro de BiCIAM
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.MultiobjectiveStochasticHillClimbing);
			//guarda en un fichero la lista de soluciones no dominadas
			SaveTxt.writeFileTxt(pathFile + "file/", "ECMO_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	public void AG() throws IllegalArgumentException, SecurityException, ClassNotFoundException, 
	InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		configUP_12Problem();
		ArrayList<Double>averages=new ArrayList<Double>();
		for (int i = 0; i < ejecuciones; i++) {
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			GeneticAlgorithm.countRef = 50; //cantidad de individuos
			GeneticAlgorithm.selectionType= SelectionType.TournamentSelection;
			GeneticAlgorithm.crossoverType = CrossoverType.OnePointCrossover;
			GeneticAlgorithm.mutationType = MutationType.OnePointMutation;
			GeneticAlgorithm.replaceType = ReplaceType.SteadyStateReplace;
			GeneticAlgorithm.truncation=1;
			GeneticAlgorithm.PM = 0.5;
			GeneticAlgorithm.PC = 0.9;
			Strategy.getStrategy().saveListBestStates=true;
			//			Strategy.getStrategy().saveFreneParetoMonoObjetivo=true;
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.RandomSearch);
			double average=CalcularMedia(Strategy.getStrategy().listBest);
			averages.add(average);
		}
		SaveTxt.writeFileTxtAverage(pathFile + "FP/", "AG-FP" + ".txt", averages);
		averages.clear();
		Strategy.destroyExecute();
	}
	public void NSGAII() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, BiffException {
		configUP_12Problem();
		for(int i =0;i<ejecuciones;i++){
			//Configurar el algoritmo, dentro de BiCIAM
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			NSGAII.countRef = 20; //cantidad de individuos
			NSGAII.selectionType= SelectionType.TournamentSelection;
			NSGAII.crossoverType = CrossoverType.OnePointCrossover;
			NSGAII.mutationType = MutationType.TowPointsMutation;
			Strategy.getStrategy().calculateTime = true;
			NSGAII.PM = 0.9;
			NSGAII.PC = 0.5;
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.RandomSearch);
			//guarda en un fichero la lista de soluciones no dominadas
			System.out.println("time:"+ Strategy.getStrategy().timeExecute);
			SaveTxt.writeFileTxt(pathFile + "file/", "NSGAII_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	
	//MOEA/D-DE
	
	public void MOEADDE() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, BiffException {
		configUP_12Problem();
		for(int i =0;i<ejecuciones;i++){
			//Configurar el algoritmo, dentro de BiCIAM
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			MOEADDE.countRef = 20; //cantidad de individuos
			MOEADDE.crossoverType = CrossoverType.OnePointCrossover;
			MOEADDE.mutationType= MutationType.TowPointsMutation;
			Strategy.getStrategy().calculateTime = true;
			MOEADDE.PS = 0.5;
			MOEADDE.PC = 0.5;
			MOEADDE.PM = 0.9;
			MOEADDE.T=(int) (0.1*MOEADDE.countRef);
			MOEADDE.Nr=(int) (0.1*MOEADDE.countRef);
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.RandomSearch);
			//guarda en un fichero la lista de soluciones no dominadas
			System.out.println("time:"+ Strategy.getStrategy().timeExecute);
			SaveTxt.writeFileTxt(pathFile + "file/", "MOEADDE_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	
	//Recorrido Simulado 
	public void RS() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		configUP_12Problem();
		ArrayList<Double>averages=new ArrayList<Double>();
		for(int i =0;i<ejecuciones;i++){
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			SimulatedAnnealing.countIterationsT=100;
			SimulatedAnnealing.alpha=0.9;
			SimulatedAnnealing.tfinal=0.0;
			SimulatedAnnealing.tinitial=20.0;
			Strategy.getStrategy().saveListBestStates=true;
			Strategy.getStrategy().setProblem(this.problem);
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.SimulatedAnnealing);
			double average=CalcularMedia(Strategy.getStrategy().listBest);
			averages.add(average);
		}
		SaveTxt.writeFileTxtAverage(pathFile + "FP/", "RS-FP" + ".txt", averages);
		averages.clear();
		Strategy.destroyExecute();
	}

	public void RSMO() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, BiffException {
		//Configurar el Problema
		configUP_12Problem();
		for(int i =0;i<ejecuciones;i++){
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			MultiCaseSimulatedAnnealing.countIterationsT=30;
			MultiCaseSimulatedAnnealing.alpha=0.9;
			MultiCaseSimulatedAnnealing.tfinal=0.0;
			MultiCaseSimulatedAnnealing.tinitial=20.0;
			Strategy.getStrategy().setProblem(this.problem);
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.MultiCaseSimulatedAnnealing);
			SaveTxt.writeFileTxt(pathFile + "file/", "RSMO_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	public void UMOSA() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, BiffException {
		//Configurar el Problema
		configUP_12Problem();
		for(int i =0;i<ejecuciones;i++){
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			UMOSA.countIterationsT=30;
			UMOSA.alpha=0.9;
			UMOSA.tfinal=0.0;
			UMOSA.tinitial=20.0;
			Strategy.getStrategy().setProblem(this.problem);
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.UMOSA);
			SaveTxt.writeFileTxt(pathFile + "file/", "UMOSA_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}

	public void BT() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		configUP_12Problem();
		ArrayList<Double>averages=new ArrayList<Double>();
		for(int i =0; i<ejecuciones;i++){
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			TabuSolutions.maxelements=20;
			Strategy.getStrategy().saveListBestStates=true;
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.TabuSearch);
			double average=CalcularMedia(Strategy.getStrategy().listBest);
			averages.add(average);
		}
		SaveTxt.writeFileTxtAverage(pathFile + "FP/", "BT-FP" + ".txt", averages);
		averages.clear();
		Strategy.destroyExecute();
	}

	public void BTMO() throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, 
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException, BiffException {
		configUP_12Problem();
		for(int i =0; i<ejecuciones;i++){
			Strategy.getStrategy().setStopexecute(new StopExecute());
			Strategy.getStrategy().setUpdateparameter(new UpdateParameter());
			Strategy.getStrategy().setProblem(this.problem);
			TabuSolutions.maxelements=20;
			Strategy.getStrategy().executeStrategy(countmaxIterations, operatornumber, GeneratorType.MultiobjectiveTabuSearch);
			SaveTxt.writeFileTxt(pathFile + "file/", "BTMO_" + i + ".txt", Strategy.getStrategy().listRefPoblacFinal);
		}
		Strategy.destroyExecute();
	}
	public void writeFileFrenteTrue(String fichero, ArrayList<State> list) {
		try {
			FileWriter fw = new FileWriter("resultados//" + fichero);// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			title.print("TamañoCode");
			title.print(",");
			title.print("Xa");
			title.print(",");
			title.print("F(Xa)");
			title.print(",");
			title.println();
			title.close();
			for (int j = 0; j < list.size(); j++) {
				bw = new BufferedWriter(new FileWriter("resultados//" + fichero,true));// buffer para el manejo de los datos
				PrintWriter writer = new PrintWriter(bw);
				writer.print(list.get(j).getCode().size());
				writer.print(",");
				writer.print(list.get(j).getCode().toString());
				writer.print(",");
				writer.print(list.get(j).getEvaluation());
				writer.print(",");
				writer.println();
				writer.close();
				//j+=100;
			} 
		}
		catch(java.io.IOException ioex) {
		}
	}
	public void BuscarFrenteParetoV ( List<State>frentePareto){
		Strategy.getStrategy().setProblem(this.problem);
		int i = 0;
		while(i < frentePareto.size()){
			State sol = frentePareto.get(i);
			boolean find = ListDominance(sol, frentePareto);
			if(find==false)
				i = 0;
			else i++;
			System.out.println("iter"+i);
		}

		writeFileFrenteTrue("resultadosFPt_" + ".txt", (ArrayList<State>) frentePareto);
	}
	public boolean ListDominance(State solutionX, List<State> list){
		Dominance dom=new Dominance();
		boolean domain = false;
		List<State> deletedSolution = new ArrayList<State>();
		for (int i = 0; i < list.size() && domain == false; i++) {
			State element = list.get(i);

			//Si la solución X domina a la solución de la lista
			if(dom.dominance(solutionX, element) == true){
				//Se elimina el elemento de la lista
				deletedSolution.add(element);
			}
			if(dom.dominance(element, solutionX) == true){
				domain = true;
				list.remove(solutionX);
			}
		}
		//Si la solución X no fue dominada
		if(domain == false){

			//Se eliminan de la lista de soluciones optimas de pareto aquellas que fueron dominadas por la solución candidata
			list.removeAll(deletedSolution);
			//			Strategy.getStrategy().listRefPoblacFinal = list;
			return true;
		}
		return false;
	}
	/*public  boolean findEndElement(String lines){
		return lines.indexOf("FIN") != -1;
	}

	public  boolean loadFile(String pathFile) throws IOException{
		boolean load = false;
		LineNumberReader line = new LineNumberReader(new FileReader(pathFile));
		String cad = new String();
		instanceFile = new ArrayList<String>();
		instanceFile.clear();

		while(! findEndElement(pathFile ))
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

	public State loadBody(String cad){	
		State state = new State();	
		StringTokenizer tool = new StringTokenizer(cad, ",");


		//ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList< Double> listEvaluate = new ArrayList<Double>();
//		int iteration = Integer.valueOf(tool.nextToken());
		int codeLenght = Integer.valueOf(tool.nextToken());

		for (int i = 0; i < codeLenght; i++) {

			String  ob = tool.nextToken();
			state.getCode().add(ob);
		}
		for(int j=0; j < problem.getFunction().size(); j++ )
			//			listEvaluate.add(Double.valueOf(tool.nextToken()));
			listEvaluate.add(Double.valueOf(tool.nextToken()));
		state.setEvaluation(listEvaluate);

		return state;
	}

	public ArrayList<State>  loadFP(){
		ArrayList<State> list = new ArrayList<State>();
		for(int i = 1; i < (instanceFile.size()); i++)
			list.add(loadBody(instanceFile.get(i)));
		return list;


	}*/
	private static void writeFileMetricas(String fichero, ArrayList<Double>listMetricas, double min, double max, double media) {// fichero para las metricas
		try {
			FileWriter fw = new FileWriter("resultados//" + fichero);// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			title.print("CantElementos");
			title.print(";");
			title.print("Metrica");
			title.print(";");
			title.print("Minimo");
			title.print(";");
			title.print("Máximo");
			title.print(";");
			title.print("Media");
			title.print(";");
			title.println();
			title.close();
			for (int j = 0; j < listMetricas.size(); j++) {
				bw = new BufferedWriter(new FileWriter("resultados//" + fichero, true));// buffer para el manejo de los datos
				PrintWriter writer = new PrintWriter(bw);
				writer.print(j);
				writer.print(";");
				writer.print("Metrica");
				writer.print(";");
				writer.print(min);
				writer.print(";");
				writer.print(max);
				writer.print(";");
				writer.print(media);
				writer.println();
				writer.close();
			} 
		}
		catch(java.io.IOException ioex) {
		}
	}


	public void loadRepositoryFile(String file, int idFile, ArrayList<State> repositoryFile) throws IOException{
		Load load = new Load();
		load.loadFile(pathFile + file  + idFile + ".txt");
		repositoryFile.addAll(load.createRepository());
	}
	public void loadRepositoryFileAux(String file, ArrayList<Auxiliar> repositoryFile) throws IOException{
		Load load = new Load();
		load.loadFile(pathFile + file + ".txt");
		repositoryFile.addAll(load.testing());
	}
	public void getRealFrentePareto (ArrayList<State> frentePareto) throws IOException{
		this.problem = new Problem();
		this.problem.setTypeProblem(ProblemType.Maximizar);
		Strategy.getStrategy().setProblem(this.problem);

		int i = 0;
		boolean found;

		while(i < frentePareto.size())
		{System.out.println("size:"+ frentePareto.size());
		System.out.println(i);
		State actSolution = frentePareto.get(i);
		found = ListDominance(actSolution, frentePareto);

		if(!found)
			i = 0;
		else 
			i++;
		}

		SaveTxt.writeFileTxt(pathFile + "frentepareto//", "fp_0.txt", frentePareto);
	}	
	public void compareWithFPtrue(ArrayList<State> FPtrue, ArrayList<State> FPcurrent) throws IllegalArgumentException, SecurityException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BiffException, IOException{
		MetricasMultiobjetivo metricsMO = new MetricasMultiobjetivo();

		MetricsMO.getMetricsMO().getListTasaError().add(metricsMO.TasaError(FPcurrent,FPtrue));
		MetricsMO.getMetricsMO().getListDistanciaGeneracional().add(metricsMO.DistanciaGeneracional(FPcurrent,FPtrue));
		MetricsMO.getMetricsMO().getListDispersion().add(metricsMO.Dispersion(FPcurrent));
	}
	public void getGlobalsMetrics(){
		//		MetricasMultiobjetivo metricsMO = new MetricasMultiobjetivo();

		/*double maxMetric = metricsMO.CalcularMax(MetricsMO.getMetricsMO().getListTasaError());
		double minMetric = metricsMO.CalcularMin(MetricsMO.getMetricsMO().getListTasaError());
		double avgMetric = metricsMO.CalcularMedia(MetricsMO.getMetricsMO().getListTasaError());
		double medianMetric = metricsMO.CalcularMax(MetricsMO.getMetricsMO().getListTasaError());
		SaveTxt.writeFileMetrics(pathFile + "metrics//", "Tasa_Error", maxMetric, minMetric, avgMetric, medianMetric);*/

		SaveTxt.writeFileListMetrics(pathFile + "metrics//", "Tasa_Error.txt", MetricsMO.getMetricsMO().getListTasaError());
		SaveTxt.writeFileListMetrics(pathFile + "metrics//", "Distancia_Generacional.txt", MetricsMO.getMetricsMO().getListDistanciaGeneracional());
		SaveTxt.writeFileListMetrics(pathFile + "metrics//", "Dispersion.txt", MetricsMO.getMetricsMO().getListDispersion());

		/*maxMetric = metricsMO.CalcularMax(MetricsMO.getMetricsMO().getListDistanciaGeneracional());
		minMetric = metricsMO.CalcularMin(MetricsMO.getMetricsMO().getListDistanciaGeneracional());
		avgMetric = metricsMO.CalcularMedia(MetricsMO.getMetricsMO().getListDistanciaGeneracional());
		medianMetric = metricsMO.CalcularMax(MetricsMO.getMetricsMO().getListDistanciaGeneracional());
		SaveTxt.writeFileMetrics(pathFile + "metrics//", "Distancia_Generacional", maxMetric, minMetric, avgMetric, medianMetric);

		maxMetric = metricsMO.CalcularMax(MetricsMO.getMetricsMO().getListDispersion());
		minMetric = metricsMO.CalcularMin(MetricsMO.getMetricsMO().getListDispersion());
		avgMetric = metricsMO.CalcularMedia(MetricsMO.getMetricsMO().getListDispersion());
		medianMetric = metricsMO.CalcularMax(MetricsMO.getMetricsMO().getListDispersion());
		SaveTxt.writeFileMetrics(pathFile + "metrics//", "Dispersion", maxMetric, minMetric, avgMetric, medianMetric);*/
	}
}
