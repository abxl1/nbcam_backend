package calculator;
import java.util.ArrayList;

public class Calculator {
    private ArrayList<Integer> arr = new ArrayList<>();

    public ArrayList<Integer> getArrayList() { // getter 사용
        return arr;
    }

    public int calculate(int num1, int num2, String calc) {

        int result = 0;

        switch (calc) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
        }
        return result;
    }
}
