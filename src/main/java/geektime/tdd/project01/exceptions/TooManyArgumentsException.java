package geektime.tdd.project01.exceptions;

public class TooManyArgumentsException extends RuntimeException {
    private String option;

    public TooManyArgumentsException(String value) {
        this.option = value;
    }

    public String getOption() {
        return option;
    }
}
