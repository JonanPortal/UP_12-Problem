import java.util.ArrayList;

import problem.definition.State;
import util.Auxiliar;

public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Tester tester = new Tester();
		int option=0;
		//0-Ejecutar, 1- Crear FPtrue 2-Metricas MO

		switch(option)
		{
		case 0:
		{
			tester.ECMO();
			/*tester.EC();
			tester.BT();
			tester.RS();
			tester.AG();*/
			//	tester.ECMOD(); 
			//tester.ECMOR();
			//	tester.NSGAII();
			//	tester.BTMO();
			//	tester.RSMO();
			//	tester.UMOSA();
			//tester.MOEADDE();
			break;
		}

		case 1:
		{
			int cantFile = 5;  
			ArrayList<State> FPtrue = new ArrayList<State>();
			String file = "repository//result_"; 
			for(int i = 0; i <= cantFile; i++)
				tester.loadRepositoryFile(file, i, FPtrue);

			tester.getRealFrentePareto(FPtrue);

			break;
		}

		case 2:
		{
			String file = "frentepareto//fp_";
			ArrayList<State> FPtrue = new ArrayList<State>();
			tester.loadRepositoryFile(file, 1 , FPtrue);

			int cantFPcurrent = 20;  
			file = "file//ECMO_";
 
			for(int i = 0; i < cantFPcurrent; i++)
			{
				ArrayList<State> FPcurrent = new ArrayList<State>();
				tester.loadRepositoryFile(file, i, FPcurrent);
				tester.compareWithFPtrue(FPtrue, FPcurrent);
			}

			tester.getGlobalsMetrics();
			break;
		}
		case 3:
		{

			String file = "frentepareto//fp_";
			ArrayList<Auxiliar>aux = new ArrayList<Auxiliar>();
			ArrayList<State> FPtrue = new ArrayList<State>();
			tester.loadRepositoryFile(file, 0, FPtrue);

			file = "file//MOEADDE";
			tester.loadRepositoryFileAux(file, aux);
			for (int i = 0; i < aux.size(); i++) {
				System.out.println(i);
				if (aux.get(i).getRun() == i) {
					ArrayList<State> FPcurrent = new ArrayList<State>();
					FPcurrent.addAll(aux.get(i).getListFCurrentTotal());
					tester.compareWithFPtrue(FPtrue, FPcurrent);
				}
			} 
			tester.getGlobalsMetrics();
			break;
		}
		}
	}
}
