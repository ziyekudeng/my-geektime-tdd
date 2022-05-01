package geektime.tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArgsTest {
    // 策略一：正则切割，比较复杂
    // -l -p 8080 -d /usr/logs

    // 策略二：拆分成数组
    // 实际上我们可以把它看成是一系列以dash开头的标志，然后把这个数组进行分割
    // 分割成的数组如下。分完段之后，我们就可以由对应的Annotation 标注好的类型结构里面去读
    // 读取的时候时候再根据它生成的新的小结构转换成对应的这样一个类型。
    // [-l], [-p, 8080], [-d, /usr/logs]
    // 上面这种是我们选择的实现策略

    // ①像上面那种利用数组进行分段，我们可以利用一个index 标志位，找到每一个位置，然后去读。
    // ②我们也可以把这么个list做成一个特定的interface对它进行整体的分类处理
    // 这里我们选那种写法都可以，还有map的写法等

    // 策略三：map
    // {-l:[], -p:[8080], -d:[/usr/logs] }

    // Single Options:
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");

        assertTrue(option.logging());
    }

    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);

        assertFalse(option.logging());
    }

    static record BooleanOption(@Option("l") boolean logging) {
    }

    @Test
    public void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    static record IntOption(@Option("p") int port) {

    }

    @Test
    public void should_get_string_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    static record StringOption(@Option("d") String directory) {}

    @Test
    public void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    static record MultiOptions(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    // sad path:
    //  - bool -l t / -l t f
    //  - int -p/ -p 8080 8081
    //  - string -d/ -d /usr/logs /usr/vars
    // default value
    // - bool : false
    // - int : 0
    // - string : ""

    // -g this is a list -d 1 2 -3 5
    @Test
    @Disabled
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    static record Options(@Option("l") boolean logging, @Option("p") int port, @Option("d") String directory) {
    }

    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }
}
