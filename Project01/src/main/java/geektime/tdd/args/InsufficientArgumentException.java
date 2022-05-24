package geektime.tdd.args;

public class InsufficientArgumentException extends RuntimeException {
    private final String option;
    InsufficientArgumentException(String option) {
        super(option);
        this.option = option;
    }
    public String getOption() {
        return option;
    }
}
