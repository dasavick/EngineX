package pl.daffit.enginex.registry.effect;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.effect.EngineEffectExecutor;
import pl.daffit.enginex.execution.ExecutionContext;

public class RawTextEffectExecutor implements EngineEffectExecutor {

    /**
     * @param varargs 0: message
     * @return delivery state
     */
    @Override
    public boolean execute(ExecutionContext context, Object... varargs) throws EngineException {

        CommandSender sender = context.getSender();
        if (sender == null) {
            throw new EngineException("RawTextEffect requires sender in the context");
        }

        if ((sender instanceof Player) && !((Player) sender).isOnline()) {
            return false;
        }

        sender.sendMessage(String.valueOf(varargs[0]));
        return true;
    }
}
