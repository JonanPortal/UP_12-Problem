

import java.util.ArrayList;
import java.util.List;

import metaheurictics.strategy.Strategy;

import problem.definition.Operator;
import problem.definition.State;

public class UP_12MutationOperator extends Operator {

	@Override
	public List<State> generatedNewState(State stateCurrent, Integer operatornumber) {
		List<State> listNeigborhood = new ArrayList<State>();
		for (int i = 0; i < operatornumber; i++){
			int key = Strategy.getStrategy().getProblem().getCodification().getAleatoryKey();
			int candidate = (Integer) Strategy.getStrategy().getProblem().getCodification().getVariableAleatoryValue(key);
			State state = new State();
			state.setCode(new ArrayList<Object>(stateCurrent.getCode()));
			state.getCode().set(key, candidate);
			listNeigborhood.add(state);
		}
		return listNeigborhood;
	}

	@Override
	public List<State> generateRandomState(Integer operatornumber) {
		List<State> list = new ArrayList<State>();
		for (int i = 0; i < operatornumber; i++) {
			State stateinitial = new State();
			ArrayList<Object> code = stateinitial.getCode();
			for (int j = 0; j < UP_12.countVariables; j++) {
				int candidate = (Integer) Strategy.getStrategy().getProblem().getCodification().getVariableAleatoryValue(j);
				code.add(j, candidate);
			} 
			stateinitial.setCode(code);
			list.add(stateinitial);
		}
		return list;
	}

}
