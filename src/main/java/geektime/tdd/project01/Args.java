package geektime.tdd.project01;

import java.util.Map;

public class Args {
    public static <T> T parse(Class<T> optionsClass, String... args) {
        return new OptionClass<T>(optionsClass, PARSERS).parse(args);
    }

    private static Map<Class<?>, OptionParser<?>> PARSERS = Map.of(boolean.class, OptionParsers.bool(),
            int.class, OptionParsers.unary(0, Integer::parseInt),
            String.class, OptionParsers.unary("", String::valueOf),
            String[].class, OptionParsers.list(String[]::new, String::valueOf),
            Integer[].class, OptionParsers.list(Integer[]::new, Integer::parseInt));
}
