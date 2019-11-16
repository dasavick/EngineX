package pl.daffit.enginex.execution;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ExecutionContext {

    private CommandSender sender;
    private Map<String, Object> variables = new HashMap<>();
}
