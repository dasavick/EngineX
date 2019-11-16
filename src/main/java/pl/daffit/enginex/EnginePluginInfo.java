package pl.daffit.enginex;

import java.util.Arrays;

public class EnginePluginInfo {

    private final String name;
    private final String version;
    private final String[] authors;

    public EnginePluginInfo(String name, String version, String[] authors) {
        this.name = name;
        this.version = version;
        this.authors = authors;
    }

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public String toString() {
        return "EnginePluginInfo(name=" + this.getName() + ", version=" + this.getVersion() + ", authors=" + Arrays.deepToString(this.getAuthors()) + ")";
    }
}