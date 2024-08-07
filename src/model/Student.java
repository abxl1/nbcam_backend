package model;

import java.util.Scanner;

public class Student {
    private String studentId;   // 수강생 ID
    private String studentName; // 수강생 이름
    private String studentStatus; // 수강생 상태
    private String[] subjects;  // 수강생 수강 과목목록

    // 생성자
    public Student(String seq, String studentName, String[] subjects) {
        this.studentId = seq;
        this.studentName = studentName;
        this.subjects = subjects;
        this.studentStatus = "상태미지정";
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentStatus() { return studentStatus; }

    public String[] getSubjects() { return subjects; }

    // 수강생 상태 등록 메서드
    public void setStudentStatus() {
        System.out.print("수강생의 상태를 (Green, Red, Yellow)중에 입력해주세요. : ");

        boolean vaildInput = false;
        while(!vaildInput){
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if(input.equals("Green") || input.equals("Red") || input.equals("Yellow")){
                studentStatus = input;
                this.studentStatus = studentStatus;
                vaildInput = true;
            }else{
                System.out.print("잘못된 상태값입니다. (Green, Red, Yellow)중에 입력해주세요. : ");
            }
        }
    }

    // 수강생 이름 입력 메서드
    public static String inputName(){
        Scanner sc = new Scanner(System.in);
        boolean vaildInput = false;
        String studentName = sc.nextLine();
        while (!vaildInput){
            if(studentName.matches("^[가-힣]+$")){
                vaildInput = true;
            }else {
                System.out.println("유효하지 않은 입력입니다. 이름은 한글로만 입력해주세요.");
                System.out.print("수강생 이름 입력(한글만입력가능): ");
                studentName = sc.next();
                sc.nextLine();
            }
        }
        return studentName;
    }

    // 수강생 이름 수정 메서드
    public void changeName(){
        System.out.println("==================================");
        System.out.println("수강생 이름 수정 실행중...");
        System.out.print("수정 할 이름을 입력하세요. : ");
        String beforeName = this.studentName;
        String newName = inputName();
        this.studentName = newName;

        System.out.println("이름 수정 완료!");
        System.out.println(String.format("\"%s\"에서 \"%s\"(으)로 이름이 변경됐습니다.", beforeName, newName));
    }

    // 수강생의 상태 수정 메서드
    public void changeStatus(){
        System.out.println("==================================");
        System.out.println("수강생 상태 수정 실행중...");

        String status = this.studentStatus;
        System.out.println(String.format("현재 %s수강생의 상태는 \"%s\"입니다.",this.studentName ,status));
        System.out.print("변경 할 ");
        setStudentStatus();
        System.out.println("수강생 상태를 수정 했습니다.");
        System.out.println(this.getStudentId() + " " + this.getStudentName()
                + " 수강생의 상태는 " + this.studentStatus + "입니다.");
    }
}
