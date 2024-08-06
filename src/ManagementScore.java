
import model.Score;
import model.Student;
import model.Subject;

import java.util.*;

// 점수관리 클래스
public class ManagementScore {

    private List<Student> students = CampManagementApp.studentStore;
    private List<Subject> subjects = CampManagementApp.subjectStore;
    private List<Score> scores = CampManagementApp.scoreStore;

    // 사용자로부터 관리할 수강생 ID를 입력받는 메서드.
    private String getInputStudentId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n관리할 수강생의 번호를 입력하시오 : ");
        return sc.next();
    }

    // 사용자로부터 관리할 과목이름을 입력받는 메서드
    private String getInputSubjectname() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n관리할 과목을 입력하시오 : ");
        return sc.next();
    }

    //점수를 받아 등급을 반환하는 메서드
    private String returnGrade(String type, int score) {
        String grade = "";
        //필수 과목인지 선택과목인지
        if (type.equals(CampManagementApp.SUBJECT_TYPE_MANDATORY)) {
            //필수 과목일 경우 등급계산
            if (95 <= score) grade = "A";
            else if (90 <= score && score < 95) grade = "B";
            else if (80 <= score && score < 90) grade = "C";
            else if (70 <= score && score < 80) grade = "D";
            else if (60 <= score && score < 70) grade = "F";
            else grade = "N";

        } else {
            //선택 과목일 경우 등급계산
            if (90 <= score) grade = "A";
            else if (80 <= score && score < 90) grade = "B";
            else if (70 <= score && score < 80) grade = "C";
            else if (60 <= score && score < 75) grade = "D";
            else if (50 <= score && score < 60) grade = "F";
            else grade = "N";
        }
        return grade;
    }

    // 입력받은 수강생 id의 점수(Score)목록을 반환하는 메서드
    private List<Score> getStudentScores(String studentId) {
        List<Score> studentScores = new ArrayList<>();
        for (Score score : scores) {
            if (studentId.equals(score.getStudentId())) {
                studentScores.add(score);
            }
        }
        return studentScores;
    }

    // 과목명(subjectName)이 수강생(studentId)의 수강중인 과목인지 체크하는 메서드
    private boolean isSubjectList(String studentId, String subjectName) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId) && Arrays.asList(student.getSubjects()).contains(subjectName)) {
                return true;
            }
        }
        return false;
    }

    // 입력받은 과목명으로 과목 ID를 얻는 메서드.
    private String getSubjectId(String subjectName) {
        for (Subject subject : subjects) {
            if (subject.getSubjectName().equals(subjectName)) {
                return subject.getSubjectId();
            }
        }
        return "";
    }

    // 등록 된 학생 id가 맞는지 체크하는 메서드
    private boolean checkStudentId(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return true;
            }
        }
        return false;
    }

    // 과목타입(필수,선택)을 체크하는 메서드
    private boolean checkType(String subjectId, String type) {
        for (Subject subject : subjects) {
            if (subject.getSubjectId().equals(subjectId)) {
                if (subject.getSubjectType().equals(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    //학생 id,과목 id, round를 받아 해당하는 특정 score객체를 반환하는 메서드
    private Score getThatScore(String studentId, String subjectId, int round) {
        for (Score score : scores) {
            if (score.getSubjectId().equals(subjectId) && score.getStudentId().equals(studentId) && score.getRound() == round) {
                return score;
            }
        }
        return null;
    }


    // 기능 구현 - 추종윤님
    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {

        // 1.점수 등록할 사람 id입력받기 (저장)
        String studentId = getInputStudentId();
        if (!checkStudentId(studentId)) {
            System.out.println("등록되지 않은 수강생입니다.");
            return;
        }

        // 2. 해당 수강생 ID를 가진 수강생 찾기와 과목 등록
        String[] studentSubjects = {};
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) { //학생비교
                studentSubjects = student.getSubjects();  //수강목록을 가져오기
            }
        }

        // 3. 등록할 회차 입력받기(사용자)
        Scanner sc = new Scanner(System.in);
        System.out.print("\n등록할 회차를 입력하시오 (1~10) : ");
        int inputRound = sc.nextInt();
        if (inputRound < 1 || inputRound > 10) {
            System.out.println("1~10 사이로 입력하세요.");
            return;
        }

        System.out.println("시험 점수를 등록합니다...");
        for (String sub : studentSubjects) {

            // 4 - 1 과목별로 점수 입력받기(사용자) , 과목별로 담을 변수를 담기
            System.out.print(sub + "의 점수를 입력하시오 (1~100) :");
            int score = sc.nextInt();
            if (score < 1 || score > 100) {
                System.out.println("1~100 사이로 입력하세요.");
                return;
            }

            // 4 - 2 입력받은 점수가 어느 등급인지 계산
            String subjectType = "";
            String subjectId = "";
            for (Subject subject : subjects) {
                if (subject.getSubjectName().equals(sub)) {
                    subjectType = subject.getSubjectType();
                    subjectId = subject.getSubjectId();
                }
            }
            String grade = returnGrade(subjectType, score);

            // 4 - 3 입력받은 값으로 score인스턴스에 생성하기
            String scoreId = CampManagementApp.sequence(CampManagementApp.INDEX_TYPE_SCORE);
            Score setScore = new Score(scoreId, studentId, subjectId, inputRound, score, grade);
            scores.add(setScore);
        }
        System.out.println("\n점수 등록 성공!");
    }

    // 기능 구현 -정승헌님
    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject() {
        String studentId = getInputStudentId();
        if (!checkStudentId(studentId)) {
            System.out.println("등록되지 않은 수강생입니다.");
            return;
        }
        if (getStudentScores(studentId).isEmpty()) {
            System.out.println("점수가 없는 학생입니다.");
            return;
        }


        String subName = getInputSubjectname();
        if (!isSubjectList(studentId, subName)) {
            System.out.println("수강생의 수강과목중 입력하신 정보가 없습니다.");
            return;
        }
        String subjectId = getSubjectId(subName);
        while (true) {
            int round = 0;

            while (true) {
                try {
                    System.out.println("회차입력");
                    Scanner sc = new Scanner(System.in);
                    round = sc.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("정수를 입력해주세요");
                }
            }


            Score bigScore = null;

            for (int i = 0; i < scores.size(); i++) {
                if (scores.get(i).getStudentId().equals(studentId) && scores.get(i).getSubjectId().equals(subjectId) && scores.get(i).getRound() == round) {
                    bigScore = getThatScore(studentId, subjectId, round);
                    break;
                }
                if (i + 1 == scores.size() && bigScore == null) {
                    System.out.println("해당 회차에 대한 정보가 없습니다.");
                }
            }

            if (bigScore == null) {
                continue;
            }

            System.out.println("점수 : " + bigScore.getScore() + " 등급 : " + bigScore.getGrade() + " 입니다.\n");
            int newScore = 0;
            while (true) {
                try {
                    System.out.println("몇 점으로 수정하시겠습니까? ");
                    Scanner sc = new Scanner(System.in);
                    newScore = sc.nextInt();
                    if (0 <= newScore && newScore <= 100) {
                        break;
                    } else {
                        System.out.println("0~100점 사이로 입력하세요");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("정수를 입력해주세요");
                }
            }


            String type = "";
            while (true) {
                System.out.println("과목의 타입을 입력하세요(MANDATORY or CHOICE)");
                Scanner sc = new Scanner(System.in);
                type = sc.next();

                if (!checkType(subjectId, type)) {
                    System.out.println("과목의 타입을 제대로 입력하세요");
                } else {
                    break;
                }
            }
            bigScore.setScore(newScore);
            bigScore.setGrade(returnGrade(type, newScore));
            System.out.println("수정이 완료되었습니다.");
            System.out.println("점수 : " + bigScore.getScore() + " 등급 : " + bigScore.getGrade() + " 입니다.");
            return;
        }


    }


    // 기능 구현 - 한지은
    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {

        // 조회할 수강생 ID 입력받고 등록여부 체크
        String studentId = getInputStudentId();
        if (!checkStudentId(studentId)) {
            System.out.println("등록되지 않은 수강생입니다.");
            return;
        }

        // 입력받은 수강생 ID로 등록된 점수(Score) 여부 체크
        if (getStudentScores(studentId).isEmpty()) {
            System.out.println("점수를 등록하지 않은 수강생입니다.");
            return;
        }

        // 조회할 과목명 입력받기
        Scanner sc = new Scanner(System.in);
        System.out.print("조회할 과목을 입력하시오 : ");
        String subjectName = sc.next();

        // 입력받은 과목명이 수강생의 수강중인 과목에 포함되어있는지 체크
        boolean includeSubject = isSubjectList(studentId, subjectName);

        if (includeSubject) {   // 수강중인 과목일 경우
            // 과목 ID 얻기
            String subjectId = getSubjectId(subjectName);

            // 수강생의 점수 목록 중 과목 ID가 일치하는 점수 출력
            System.out.println("회차별 등급을 조회합니다...");
            for (Score score : getStudentScores(studentId)) {
                if (score.getSubjectId().equals(subjectId)) {
                    System.out.println("회차 = " + score.getRound() + " | 점수 = " + score.getScore() + " | 등급 = " + score.getGrade());
                }
            }
            System.out.println("등급 조회 성공!");

        } else { // 수강중인 과목이 아닐경우
            System.out.println("수강 중인 과목이 아닙니다.");
        }
    }

    // 기능 구현 - 한지은
    // 수강생의 과목별 평균 등급 조회
    public void inquireAverageGradeBySubject() {
        // 조회할 수강생 ID 입력받기
        String studentId = getInputStudentId();
        if (!checkStudentId(studentId)) {
            System.out.println("등록되지 않은 수강생입니다.");
            return;
        }

        // 입력받은 수강생 ID로 등록된 점수(Score) 여부 체크
        if (getStudentScores(studentId).isEmpty()) {
            System.out.println("점수를 등록하지 않은 수강생입니다.");
            return;
        }

        // 과목별 평균 등급 조회
        for (Subject subject : subjects) {
            int sumScore = 0, avgScore = 0, index = 0;
            for (Score score : getStudentScores(studentId)) {   // 해당 수강생의 점수목록을 전부 순회
                if (score.getSubjectId().equals(subject.getSubjectId())) {
                    sumScore += score.getScore();
                    index++;
                }
            }
            if (index == 0) {
                continue;
            }
            avgScore = sumScore / index; // 과목별 평균점수(총합점수/회차)
            String avgGrade = returnGrade(subject.getSubjectName(), avgScore); // 과목타입, 평균 점수 넣어서 등급 받기
            System.out.println("과목= " + subject.getSubjectName() + " | 평균점수= " + avgScore + " | 평균등급= " + avgGrade);
        }
        System.out.println("과목별 평균등급 조회 성공!");
    }

    //기능구현 - 정승헌
    //특정 상태 수강생들의 필수 과목 평균 등급을 조회합니다
    public void inquireSpecificGradeAvg(){

        String status = getInputStudentStatus();
        int index =0;
        int sum = 0;
        for(Student student:students){
            if(student.getStudentStatus().equals(status)){
                String studentIdStatus = student.getStudentId();//student중 해당 status가진 학생 id 찾기

                for(Score score:scores){
                    if(score.getScoreId().equals(studentIdStatus)) {//score중에 해당 studentid가진 객체찾기
                        String subjectIdStatus=score.getSubjectId();//그 객체들의 과목 id찾기
                        for(Subject subject:subjects){
                            if(subject.getSubjectId().equals(subjectIdStatus)){
                                if(subject.getSubjectType().equals("MANDATORY")){//subject에서 과목이 필수과목인지 체크
                                    sum+=score.getScore();
                                    index +=1;
                                }
                            }
                        }
                    }
                }
                int avg = (sum/index);
                String avgGrade = returnGrade("MANDATORY",avg);
                System.out.println("학생 : " + student.getStudentName() + " 필수과목 등급 : " +avg);
            }
        }

    }




    //상태 입력받고 확인까지
    private static String getInputStudentStatus() {
        while(true){
              Scanner sc = new Scanner(System.in);
              System.out.print("\n관리할 수강생의 상태를 입력하시오 : (Green, Red, Yellow)");
              String status= sc.next();
                for(Student student:students){
                    if(student.getStudentStatus().equals(status)){
                        return status;
                    }else {
                        System.out.println("해당 상태의 수강생이 없습니다.다시 입력하세요.");
                    }
                }

            }
        }
}

