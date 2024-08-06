import model.Student;
import model.Subject;

import java.util.Arrays;
import java.util.Scanner;



// 수강생 관리 클래스
public class ManagementStudent {
    // 스캐너
    static Scanner sc = new Scanner(System.in);

    // 기능 구현 - 강동준님
    // 수강생 등록
    public static void createStudent() {
        boolean vaildInput = false;
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력(한글만입력가능): ");
        String studentName = Student.inputName();
        ManagementStudent ms = new ManagementStudent();
        String[] subjects = ms.inputSubject();

        String seq = CampManagementApp.sequence(CampManagementApp.INDEX_TYPE_STUDENT);
        Student input = new Student(seq, studentName, subjects);
        CampManagementApp.studentStore.add(input);
        System.out.println("수강생 등록 성공!\n");
    }

    // 코드를 입력받아 과목명으로 저장
    public String[] inputSubject(){
        Scanner sc = new Scanner(System.in);
        boolean found = false;
        for(Subject subject : CampManagementApp.subjectStore){
            System.out.println(subject.getSubjectId()+" "+subject.getSubjectName());
        }
        System.out.print("수강 과목 코드입력[,로 구분 뛰어쓰기 없이 대문자로 정확히 입력해 주세요 ex)SU1,SU2...]: \n");

        String[] subjectsCode = sc.nextLine().split(",");
        String[] subjects = new String[subjectsCode.length];

        for (String code : subjectsCode) {
            found = false;
            for (Subject subject : CampManagementApp.subjectStore){
                if(code.equals(subject.getSubjectId())){
                    found = true;
                    break;
                }
            }
            if(!found){
                break;
            }
        }
        if(!found){
            System.out.println("잘못된 과목 코드를 입력했습니다, 다시 입력해주세요");
            inputSubject();
        }else{
            for (int i = 0; i < subjectsCode.length; i++) {
                for (Subject subject : CampManagementApp.subjectStore) {
                    if (subjectsCode[i].equals(subject.getSubjectId())) {
                        subjects[i] = subject.getSubjectName();
                    }
                }
            }
        }
        return subjects;
    }


    // 수강생 상태 등록 메서드
    public static void createStudentStatus(){
        System.out.println("==================================");
        System.out.println("수강생의 상태등록 실행중...");
        System.out.println("상태를 등록할 수강생의 이름을 입력해주세요 : ");
        Scanner sc = new Scanner(System.in);
        String studentName = sc.next();
        sc.nextLine();
        boolean found = false;

        // 수강생 객체에 접근
        for(Student st : CampManagementApp.studentStore){
            if(studentName.equals(st.getStudentName())){
                st.setStudentStatus();
                System.out.println("수강생 상태 등록을 완료했습니다.");
                System.out.println(st.getStudentId() + " " + st.getStudentName()
                        + " 수강생의 상태는 " + st.getStudentStatus() + "입니다.");
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("등록되지않은 수강생입니다, 수강생 관리 화면으로 돌아갑니다.");
        }
    }

    // 수강생 정보(이름, 상태) 수정 메서드
    public static void changeStudent(){
        System.out.println("==================================");
        System.out.println("수강생 정보 수정 실행중...");
        System.out.println("정보(이름, 상태)를 수정 할 수강생의 이름을 입력하세요 : ");
        Scanner sc = new Scanner(System.in);
        String studentName = sc.next();
        sc.nextLine();
        boolean found = false;

        for(Student st : CampManagementApp.studentStore) {
            if (st.getStudentName().equals(studentName)) {
                found = true;
                System.out.println("수정 할 내용을 선택하세요.");
                System.out.println("1. 수강생 이름 수정");
                System.out.println("2. 수강생 상태 수정");
                int input = sc.nextInt();

                switch (input) {
                    case 1 -> st.changeName(); // 수강생 이름 수정
                    case 2 -> st.changeStatus(); // 수강생 상태 수정
                }
            }
        }

        if(!found){
            System.out.println("등록되지않은 수강생입니다, 수강생 관리 화면으로 돌아갑니다.");
        }
    }





















    // 기능 구현 - 김나영님
    // 등록된 수강생 목록 조회 ( 전체 조회 or 상태별 조회 )
    public static void inquireStudent() {
        boolean flag = true;
        boolean found = false;

        while (flag) {

            String newStudentId; // 수강생 아이디
            String newStudentName; // 수강생 이름
            String newStudentStatus; // 수강생 상태
            String[] newSubjects; // 수강생 선택 과목

            System.out.println("==================================");
            System.out.println("수강생 목록 조회 프로그램 실행 중...");
            System.out.println("1. 전체 수강생 목록");
            System.out.println("2. 상태별 수강생 목록");
            System.out.println("3. 이전으로");
            System.out.print("조회 항목을 선택하세요 : ");
            int input = sc.nextInt();

            switch (input) {

                // 전체 수강생 목록 조회
                case 1 -> {
                    System.out.println("\n수강생 목록을 조회합니다...");

                    // 전체 수강생 목록이 비었다면 예외처리
                    if (!CampManagementApp.studentStore.isEmpty()) {

                        // 전체 수강생 출력
                        for (Student student : CampManagementApp.studentStore) {
                            newStudentId = student.getStudentId();
                            newStudentName = student.getStudentName();
                            newStudentStatus = student.getStudentStatus();
                            newSubjects = student.getSubjects();

                            System.out.println("학생 고유번호 : " + newStudentId + " / 이름 : " + newStudentName + " / 상태 : " + newStudentStatus + " / 선택과목명 : " + Arrays.toString(newSubjects));
                        }
                        System.out.println("\n전체 수강생 목록 조회 성공!");
                    } else {
                        System.out.println();
                        System.out.println("등록된 수강생이 없습니다.");
                    }
                    return;
                }

                // 상태별 수강생 목록 조회
                case 2 -> {
                    System.out.println("\n상태별 수강생 목록을 조회합니다...");
                    System.out.println();
                    System.out.println("1. Green");
                    System.out.println("2. Red");
                    System.out.println("3. Yellow");
                    System.out.print("항목을 선택하세요 : ");
                    input = sc.nextInt();

                    // 조회할 상태 선택
                    String status = switch (input) {
                        case 1 -> "Green";
                        case 2 -> "Red";
                        case 3 -> "Yellow";
                        default -> {
                            System.out.println("잘못된 입력입니다.");
                            flag = false;
                            yield "Invalid";
                        }
                    };
                    System.out.println();

                    // 상태별 수강생 목록 출력
                    for (Student student : CampManagementApp.studentStore) {
                        if (status.equals(student.getStudentStatus())) {
                            newStudentId = student.getStudentId();
                            newStudentName = student.getStudentName();
                            System.out.println("학생 고유번호 : " + newStudentId + " / 이름 : " + newStudentName);
                            found = true;
                        }
                    }

                    // 특정 상태에 지정된 수강생이 비었다면 예외처리
                    if(!found){
                        System.out.println(status + " 상태에 지정된 수강생이 없습니다.");
                    } else {
                        System.out.println(status +" 상태의 수강생 목록 조회 성공!");
                    }
                }

                case 3 -> { flag = false; }

                default -> {
                    System.out.println("잘못된 입력입니다. 다시 선택해 주세요.");
                    flag = false;
                }
            }
        }
    }
}
