package pl.daffit.enginex.variable;

import org.apache.commons.lang.StringUtils;
import pl.daffit.enginex.EngineException;
import pl.daffit.enginex.execution.ExecutionContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VariableUtils {

    private static final Pattern VARIABLE_NAME = Pattern.compile("^[a-zA-Z_#]{1,64}$");
    private static final Pattern VARIABLE_PLACEHOLDER = Pattern.compile("\\$([a-zA-Z_#]{1,64})");

    public static void validateVariableName(String name) throws EngineException {
        if (isValidVariableName(name)) {
            return;
        }
        throw new EngineException("Variable name contains illegal characters or is too long: " + name);
    }

    public static void validateVariableExistence(ExecutionContext context, String name) throws EngineException {
        if (context.getVariable(name) != null) {
            return;
        }
        throw new EngineException("Undefined variable: " + name);
    }

    public static boolean isValidVariableName(String name) {
        return VARIABLE_NAME.matcher(name).matches();
    }

    public static String replaceVariables(ExecutionContext context, String text) {

        Matcher matcher = VARIABLE_PLACEHOLDER.matcher(text);
        Map<String, Object> variables = context.getVariables();

        while (matcher.find()) {

            String name = matcher.group(1);
            String fullName = name;
            String[] parts = new String[0];

            if (name.contains("#")) {
                parts = StringUtils.splitPreserveAllTokens(name, '#');
                name = parts[0];
            }

            Object value = variables.get(name);
            if (value == null) {
                value = "<null>";
            } else if (parts.length > 1) {
                try {
                    for (int i = 1; i < parts.length; i++) {
                        Class<?> clazz = (i == 1) ? context.getVariableType(name) : value.getClass();
                        Method method = clazz.getMethod(parts[i]);
                        if (method.getReturnType().equals(Void.TYPE)) {
                            value = "<void>";
                            break;
                        }
                        value = method.invoke(value);
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException exception) {
                    value = "<error>";
                    exception.printStackTrace();
                }
            }

            text = StringUtils.replace(text, "$" + fullName, String.valueOf(value));
        }

        return text;
    }

    public static double parseAsNumber(String value) throws EngineException {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException exception) {
            throw new EngineException("Failed to parse as number: " + value);
        }
    }

    public static boolean isInteger(String value) throws EngineException {
        double variable = parseAsNumber(value);
        return isInteger(variable);
    }

    public static boolean isInteger(double variable) {
        return (variable == Math.floor(variable)) && !Double.isInfinite(variable);
    }

    public static String numericToString(double numeric) {
        if (isInteger(numeric)) {
            return String.valueOf((int) numeric);
        } else {
            return Double.toString(numeric);
        }
    }
}
