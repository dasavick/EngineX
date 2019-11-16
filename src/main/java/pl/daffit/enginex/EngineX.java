package pl.daffit.enginex;

import com.github.odiszapc.nginxparser.NgxConfig;
import com.github.odiszapc.nginxparser.NgxDumper;
import org.bukkit.plugin.java.JavaPlugin;
import pl.daffit.enginex.registry.EngineEffectRegistry;
import pl.daffit.enginex.registry.effect.RawTextEffect;
import pl.daffit.enginex.registry.effect.TextEffect;
import pl.daffit.enginex.runtime.EngineRuntimeRegistry;

import java.io.IOException;
import java.util.logging.Logger;

public class EngineX extends JavaPlugin {

    private static Logger logger;

    public static Logger logger() {
        return logger;
    }

    @Override
    public void onEnable() {
        logger = this.getLogger();
        EngineEffectRegistry.register("text", TextEffect.class);
        EngineEffectRegistry.register("raw_text", RawTextEffect.class);
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
}
