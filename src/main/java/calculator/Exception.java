package calculator;

public class Exception {
    public void exceptDivZero(int num2, String calc) throws ArithmeticException {
        if (calc.equals("/") && num2 == 0) {
            throw new ArithmeticException();
        }
    }

    public void exceptInvalidOperator(String calc) throws IllegalArgumentException {
        switch (calc) {
            case "+":
            case "-":
            case "*":
            case "/":
                break;
            default:
                throw new IllegalArgumentException("Invalid operation.");
        }
    }
}