package pl.daffit.enginex.command;

import java.util.Arrays;
import java.util.Map;

public class EngineCommand {

    private final String name;
    private final String[] aliases;
    private final String permission;
    private final String description;
    private final Map<String, EngineCommandAction> actions;

    public EngineCommand(String name, String[] aliases, String permission, String description, Map<String, EngineCommandAction> actions) {
        this.name = name;
        this.aliases = aliases;
        this.permission = permission;
        this.description = description;
        this.actions = actions;
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

    public Map<String, EngineCommandAction> getActions() {
        return this.actions;
    }

    public String toString() {
        return "EngineCommand(name=" + this.getName() + ", aliases=" + Arrays.deepToString(this.getAliases()) + ", permission=" + this.getPermission() + ", description=" + this.getDescription() + ", actions=" + this.getActions() + ")";
    }
}
