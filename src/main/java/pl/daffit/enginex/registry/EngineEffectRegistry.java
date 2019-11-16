package pl.daffit.enginex.registry;

import pl.daffit.enginex.EngineX;
import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.EngineException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class EngineEffectRegistry {

    private static final Map<String, Class<EngineEffect>> EFFECT_MAP = new ConcurrentHashMap<>();

    public static void register(String name, Class effect) {

        if (EFFECT_MAP.containsKey(name)) {
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            EngineX.logger().warning(name + " effect was overridden by " + stack[stack.length - 1]);
        }

        EFFECT_MAP.put(name, effect);
    }

    public static EngineEffect getEffect(String name) throws EngineException {
        try {
            return EFFECT_MAP.get(name).newInstance();
        } catch (InstantiationException | IllegalAccessException exception) {
            throw new EngineException("Failed to create new effect instance for " + name, exception);
        }
    }

    public static boolean isEffect(String name) {
        return EFFECT_MAP.containsKey(name);
    }
}
