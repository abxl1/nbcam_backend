package calculator;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Exception exception = new Exception();
        Scanner sc = new Scanner(System.in);

        // setter가 필요한 상황 -> 컬렉션 전체를 수정해야 할 때
        // 현재는 새 연산값을 넣고 첫 연산값을 빼므로 전체적인 요소의 변화는 필요가 없음
        ArrayList<Integer> arr = calculator.getArrayList(); // getter 사용

        while (true) {

            System.out.print("첫 번째 숫자를 입력하세요: ");
            int num1 = sc.nextInt();

            System.out.print("사칙연산 기호를 입력하세요: ");
            sc.nextLine();
            String calc = sc.nextLine();

            System.out.print("두 번째 숫자를 입력하세요: ");
            int num2 = sc.nextInt();

            int result = 0;

            result = calculator.calculate(num1, num2, calc);

            try {
                exception.exceptInvalidOperator(num1, num2, calc);
            } catch(IllegalArgumentException e) { // 연산 기호를 잘못 입력한 경우
                System.out.println("올바른 연산기호 (+, -, *, /)를 입력해야 합니다.");
                throw new IllegalArgumentException();
                // 에러 메시지를 throw하면서 코드 진행이 종료됨.
                // 종료시키지 않고 계속 진행시킬 수 있는가? ''' try...catch?
            }

            System.out.println("결과 : " + result);
            arr.add(result);

            System.out.println("가장 먼저 저장된 연산 결과를 삭제하시겠습니까? (remove 입력 : 삭제, enter 입력 : 계속)");
            sc.nextLine();

            if (sc.nextLine().equals("remove")) {
                arr.remove(0);
            }

            System.out.println("저장된 연산결과를 모두 조회하시겠습니까? (inquiry 입력 : 조회, enter 입력 : 계속)");
//            String inquiry = sc.nextLine(); // 반복될 연산이 복잡해졌을 경우에 변수 선언이 더 효율적

            if (sc.nextLine().equals("inquiry")) {
                System.out.println(arr.toString());
            }

            System.out.println("더 계산하시겠습니까? (exit 입력 : 종료, enter 입력 : 계속)");

            if (sc.nextLine().equals("exit")) {
                System.out.println("프로그램이 종료되었습니다.");
                break;
            }
        }
    }
}