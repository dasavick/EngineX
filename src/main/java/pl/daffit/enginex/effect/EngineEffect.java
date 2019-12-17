package pl.daffit.enginex.effect;

public abstract class EngineEffect {

    private int index = -1;
    private String name;
    private Object[] varargs;

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

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
