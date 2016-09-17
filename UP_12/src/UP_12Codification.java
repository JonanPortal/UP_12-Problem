import problem.definition.Codification;
import problem.definition.State;

public class UP_12Codification extends Codification {

	@Override
	public int getAleatoryKey() {
		return (int)(Math.random() * (double)(UP_12.countVariables));
	}

	@Override
	public Object getVariableAleatoryValue(int variable){
		// TODO Auto-generated method stub
		return (int)(Math.random() * (double)(2));
	}

	@Override
	public int getVariableCount() {
		// TODO Auto-generated method stub
		return UP_12.countVariables;
	}

	@Override
	public boolean validState(State state) {
		boolean encontrar = false;
		int i = 0;
		while (encontrar==false && i < state.getCode().size()) {
			if((Integer)state.getCode().get(i) != 0 || (Integer)state.getCode().get(i) != 1)
				encontrar = true;
			else i++;
		}
		return encontrar;
	}

}
