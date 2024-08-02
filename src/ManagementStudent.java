import java.util.Scanner;

// 점수관리 클래스
public class ManagementStudent {

    // 기능 구현 - 강동준님
    // 수강생 등록
    public static void createStudent() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();
        sc.nextLine();

        // 수강생 수강 과목 여러개 입력받기
        // 예시 코드입니다
        System.out.print("수강 과목 입력(,로 구분 해 주세요.): ");
        String[] subjects = sc.nextLine().split(",");

        // 수강생 인스턴스 생성 예시 코드
        // Student student = new Student(CampManagementApp.sequence(CampManagementApp.INDEX_TYPE_STUDENT), studentName, subjects);
















        System.out.println("수강생 등록 성공!\n");
    }

    // 기능 구현 - 김나영님
    // 수강생 목록 조회
    public static void inquireStudent() {
        String newStudentId;
        String newStudentName;
//        String[] newSubjects;

        System.out.println("\n수강생 목록을 조회합니다...");

        for (int i = 0; i < CampManagementApp.studentStore.size() ; i++) {
            newStudentId = CampManagementApp.studentStore.get(i).getStudentId();
            newStudentName = CampManagementApp.studentStore.get(i).getStudentName();

            System.out.println (" 학생 아이디 : " + newStudentId + ", 학생 이름 : " + newStudentName );
            System.out.println();
        }

        if(CampManagementApp.studentStore.isEmpty()) {
            System.out.println();
            System.out.println("등록된 수강생이 없습니다.");
        }
























        System.out.println("\n수강생 목록 조회 성공!");
    }

}
