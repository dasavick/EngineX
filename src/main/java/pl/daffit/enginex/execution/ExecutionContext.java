package pl.daffit.enginex.execution;

import org.bukkit.command.CommandSender;
import pl.daffit.enginex.EngineX;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {

    private CommandSender sender;
    private Map<String, Object> variables = new HashMap<>();
    private Map<String, Class> variableTypes = new HashMap<>();
    private boolean execution = true;

    public CommandSender getSender() {
        return this.sender;
    }

    public Object getVariable(String key) {
        return this.variables.get(key);
    }

    public void setSender(CommandSender sender) {
        this.sender = sender;
    }

    public Map<String, Object> getVariables() {
        return this.variables;
    }

    public void setVariable(String key, Object value) {
        EngineX.debug(key + " = " + value);
        this.variables.put(key, value);
    }

    public void setVariable(String key, Object value, Class type) {
        this.setVariable(key, value);
        this.variableTypes.put(key, type);
    }

    public Class getVariableType(String key) {
        return this.variableTypes.getOrDefault(key, Object.class);
    }

    public boolean isExecution() {
        return this.execution;
    }

    public void setExecution(boolean execution) {
        this.execution = execution;
    }
}
