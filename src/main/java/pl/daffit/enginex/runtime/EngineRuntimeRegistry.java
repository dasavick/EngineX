package pl.daffit.enginex.runtime;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.EnginePlugin;
import pl.daffit.enginex.EngineX;
import pl.daffit.enginex.command.EngineCommand;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class EngineRuntimeRegistry {

    private static final List<EnginePlugin> REGISTERED_PLUGINS = new ArrayList<>();
    private static final Map<EnginePlugin, Command> PLUGINS_COMMANDS = new ConcurrentHashMap<>();

    public static void register(EnginePlugin plugin) throws EngineException {
        EngineX.logger().info("Registering" + plugin.getInfo());
        List<EngineCommand> commands = plugin.getCommands();
        EngineX.logger().info("Found " + commands.size() + " commands.");
        for (EngineCommand command : commands) {
            registerCommand(command);
        }
    }

    public static void registerCommand(EngineCommand command) throws EngineException {
        try {
            Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register(command.getName(), new EngineCommandRunner(command));
        } catch (IllegalAccessException | NoSuchFieldException exception) {
            throw new EngineException("Failed to register command " + command.getName(), exception);
        }
    }

    public static String createCommandUsage(EngineCommand command) {

        StringBuilder usage = new StringBuilder().append("/").append(command.getName());

        if (command.getActions().size() > 1) {
            List<String> nonEmptyActions = command.getActions().keySet().stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList());
            usage.append(" <").append(StringUtils.join(nonEmptyActions, " | ")).append(">");
        }

        return usage.toString();
    }
}
