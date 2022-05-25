package geektime.tdd.project01.exceptions;

public class InsufficientArgumentsException extends RuntimeException {
    private String option;

    public InsufficientArgumentsException(String value) {
        this.option = value;
    }

    public String getOption() {
        return option;
    }
}
