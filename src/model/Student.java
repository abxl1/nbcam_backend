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
        System.out.println("수강생의 상태를 (Green, Red, Yellow)중에 입력해주세요.");

        boolean vaildInput = false;
        while(!vaildInput){
            Scanner sc = new Scanner(System.in);
            String input = sc.next();
            if(input.equals("Green") || input.equals("Red") || input.equals("Yellow")){
                studentStatus = input;
                this.studentStatus = studentStatus;
                vaildInput = true;
            }else{
                System.out.println("잘못된 상태값입니다. (Green, Red, Yellow)중에 입력해주세요.");
            }
        }
    }
}
