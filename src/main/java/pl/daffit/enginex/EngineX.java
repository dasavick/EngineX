package pl.daffit.enginex;

import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import pl.daffit.enginex.execution.ExecutionContext;
import pl.daffit.enginex.registry.EngineEffectRegistry;
import pl.daffit.enginex.registry.effect.*;
import pl.daffit.enginex.runtime.EngineRuntimeRegistry;

import java.io.IOException;
import java.util.logging.Logger;

public class EngineX extends JavaPlugin {

    private static boolean debugEnabled = true;
    private static Logger logger;
    private static EngineX instance;

    public static Logger logger() {
        return logger;
    }

    public static EngineX getInstance() {
        return instance;
    }

    public static boolean isDebugEnabled() {
        return debugEnabled;
    }

    @Override
    public void onEnable() {
        instance = this;
        logger = this.getLogger();
        EngineEffectRegistry.register("text", TextEffect.class);
        EngineEffectRegistry.register("raw_text", RawTextEffect.class);
        EngineEffectRegistry.register("set", SetEffect.class);
        EngineEffectRegistry.register("increase", IncreaseEffect.class);
        EngineEffectRegistry.register("stop", StopEffect.class);
        try {
            NgxConfig conf = NgxConfig.read(this.getDataFolder().getPath() + "/test.conf");
            NgxDumper dumper = new NgxDumper(conf);
            dumper.dump(System.out);
            EnginePlugin enginePlugin = EnginePluginFactory.fromConfig(conf);
            System.out.println(enginePlugin);
            EngineRuntimeRegistry.register(enginePlugin);
        } catch (IOException | EngineException e) {
            e.printStackTrace();
        }
    }

    public static void debug(String message) {
        if (!isDebugEnabled()) {
            return;
        }
        logger.info(message);
    }

    public static ExecutionContext createContext() {
        ExecutionContext executionContext = new ExecutionContext();
        executionContext.setVariable("server", Bukkit.getServer(), Server.class);
        return executionContext;
    }
}
