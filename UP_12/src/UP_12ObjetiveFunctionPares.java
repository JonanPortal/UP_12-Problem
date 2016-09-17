

import problem.definition.ObjetiveFunction;
import problem.definition.State;

public class UP_12ObjetiveFunctionPares extends ObjetiveFunction {

	@Override
	public Double Evaluation(State state) {
		double cant = 0;
		for (int i = 0; i < state.getCode().size()-1; i++) {
			if(state.getCode().get(i)!= state.getCode().get(i +1))
				cant++;
		}
		return cant;
	}
}
