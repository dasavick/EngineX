package pl.daffit.enginex.effect;

import pl.daffit.enginex.execution.ExecutionContext;
import pl.daffit.enginex.EngineException;

public interface EngineEffectExecutor {

    boolean execute(ExecutionContext context, Object... varargs) throws EngineException;
}
