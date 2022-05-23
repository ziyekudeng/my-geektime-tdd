package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalOptionException;
import geektime.tdd.args.exceptions.TooManyArgumentsException;
import geektime.tdd.args.exceptions.UnsupportedOptionTypeException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OptionClass<T> {
    private Class<T> optionsClass;
    private Map<Class<?>, OptionParser<?>> parsers;

    public OptionClass(Class<T> optionsClass, Map<Class<?>, OptionParser<?>> parsers) {
        this.optionsClass = optionsClass;
        this.parsers = parsers;
    }

    public T parse(String[] args) {
        try {
            List<String> arguments = Arrays.asList(args);

            Constructor<?> constructor = this.optionsClass.getDeclaredConstructors()[0];

            Object[] values = Arrays.stream(constructor.getParameters()).map(it -> {
                try {
                    return parseOption(arguments, it);
                } catch (TooManyArgumentsException e) {
                    throw new RuntimeException(e);
                }
            }).toArray();

            return (T) constructor.newInstance(values);
        } catch (IllegalOptionException e) {
            throw e;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object parseOption(List<String> arguments, Parameter parameter) throws TooManyArgumentsException {
        if (!parameter.isAnnotationPresent(Option.class)) {
            throw new IllegalOptionException(parameter.getName());
        }
        Option option = parameter.getAnnotation(Option.class);
        if (!parsers.containsKey(parameter.getType())) {
            throw new UnsupportedOptionTypeException(option.value(), parameter.getType());
        }
        return parsers.get(parameter.getType()).parse(arguments, option);
    }
}
