package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.effect.EngineEffectExecutor;

public class RawTextEffect extends EngineEffect {

    @Override
    public EngineEffectExecutor getExecutor() {
        return new RawTextEffectExecutor();
    }

    @Override
    public Class[] getVarargMap() {
        return new Class[]{String.class};
    }

    @Override
    public String toString() {
        return "RawTextEffect(message=" + this.getVarargs()[0] + ")";
    }
}
