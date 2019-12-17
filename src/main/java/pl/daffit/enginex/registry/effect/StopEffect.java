package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.effect.EngineEffectExecutor;

public class StopEffect extends EngineEffect {

    @Override
    public EngineEffectExecutor getExecutor() {
        return new StopEffectExecutor();
    }

    @Override
    public Class[] getVarargMap() {
        return new Class[]{};
    }

    @Override
    public String toString() {
        return "StopEffect()";
    }
}
