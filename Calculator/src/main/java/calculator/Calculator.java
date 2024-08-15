package calculator;
import java.util.ArrayList;

public class Calculator {
    private ArrayList<Integer> arr;

    public Calculator(ArrayList<Integer> arr) { // 생성자를 통해서 매개변수인 컬렉션 리스트를 초기화
        this.arr = arr;
    }

    public ArrayList<Integer> getArrayList() { // getter 사용
        return arr;
    }

    public int calculate(int num1, int num2, String calc) { // 사칙연산

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
                if (num2 == 0) { // 0으로 나눌 때
                    throw new ArithmeticException("0으로 나누지 마시오.");
                    // 에러 메시지를 throw하면서 코드 진행이 종료됨.
                    // 종료시키지 않고 계속 진행시킬 수 있는가? ''' try...catch?
                }
                result = num1 / num2;
                break;
        }
        return result;
    }

    public void calcInvalidOperator(String calc) throws IllegalArgumentException {
        switch (calc) {
            case "+" :
            case "-" :
            case "*" :
            case "/" :
                break;
            default: throw new IllegalArgumentException();
        }
    }

    public String removeInvalid(String remove) { // 삭제 메서드
        if (remove.equals("remove")) {
            arr.remove(0);
        }
        return remove;
    }

    public String inquiryInvalid(String inquiry) { // 조회 메서드
        if (inquiry.equals("inquiry")) {
            System.out.println(arr.toString());
        }
        return inquiry;
    }
}
