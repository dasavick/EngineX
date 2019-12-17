package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.EngineX;
import pl.daffit.enginex.effect.EngineEffectExecutor;
import pl.daffit.enginex.execution.ExecutionContext;
import pl.daffit.enginex.variable.VariableUtils;

public class IncreaseEffectExecutor implements EngineEffectExecutor {

    /**
     * @param varargs 0: variable
     *                1: increase
     * @return always true
     */
    @Override
    public boolean execute(ExecutionContext context, Object... varargs) throws EngineException {

        String key = ((String) varargs[0]);
        String increase = String.valueOf(varargs[1]);
        VariableUtils.validateVariableName(key);
        VariableUtils.validateVariableExistence(context, key);

        double variable = VariableUtils.parseAsNumber(String.valueOf(context.getVariable(key)));
        double numericIncrease = VariableUtils.parseAsNumber(increase);

        double newValue = variable + numericIncrease;
        String newValueText = VariableUtils.numericToString(newValue);

        EngineX.debug(key + " += " + numericIncrease);
        context.setVariable(key, newValueText);

        return true;
    }
}
