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
            case "/":
                if (num2 == 0) {
                    System.out.println("들어갔나요?");
                    throw new ArithmeticException("0으로 나누지 마시오.");
                    // 에러 메시지를 throw하면서 코드 진행이 종료됨.
                    // 종료시키지 않고 계속 진행시킬 수 있는가? ''' try...catch?
                }
                result = num1 / num2;
                break;
        }
        return result;
    }
}
