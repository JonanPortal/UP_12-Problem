package util;

import java.util.ArrayList;

import problem.definition.State;

public class Auxiliar {
	private int run;
	private ArrayList<State> listFCurrentTotal = new ArrayList<State>();
	
	public Auxiliar(int run, ArrayList<State> listFCurrentTotal) {
		super();
		this.run = run;
		this.listFCurrentTotal = listFCurrentTotal;
	}
	
	public Auxiliar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getRun() {
		return run;
	}
	public void setRun(int run) {
		this.run = run;
	}
	public ArrayList<State> getListFCurrentTotal() {
		return listFCurrentTotal;
	}
	public void setListFCurrentTotal(ArrayList<State> listFCurrentTotal) {
		this.listFCurrentTotal = listFCurrentTotal;
	}
}
