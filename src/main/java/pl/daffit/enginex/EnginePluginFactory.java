package pl.daffit.enginex;

import com.github.odiszapc.nginxparser.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import pl.daffit.enginex.command.EngineCommand;
import pl.daffit.enginex.command.EngineCommandAction;
import pl.daffit.enginex.effect.EngineEffect;
import pl.daffit.enginex.registry.EngineEffectRegistry;

import java.util.*;

public final class EnginePluginFactory {

    public static EnginePlugin fromConfig(NgxConfig config) throws EngineException {

        NgxBlock plugin = config.findBlock("plugin");
        if (plugin == null) {
            throw new EngineException("Missing 'plugin' block.");
        }

        NgxParam name = plugin.findParam("name");
        if (name == null) {
            throw new EngineException("Missing 'name' in 'plugin' block.");
        }

        NgxParam version = plugin.findParam("version");
        if (version == null) {
            throw new EngineException("Missing 'version' in 'plugin' block.");
        }

        NgxParam author = plugin.findParam("author");
        if (author == null) {
            throw new EngineException("Missing 'author' in 'plugin' block.");
        }

        String pluginName = StringUtils.strip(name.getValue(), "\"");
        String pluginVersion = StringUtils.strip(version.getValue(), "\"");
        String[] pluginAuthor = author.getValues().stream().map(e -> StringUtils.strip(e, "\"")).toArray(String[]::new);

        try {
            Validate.notEmpty(pluginName, "plugin.name cannot be empty");
            Validate.notEmpty(pluginVersion, "plugin.version cannot be empty");
            Validate.notEmpty(pluginAuthor, "plugin.author cannot be empty");
        } catch (IllegalArgumentException exception) {
            throw new EngineException(exception.getMessage());
        }

        // basic info
        EnginePluginInfo enginePluginInfo = new EnginePluginInfo(pluginName, pluginVersion, pluginAuthor);
        EngineX.logger().info("Loading " + pluginName + " " + pluginVersion + " by " + StringUtils.join(pluginAuthor, ", "));

        // commands
        List<EngineCommand> engineCommands = parseCommands(config);

        // loaded data
        return new EnginePlugin(enginePluginInfo, engineCommands);
    }

    public static List<EngineCommand> parseCommands(NgxConfig config) throws EngineException {

        List<EngineCommand> commands = new ArrayList<>();
        List<NgxEntry> commandBlocks = config.findAll(NgxConfig.BLOCK, "command");
        EngineX.logger().info("Trying to load " + commandBlocks.size() + " commands: " + commandBlocks);

        for (NgxEntry entry : commandBlocks) {

            NgxBlock commandBlock = (NgxBlock) entry;
            Collection<NgxToken> tokens = commandBlock.getTokens();
            if (tokens.size() < 2) {
                throw new EngineException("Anonymous commands are not allowed: " + commandBlock);
            }

            List<NgxToken> commandNames = new ArrayList<>(tokens).subList(1, tokens.size());
            NgxToken mainCommandName = commandNames.get(0);
            List<NgxToken> commandAliasesTokens = commandNames.subList(1, commandNames.size());

            if (mainCommandName == null) {
                throw new EngineException("No valid command name: " + commandBlock);
            }

            NgxParam permission = commandBlock.findParam("permission");
            NgxParam description = commandBlock.findParam("description");

            String commandName = StringUtils.strip(mainCommandName.getToken(), "\"");
            String[] commandAliases = commandAliasesTokens.stream().map(e -> StringUtils.strip(e.getToken(), "\"")).toArray(String[]::new);
            String commandPermission = (permission == null) ? "" : StringUtils.strip(permission.getValue(), "\"");
            String commandDescription = (description == null) ? "" : StringUtils.strip(description.getValue(), "\"");

            try {
                Validate.notEmpty(commandName, "command.name cannot be empty: " + commandBlock);
            } catch (IllegalArgumentException exception) {
                throw new EngineException(exception.getMessage());
            }

            Map<String, EngineCommandAction> commandActions = parseCommandActions(commandBlock);
            commands.add(new EngineCommand(commandName, commandAliases, commandPermission, commandDescription, commandActions));
        }

        return commands;
    }

    public static Map<String, EngineCommandAction> parseCommandActions(NgxBlock commandBlock) throws EngineException {

        Map<String, EngineCommandAction> actions = new HashMap<>();
        List<NgxEntry> actionBlocks = commandBlock.findAll(NgxConfig.BLOCK, "action");
        EngineX.logger().info("Trying to load " + actionBlocks.size() + " actions: " + actionBlocks);

        for (NgxEntry entry : actionBlocks) {

            NgxBlock actionBlock = (NgxBlock) entry;
            Collection<NgxToken> tokens = actionBlock.getTokens();

            // main, fallback action
            if (tokens.size() == 1) {

                NgxParam permission = actionBlock.findParam("permission");
                if (permission != null) {
                    throw new EngineException("Option 'permission' is not allowed in default action as it inherits from the parent.");
                }

                NgxParam description = actionBlock.findParam("description");
                if (description != null) {
                    throw new EngineException("Option 'description' is not allowed in default action as it inherits from the parent.");
                }

                List<EngineEffect> effects = parseCommandActionEffects(actionBlock);
                actions.put("", new EngineCommandAction("", new String[]{}, "", "", effects));
                EngineX.logger().info("Loaded default action.");
                continue;
            }

            // named action
            List<NgxToken> commandNames = new ArrayList<>(tokens).subList(1, tokens.size());
            NgxToken mainCommandName = commandNames.get(0);
            List<NgxToken> commandAliasesTokens = commandNames.subList(1, commandNames.size());

            if (mainCommandName == null) {
                throw new EngineException("No valid action name: " + commandBlock);
            }

            NgxParam permission = commandBlock.findParam("permission");
            NgxParam description = commandBlock.findParam("description");

            String actionName = StringUtils.lowerCase(StringUtils.strip(mainCommandName.getToken(), "\""));
            String[] actionAliases = commandAliasesTokens.stream().map(e -> StringUtils.strip(e.getToken(), "\"")).toArray(String[]::new);
            String actionPermission = (permission == null) ? null : StringUtils.strip(permission.getValue(), "\"");
            String actionDescription = (description == null) ? null : StringUtils.strip(description.getValue(), "\"");
            List<EngineEffect> effects = parseCommandActionEffects(actionBlock);

            if (actions.containsKey(actionName)) {
                EngineX.logger().warning(actionName + " was overridden: " + actionBlock);
            }

            actions.put(actionName, new EngineCommandAction(actionName, actionAliases, actionPermission, actionDescription, effects));
            EngineX.logger().info("Loaded action '" + actionName + "'.");
        }

        return actions;
    }

    public static List<EngineEffect> parseCommandActionEffects(NgxBlock actionBlock) throws EngineException {

        List<EngineEffect> effects = new ArrayList<>();
        NgxBlock effectsBlock = actionBlock.findBlock("do");
        if (effectsBlock == null) {
            throw new EngineException("No 'do' section in action: " + actionBlock);
        }

        Collection<NgxEntry> effectEntries = effectsBlock.getEntries();
        if (effectEntries.isEmpty()) {
            throw new EngineException("Empty 'do' section in action: " + actionBlock);
        }

        EngineX.logger().info("Trying to load " + effectEntries.size() + " effects: " + effectEntries);
        for (NgxEntry effectEntry : effectEntries) {

            NgxParam effect = (NgxParam) effectEntry;
            String effectName = effect.getName();
            List<String> effectValues = effect.getValues();

            if (!EngineEffectRegistry.isEffect(effectName)) {
                throw new EngineException(effectName + " is not valid effect name: " + effectEntry);
            }

            EngineEffect engineEffect = EngineEffectRegistry.getEffect(effectName);
            String[] varargs = effectValues.stream().map(e -> StringUtils.strip(e, "\"")).toArray(String[]::new);
            Class[] varargMap = engineEffect.getVarargMap();

            if (varargs.length != varargMap.length) {
                throw new EngineException("Effect " + effectName + " accepts only " + varargMap.length
                        + " arguments. Argument map: " + Arrays.toString(varargMap) + ". Provided arguments: " + Arrays.toString(varargs));
            }

            engineEffect.setVarargs(varargs);
            engineEffect.setName(effectName);
            effects.add(engineEffect);
            EngineX.logger().info("Loaded effect '" + effectName + "': " + Arrays.toString(varargs));
        }

        return effects;
    }
}
