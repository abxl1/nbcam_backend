package model;

public class Score {
    private String scoreId;     // 점수 ID
    private String studentId;   // 수강생 ID
    private String subjectId;   // 과목 ID
    private int round;          // 회차 (1~10)
    private int score;          // 점수 (0~100)
    private String grade;       // 등급 (점수 등록 시 자동 추가되어야 함)

    // 생성자
    public Score(String scoreId, String studentId, String subjectId, int round, int score, String grade) {
        this.scoreId = scoreId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.round = round;
        this.score = score;
        this.grade = grade;
    }

    // Getter
    public String getScoreId() { return scoreId; }

    public String getStudentId() { return studentId; }

    public String getSubjectId() { return subjectId;}

    public int getRound() { return round; }

    public int getScore() { return score; }

    public String getGrade() { return grade; }

    public void setScore(int score) { this.score = score; }

    public void setGrade(String grade) { this.grade = grade; }

}
