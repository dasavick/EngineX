package pl.daffit.enginex;

public class EngineException extends Exception {

    public EngineException() {
    }

    public EngineException(String s) {
        super(s);
    }

    public EngineException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public EngineException(Throwable throwable) {
        super(throwable);
    }

    public EngineException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
