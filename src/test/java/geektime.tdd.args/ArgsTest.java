package geektime.tdd.args;

import geektime.tdd.args.exceptions.IllegalOptionException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
    //    我们中的大多数人都不得不时不时地解析一下命令行参数。
    //    如果我们没有一个方便的工具，那么我们就简单地处理一下传入 main 函数的字符串数组。
    //    有很多开源工具可以完成这个任务，但它们可能并不能完全满足我们的要求。所以我们再写一个吧。　
    //    传递给程序的参数由标志和值组成。标志应该是一个字符，前面有一个减号。
    //    每个标志都应该有零个或多个与之相关的值。
    //    例如：　-l -p 8080 -d /usr/logs　“l”（日志）没有相关的值，它是一个布尔标志，如果存在则为 true，不存在则为 false。
    //    “p”（端口）有一个整数值，“d”（目录）有一个字符串值。
    //    标志后面如果存在多个值，则该标志表示一个列表：　-g this is a list -d 1 2 -3 5　"g"表示一个字符串列表[“this”, “is”, “a”, “list”]，
    //    “d"标志表示一个整数列表[1, 2, -3, 5]。　如果参数中没有指定某个标志，那么解析器应该指定一个默认值。例如，false 代表布尔值，0 代表数字，”"代表字符串，[]代表列表。
    //    如果给出的参数与模式不匹配，重要的是给出一个好的错误信息，准确地解释什么是错误的。　确保你的代码是可扩展的，即如何增加新的数值类型是直接和明显的。

    //由简单至复杂
    // 1. 单选项单参数
    // 2. 多选项单参数
    // 3. 一个选项多参数

    //单选项单参数
    //  -l -p 8080 -d /usr/logs

    //多选项单参数
    @Test
    public void should_parse_multi_options() {
        MultiOptions multiOptions = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(multiOptions.logging());
        assertEquals(8080, multiOptions.port());
        assertEquals("/usr/logs", multiOptions.directory());
    }

    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {

    }

    @Test
    public void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class, () -> Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("directory", e.getParameter());
    }

    static record OptionsWithoutAnnotation(@Option("l") boolean logging, @Option("p") int port, String directory) {

    }

    //边界检查
    //TODO: -bool -l t / -l t f
    //TODO: - int -p/p -p 8080 8001
    //TODO: - string -d/ -d /usr/logs /var/logs

    //默认值
    //TODO: bool false
    //TODO: int 0
    //TODO: String ""


    //-g this is a list -d 1 2 -3 5
    @Test
    public void should_example_two() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new Integer[]{1, 2, -3, 5}, options.decimals());
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") Integer[] decimals) {

    }
}
