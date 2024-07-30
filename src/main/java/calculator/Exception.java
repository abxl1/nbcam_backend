package calculator;

public class Exception {
    public void exceptInvalidOperator(int num1, int num2, String calc) throws IllegalArgumentException {
        switch (calc) {
            case "+" :
            case "-" :
            case "*" :
            case "/" :
                break;
            default: throw new IllegalArgumentException("Invalid operation.");
        }
    }
}