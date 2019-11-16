package pl.daffit.enginex;

import pl.daffit.enginex.command.EngineCommand;

import java.util.List;

public class EnginePlugin {

    private final EnginePluginInfo info;
    private final List<EngineCommand> commands;

    public EnginePlugin(EnginePluginInfo info, List<EngineCommand> commands) {
        this.info = info;
        this.commands = commands;
    }

    public EnginePluginInfo getInfo() {
        return this.info;
    }

    public List<EngineCommand> getCommands() {
        return this.commands;
    }

    public String toString() {
        return "EnginePlugin(info=" + this.getInfo() + ", commands=" + this.getCommands() + ")";
    }
}