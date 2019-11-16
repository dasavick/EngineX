package pl.daffit.enginex.registry.effect;

import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.effect.EngineEffectExecutor;

public class TextEffect extends EngineEffect {

    @Override
    public EngineEffectExecutor getExecutor() {
        return new TextEffectExecutor();
    }

    @Override
    public Class[] getVarargMap() {
        return new Class[]{String.class};
    }

    @Override
    public String toString() {
        return "TextEffect(message=" + this.getVarargs()[0] + ")";
    }
}
