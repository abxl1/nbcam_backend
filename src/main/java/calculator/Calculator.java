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
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("0으로 나눌 수 없습니다.");
                    break;
                }

            default:
                System.out.print("사칙연산 기호인 '+', '-', '*', '/' 중 한 가지를 입력하세요.");
                break;
        }
        return result;
    }
}
