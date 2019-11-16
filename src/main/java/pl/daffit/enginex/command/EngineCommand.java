package pl.daffit.enginex.command;

import lombok.Data;

import java.util.Map;

@Data
public class EngineCommand {

    private final String name;
    private final String[] aliases;
    private final String permission;
    private final String description;
    private final Map<String, EngineCommandAction> actions;
}
