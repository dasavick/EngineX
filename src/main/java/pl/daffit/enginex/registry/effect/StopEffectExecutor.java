package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.EngineX;
import pl.daffit.enginex.effect.EngineEffectExecutor;
import pl.daffit.enginex.execution.ExecutionContext;

public class StopEffectExecutor implements EngineEffectExecutor {

    /**
     * @param varargs none
     * @return always true
     */
    @Override
    public boolean execute(ExecutionContext context, Object... varargs) throws EngineException {
        EngineX.debug("Stopped execution.");
        context.setExecution(false);
        return true;
    }
}
