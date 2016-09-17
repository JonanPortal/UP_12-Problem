package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import problem.definition.State;

public class SaveTxt {
	
	public static void writeFileTxt(String pathFile, String fichero, List<State> listRefPoblacFinal) {
		try {
			
			FileWriter fw = new FileWriter(pathFile + fichero);// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			
			title.print("Tamaño");
			title.print(";");
			title.print("Estado");
			title.print(";");
			title.print("Cantidad de FO");
			title.print(";");
			title.print("FO");
			title.println();
			title.close();
			
			for (int j = 0; j < listRefPoblacFinal.size(); j++) {
				
				bw = new BufferedWriter(new FileWriter(pathFile + fichero, true));// buffer para el manejo de los datos
				PrintWriter writer = new PrintWriter(bw);
				
				writer.print(listRefPoblacFinal.get(j).getCode().size());
				writer.print(";");
				writer.print(listRefPoblacFinal.get(j).getCode().toString());
				writer.print(";");
				writer.print(listRefPoblacFinal.get(j).getEvaluation().size());
				writer.print(";");
				writer.print(listRefPoblacFinal.get(j).getEvaluation());
				writer.println();
				writer.close();
			} 
		}
		catch(java.io.IOException ioex) {
		}
	}
	public static void writeFileListMetrics(String pathFile, String fichero, List<Double> listMetrics) {
		try {
			FileWriter fw = new FileWriter(pathFile + fichero);// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			title.print("Metrica");
			title.print(";");
			title.print("ListaMetricas");
			title.print(";");
			title.println();
			title.close();
			for (int j = 0; j < listMetrics.size(); j++) {
				bw = new BufferedWriter(new FileWriter(pathFile + fichero, true));// buffer para el manejo de los datos
				PrintWriter writer = new PrintWriter(bw);
				writer.print(fichero);
				writer.print(";");
				writer.print(listMetrics.get(j));
				writer.print(";");
				writer.println();
				writer.close();
			} 
		}
		catch(java.io.IOException ioex) {
		}
	}

	public static void writeFileTxtAverage(String pathFile, String fichero, List<Double> aveBest) {
		try {
			
			FileWriter fw = new FileWriter(pathFile + fichero);// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			
			title.print("Run");
			title.print(";");
			title.print("Promedio");
			title.println();
			title.close();
			
			for (int j = 0; j < aveBest.size(); j++) {
				
				bw = new BufferedWriter(new FileWriter(pathFile + fichero, true));// buffer para el manejo de los datos
				PrintWriter writer = new PrintWriter(bw);
				
				writer.print(j);
				writer.print(";");
				writer.print(aveBest.get(j));
				writer.print(";");
				writer.println();
				writer.close();
			} 
		}
		catch(java.io.IOException ioex) {
		}
	}
	public static void writeFileTxtMono(String pathFile, String fichero, List<State> lisBest) {
		try {
			
			FileWriter fw = new FileWriter(pathFile + fichero);// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			
			title.print("Tamaño");
			title.print(";");
			title.print("Estado");
			title.print(";");
			title.print("FO");
			title.println();
			title.close();
			
			for (int j = 0; j < lisBest.size(); j++) {
				
				bw = new BufferedWriter(new FileWriter(pathFile + fichero, true));// buffer para el manejo de los datos
				PrintWriter writer = new PrintWriter(bw);
				
				writer.print(lisBest.get(j).getCode().size());
				writer.print(";");
				writer.print(lisBest.get(j).getCode().toString());
				writer.print(";");
				writer.print(lisBest.get(j).getEvaluation().get(0));
				writer.println();
				writer.close();
			} 
		}
		catch(java.io.IOException ioex) {
		}
	}
	public static void writeFileMetrics(String pathMetric, String fichero, double maxMetric, double minMetric, double avgMetric, double medianMetric) {
		try {
			
			FileWriter fw = new FileWriter(pathMetric + fichero + ".txt");// Objeto para que establece origen de los datos
			BufferedWriter bw = new BufferedWriter(fw);// buffer para el manejo de los datos
			PrintWriter title = new PrintWriter(bw);
			
			title.print("Metrica");
			title.print(";");
			title.print("Max");
			title.print(";");
			title.print("Min");
			title.print(";");
			title.print("Promedio");
			title.print(";");
			title.print("Mediana");
			title.println();
			title.close();
			
		
			bw = new BufferedWriter(new FileWriter(pathMetric + fichero + ".txt", true));// buffer para el manejo de los datos
			PrintWriter writer = new PrintWriter(bw);
				
			writer.print(fichero);
			writer.print(";");
			writer.print(maxMetric);
			writer.print(";");
			writer.print(minMetric);
			writer.print(";");
			writer.print(avgMetric);
			writer.print(";");
			writer.print(medianMetric);
			writer.println();
			writer.close();

		}
		catch(java.io.IOException ioex) {
		}
	}
}
