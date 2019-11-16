package pl.daffit.enginex;

import lombok.Data;
import pl.daffit.enginex.command.EngineCommand;

import java.util.List;

@Data
public class EnginePlugin {

    private final EnginePluginInfo info;
    private final List<EngineCommand> commands;
}