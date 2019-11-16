package pl.daffit.enginex.registry.effect;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.effect.EngineEffectExecutor;
import pl.daffit.enginex.execution.ExecutionContext;

public class TextEffectExecutor implements EngineEffectExecutor {

    /**
     * @param varargs 0: message
     * @return delivery state
     */
    @Override
    public boolean execute(ExecutionContext context, Object... varargs) throws EngineException {

        CommandSender sender = context.getSender();
        if (sender == null) {
            throw new EngineException("TextEffect requires sender in the context");
        }

        if ((sender instanceof Player) && !((Player) sender).isOnline()) {
            return false;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(varargs[0])));
        return true;
    }
}
