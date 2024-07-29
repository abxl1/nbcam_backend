package calculator;
import java.util.ArrayList;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner sc = new Scanner(System.in);

//        ArrayList<Integer> arr = calculator.getArrayList(); // getter 사용


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

            System.out.println("결과 : " + result);
            calculator.arr.add(result);

            System.out.println("가장 먼저 저장된 연산 결과를 삭제하시겠습니까? (remove 입력 : 삭제, enter 입력 : 계속)");
            sc.nextLine();

            if (sc.nextLine().equals("remove")) {
                calculator.arr.remove(0);
            }

            System.out.println("저장된 연산결과를 모두 조회하시겠습니까? (inquiry 입력 : 조회, enter 입력 : 계속)");
//            String inquiry = sc.nextLine();

            if (sc.nextLine().equals("inquiry")) {
                System.out.println(calculator.arr.toString());
            }

            System.out.println("더 계산하시겠습니까? (exit 입력 : 종료, enter 입력 : 계속)");

            if (sc.nextLine().equals("exit")) {
                System.out.println("프로그램이 종료되었습니다.");
                break;
            }
        }
    }
}