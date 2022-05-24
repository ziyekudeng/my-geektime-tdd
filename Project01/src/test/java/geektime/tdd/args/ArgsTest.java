package geektime.tdd.args;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ArgsTest {
    // -l -p 8080 /usr/logs
    // list(index) [-l], [-p, 8080], [-d, /usr/logs]
    // map {-l: [], -p:[8080], -d:[/usr/logs]}
    // single option:
    // - Bool -l

    @Test
    public void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    static record MultiOptions(@Option("l")boolean logging, @Option("p")int port, @Option("d")String directory) {
    }

    @Test
    public void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class, () -> Args.parse(OptionsWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("port", e.getParameter());
    }

    static record OptionsWithoutAnnotation(@Option("l") boolean logging, int port, @Option("d") String directory){
    }
    // BooleanOptionParserTest:
    // sad path:
    // TODO: - bool -l t / -l t f
    // default:
    // TODO: - bool : false

//    @Test
//    public void should_not_accept_extra_argument_for_boolean_option() {
//        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> Args.parse(BooleanOption.class, "-l", "t"));
//        assertEquals("l", e.getOption());
//    }

//    @Test
//    public void should_not_accept_extra_argument_for_boolean_option_2() {
//        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> new BooleanParser().parse(asList("-l", "t"), option("l")));
//        assertEquals("l", e.getOption());
//    }


    // SingleValueOptionParserTest:
    // sad path:
    // TODO: - int -p / -p 8080 8081
    // TODO: - string -d / -d /usr/logs /usr/vars
    // default value
    // TODO: - int : 0
    // TODO: - string : ""



    @Test
    @Disabled
    public void should_example_1(){
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    @Test
    @Disabled
    public void should_example_2(){
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");

        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }



    static record ListOptions(@Option("g") String[] group, @Option("d") int[] decimals) {
    }

//    @Test
//    @Disabled
//    public void should() {
//        Arguments args = Args.parse("l:b, p:d, d:s", "-l", "-p", "8080", "-d", "/usr/logs");
//        args.getBool("l");
//        args.getInt("p");
//
//        Options options = Args.parse(Options.class, "-l", "-p", "8080", "-d", "/usr/logs");
//        options.logging();
//        options.port();
//    }
 }
