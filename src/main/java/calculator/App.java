package calculator;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("첫 번째 숫자를 입력하세요: ");
        int Num1 = sc.nextInt();
        sc.nextLine();

        System.out.print("사칙연산 기호를 입력하세요: ");
        String Calc = sc.nextLine();

        System.out.print("두 번째 숫자를 입력하세요: ");
        int Num2 = sc.nextInt();

        int result = 0;
        boolean valid = true;

        switch (Calc) {
            case "+" :
                result = Num1 + Num2;
                break;
            case "-" :
                result = Num1 - Num2;
                break;
            case "*" :
                result = Num1 * Num2;
                break;
            case "/":
                if (Num2 != 0) {
                    result = Num1 / Num2;
                    break;
                }

                else {
                    System.out.println("0으로 나눌 수 없습니다. 다른 숫자를 입력하세요.");
                    valid = false;
                    break;
                }

            default:
                System.out.print("사칙연산 기호인 '+', '-', '*', '/' 중 한 가지를 입력하세요.");
                valid = false;
                break;
        }

        if (valid) {
            System.out.print("결과 : " + result);
        }
    }
}