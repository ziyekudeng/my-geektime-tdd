package geektime.tdd.args;

import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class BooleanParserTest {
    @Test // Sad Path
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
                new BooleanParser().parse(asList("-l", "t"), option("l"));
        });
        assertEquals("l", e.getOption());
    }

    @Test
    public void should_not_accept_extra_arguments_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class, () -> {
           new BooleanParser().parse(asList("-l", "t", "f"), option("l"));
        });

        assertEquals("l", e.getOption());
    }

    @Test // Default Value
    public void should_set_default_value_to_false_if_option_not_present() {
        assertFalse(new BooleanParser().parse(asList(), option("l")));
    }

    @Test // Happy Path
    public void should_set_default_value_to_false_if_option_present() {
        assertTrue(new BooleanParser().parse(asList("-l"), option("l")));
    }

    static Option option(String value) {
        return new Option() {
          @Override
          public Class<? extends Annotation> annotationType() {
              return Option.class;
            }
          @Override
          public String value() {
              return value;
          }
        };
    }
}