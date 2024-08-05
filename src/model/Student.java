package model;

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

    // Setter
    public String setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
        return studentStatus;
    }
}
