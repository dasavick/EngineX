package pl.daffit.enginex;

import lombok.Data;

@Data
public class EnginePluginInfo {

    private final String name;
    private final String version;
    private final String[] authors;
}