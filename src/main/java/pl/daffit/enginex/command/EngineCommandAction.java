package pl.daffit.enginex.command;

import pl.daffit.enginex.effect.EngineEffect;

import java.util.Arrays;
import java.util.List;

public class EngineCommandAction {

    private final String name;
    private final String[] aliases;
    private final String permission;
    private final String description;
    private final List<EngineEffect> effects;

    public EngineCommandAction(String name, String[] aliases, String permission, String description, List<EngineEffect> effects) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
        this.effects = effects;
    }

    public String getName() {
        return this.name;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getDescription() {
        return this.description;
    }

    public List<EngineEffect> getEffects() {
        return this.effects;
    }

    public String toString() {
        return "EngineCommandAction(name=" + this.getName() + ", aliases=" + Arrays.deepToString(this.getAliases()) + ", permission=" + this.getPermission() + ", description=" + this.getDescription() + ", effects=" + this.getEffects() + ")";
    }
}