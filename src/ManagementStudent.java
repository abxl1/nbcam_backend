

import model.Student;
import model.Subject;

import java.util.Arrays;
import java.util.Scanner;

// 점수관리 클래스
public class ManagementStudent {

    // 기능 구현 - 강동준님
    // 수강생 등록
    public static void createStudent() {
        Scanner sc = new Scanner(System.in);
        boolean vaildInput = false;
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력(한글만입력가능): ");
        String studentName = sc.next();
        sc.nextLine();
        while (!vaildInput){
            if(studentName.matches("^[가-힣]+$")){
                vaildInput = true;
            }else {
                System.out.println("유효하지 않은 입력입니다. 이름은 한글로만 입력해주세요.");
                studentName = sc.next();
                sc.nextLine();
                }
            }


    // 코드를 입력받아 과목명으로 저장
        for(Subject subject : CampManagementApp.subjectStore){
            System.out.println(subject.getSubjectId()+" "+subject.getSubjectName());
        }
        System.out.print("수강 과목 코드입력[,로 구분 뛰어쓰기 없이 입력해 주세요 ex)SU1,SU2...]: \n");

        String[] subjectsCode = sc.nextLine().split(",");
        String[] subjects = new String[subjectsCode.length];
        for (int i = 0; i < subjectsCode.length; i++) {
            for (Subject subject : CampManagementApp.subjectStore) {
                if (subjectsCode[i].equals(subject.getSubjectId())) {
                    subjects[i] = subject.getSubjectName();
                }
            }
        }

        // 학생 객체 생성하여 저장
        String seq = CampManagementApp.sequence(CampManagementApp.INDEX_TYPE_STUDENT);
        Student input = new Student(seq, studentName, subjects);

        CampManagementApp.studentStore.add(input);


















        System.out.println("수강생 등록 성공!\n");
    }

    // 기능 구현 - 김나영님
    // 등록된 수강생 전체 목록 조회
    public static void inquireStudent() {
        String newStudentId;
        String newStudentName;
        String[] newSubjects;

        // 과목 선언 테스트
//        String[] a = {"a","b"};

        System.out.println("\n수강생 목록을 조회합니다...");
        System.out.println();

        // 학생 추가 테스트
//        CampManagementApp.studentStore.add(new Student("0", "kim", a));
//        CampManagementApp.studentStore.add(new Student("1", "john", a));


        for (int i = 0; i < CampManagementApp.studentStore.size() ; i++) {
            newStudentId = CampManagementApp.studentStore.get(i).getStudentId();
            newStudentName = CampManagementApp.studentStore.get(i).getStudentName();
            newSubjects = CampManagementApp.studentStore.get(i).getSubjects();

            System.out.println (i+1 + "번 학생 고유번호 : " + newStudentId + " / 이름 : " + newStudentName + " / 선택과목 : " + Arrays.toString(newSubjects));
        }

        // 등록된 수강생 없을 때 예외처리
        if(CampManagementApp.studentStore.isEmpty()) {
            System.out.println();
            System.out.println("등록된 수강생이 없습니다.");
        }
























        System.out.println("\n수강생 목록 조회 성공!");
    }

}
