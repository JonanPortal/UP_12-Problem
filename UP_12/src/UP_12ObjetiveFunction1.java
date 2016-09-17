

import problem.definition.ObjetiveFunction;
import problem.definition.State;

public class UP_12ObjetiveFunction1 extends ObjetiveFunction {

	@Override
	public Double Evaluation(State state) {
		double cant = 0;
		for (int i = 0; i < state.getCode().size(); i++) {
			if(state.getCode().get(i).equals(1))
				cant++;
		}
		return cant;
	}
}
