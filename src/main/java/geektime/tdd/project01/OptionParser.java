package geektime.tdd.project01;

import java.util.List;

interface OptionParser<T> {
    T parse(List<String> arguments, Option option);
}
