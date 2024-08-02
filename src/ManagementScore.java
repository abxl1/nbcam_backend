import java.util.Scanner;

// 점수관리 클래스
public class ManagementScore {

    // 사용자로부터 관리할 수강생 ID를 입력받는 메서드. 수정X
    private static String getInputStudentId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n관리할 수강생의 번호를 입력하시오 : ");
        return sc.next();
    }

    // 기능 구현 - 추종윤님
    // 수강생의 과목별 시험 회차 및 점수 등록
    public static void createScore() {
        String studentId = getInputStudentId();





        System.out.println("시험 점수를 등록합니다...");
        // 점수 인스턴스 생성 예시 코드
        // Score score = new Score(CampManagementApp.sequence(CampManagementApp.INDEX_TYPE_SCORE), "학생ID","과목ID",1, 100, "A");














        System.out.println("\n점수 등록 성공!");
    }

    // 기능 구현 -정승헌님
    // 수강생의 과목별 회차 점수 수정
    public static void updateRoundScoreBySubject() {
        String studentId = getInputStudentId();





        System.out.println("시험 점수를 수정합니다...");
















        System.out.println("\n점수 수정 성공!");
    }

    // 기능 구현 - 한지은
    // 수강생의 특정 과목 회차별 등급 조회
    public static void inquireRoundGradeBySubject() {
        String studentId = getInputStudentId();




        System.out.println("회차별 등급을 조회합니다...");















        System.out.println("\n등급 조회 성공!");
    }


}
