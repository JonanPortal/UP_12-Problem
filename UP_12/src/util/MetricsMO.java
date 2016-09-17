package util;

import java.util.ArrayList;

public class MetricsMO {

	private ArrayList<Double> listTasaError;
	private ArrayList<Double> listDistanciaGeneracional;
	private ArrayList<Double> listDispersion;
	private static MetricsMO instanceMetricsMO = null;

	public MetricsMO() {
		super();
		listTasaError = new ArrayList<Double>();
		listDistanciaGeneracional = new ArrayList<Double>();
		listDispersion = new ArrayList<Double>();
		// TODO Auto-generated constructor stub
	}
	
   public static MetricsMO getMetricsMO(){
		
		if(instanceMetricsMO == null)
			instanceMetricsMO = new MetricsMO();
		return instanceMetricsMO;
	}

	public ArrayList<Double> getListTasaError() {
		return listTasaError;
	}

	public void setListTasaError(ArrayList<Double> listTasaError) {
		this.listTasaError = listTasaError;
	}

	public ArrayList<Double> getListDistanciaGeneracional() {
		return listDistanciaGeneracional;
	}

	public void setListDistanciaGeneracional(
			ArrayList<Double> listDistanciaGeneracional) {
		this.listDistanciaGeneracional = listDistanciaGeneracional;
	}

	public ArrayList<Double> getListDispersion() {
		return listDispersion;
	}

	public void setListDispersion(ArrayList<Double> listDispersion) {
		this.listDispersion = listDispersion;
	}
}
