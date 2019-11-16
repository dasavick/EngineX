package pl.daffit.enginex.command;

import lombok.Data;
import pl.daffit.enginex.effect.EngineEffect;

import java.util.List;

@Data
public class EngineCommandAction {

    private final String name;
    private final String[] aliases;
    private final String permission;
    private final String description;
    private final List<EngineEffect> effects;
}