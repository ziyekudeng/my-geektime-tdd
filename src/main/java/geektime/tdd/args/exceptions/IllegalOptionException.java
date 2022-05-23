package geektime.tdd.args.exceptions;

public class IllegalOptionException extends RuntimeException {
    private String parameter;

    public IllegalOptionException(String value) {
        this.parameter = value;
    }

    public String getParameter() {
        return parameter;
    }
}