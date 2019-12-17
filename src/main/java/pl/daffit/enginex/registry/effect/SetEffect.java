package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.effect.EngineEffectExecutor;

public class SetEffect extends EngineEffect {

    @Override
    public EngineEffectExecutor getExecutor() {
        return new SetEffectExecutor();
    }

    @Override
    public Class[] getVarargMap() {
        return new Class[]{String.class, Object.class};
    }

    @Override
    public String toString() {
        return "SetEffect(variable=" + this.getVarargs()[0] + ", value=" + this.getVarargMap()[1] + ")";
    }
}
