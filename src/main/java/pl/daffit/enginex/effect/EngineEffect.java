package pl.daffit.enginex.effect;

public abstract class EngineEffect {

    private String name;
    private Object[] varargs;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object[] getVarargs() {
        return this.varargs;
    }

    public void setVarargs(Object[] varargs) {
        this.varargs = varargs;
    }

    abstract public EngineEffectExecutor getExecutor();

    abstract public Class[] getVarargMap();
}
