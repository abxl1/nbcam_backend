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
    }
}