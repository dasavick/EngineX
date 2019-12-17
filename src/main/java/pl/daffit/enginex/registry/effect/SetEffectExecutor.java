package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.effect.EngineEffectExecutor;
import pl.daffit.enginex.execution.ExecutionContext;
import pl.daffit.enginex.variable.VariableUtils;

public class SetEffectExecutor implements EngineEffectExecutor {

    /**
     * @param varargs 0: variable
     *                1: value
     * @return always true
     */
    @Override
    public boolean execute(ExecutionContext context, Object... varargs) throws EngineException {

        String key = ((String) varargs[0]);
        Object value = varargs[1];
        boolean overwrite = false;
        VariableUtils.validateVariableName(key);

        context.setVariable(key, value);
        return overwrite;
    }
}
