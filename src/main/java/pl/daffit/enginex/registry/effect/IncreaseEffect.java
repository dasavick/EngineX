package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.effect.EngineEffectExecutor;

public class IncreaseEffect extends EngineEffect {

    @Override
    public EngineEffectExecutor getExecutor() {
        return new IncreaseEffectExecutor();
    }

    @Override
    public Class[] getVarargMap() {
        return new Class[]{String.class, String.class};
    }

    @Override
    public String toString() {
        return "IncreaseEffect(variable=" + this.getVarargs()[0] + ", increment=" + this.getVarargMap()[1] + ")";
    }
}
