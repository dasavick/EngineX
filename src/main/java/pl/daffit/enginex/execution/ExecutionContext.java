package pl.daffit.enginex.execution;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {

    private CommandSender sender;
    private Map<String, Object> variables = new HashMap<>();

    public CommandSender getSender() {
        return this.sender;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
