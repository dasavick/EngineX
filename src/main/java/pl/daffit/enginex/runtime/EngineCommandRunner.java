package pl.daffit.enginex.runtime;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import pl.daffit.enginex.EngineX;
import pl.daffit.enginex.command.EngineCommand;
import pl.daffit.enginex.command.EngineCommandAction;
import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.execution.ExecutionContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EngineCommandRunner extends BukkitCommand {

    private final EngineCommand command;

    protected EngineCommandRunner(EngineCommand engineCommand) {
        super(engineCommand.getName());
        this.command = engineCommand;
        this.usageMessage = EngineRuntimeRegistry.createCommandUsage(engineCommand);
        if (!engineCommand.getDescription().isEmpty()) {
            this.description = engineCommand.getDescription();
        }
        if (!engineCommand.getPermission().isEmpty()) {
            this.setPermission(engineCommand.getPermission());
        }
        if (engineCommand.getAliases().length == 0) {
            this.setAliases(Collections.emptyList());
        } else {
            this.setAliases(Arrays.asList(engineCommand.getAliases()));
        }
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {

        ExecutionContext context = EngineX.createContext();
        context.setSender(sender);
        context.setVariable("sender", sender, CommandSender.class);

        try {
            EngineCommandAction action;

            // default action
            if (args.length == 0) {
                action = this.command.getActions().get("");
            } else {
                String argFull = StringUtils.join(args, ' ');
                action = this.command.getActions().get(argFull);
            }

            // no valid action found, display usage
            if (action == null) {
                sender.sendMessage(this.usageMessage);
                return true;
            }

            // run action
            List<EngineEffect> effects = action.getEffects();
            for (EngineEffect effect : effects) {
                if (!context.isExecution()) {
                    break;
                }
                effect.getExecutor().execute(context, effect.getVarargs());
            }

        } catch (Throwable exception) {
            sender.sendMessage(ChatColor.RED + "Internal error occured while dispatching command: " + exception.getMessage());
            EngineX.logger().warning("Error while dispatching command /" + label + " " + Arrays.toString(args));
            exception.printStackTrace();
            return true;
        }

        return true;
    }
}
