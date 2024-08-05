
import model.Score;
import model.Student;
import model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

// 점수관리 클래스
public class ManagementScore {

    static List<Student> students = CampManagementApp.studentStore;
    static List<Subject> subjects = CampManagementApp.subjectStore;
    static List<Score> scores = CampManagementApp.scoreStore;

    // 사용자로부터 관리할 수강생 ID를 입력받는 메서드.
    private static String getInputStudentId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n관리할 수강생의 번호를 입력하시오 : ");
        return sc.next();
    }

    //점수를 받아 등급을 반환하는 메서드 등급 만들기
    private static String returnGrade(String type, int score) {
        String grade = "";

        //필수 과목인지 선택과목인지
        if(type.equals(CampManagementApp.SUBJECT_TYPE_MANDATORY)) {
            //필수 과목일 경우 등급계산
            if(95 <= score ) grade = "A";
            else if(90 <= score && score < 95 ) grade = "B";
            else if(80 <= score && score < 90 ) grade = "C";
            else if(70 <= score && score < 80 ) grade = "D";
            else if(60 <= score && score < 70 ) grade = "F";
            else grade = "N";

        } else {
            //선택 과목일 경우 등급계산
            if(90 <=score ) grade = "A";
            else if(80 <= score && score < 90 ) grade = "B";
            else if(70 <= score && score < 80 ) grade = "C";
            else if(60 <= score && score < 75 ) grade = "D";
            else if(50 <= score && score < 60 ) grade = "F";
            else grade = "N";
        }
        return grade;

    }
    // 입력받은 수강생의 점수(Score)목록을 반환하는 메서드
    private static List<Score> getStudentScores (String studentId) {
        List<Score> studentScores = new ArrayList<>();
        for (Score score : scores) {
            if (studentId.equals(score.getStudentId())) {
                studentScores.add(score);
            }
        }
        return studentScores;
    }

    // 과목명(subjectName)이 수강생(studentId)의 수강중인 과목인지 체크하는 메서드
    private static boolean isSubjectList (String studentId, String subjectName) {
        for (Student student : students) {
            if(student.getStudentId().equals(studentId) && Arrays.asList(student.getSubjects()).contains(subjectName)) {
                return true;
            }
        }
        return false;
    }

    // 입력받은 과목명으로 과목 ID를 얻는 메서드.
    private static String getSubjectId (String subjectName) {
        for (Subject subject : subjects) {
            if (subject.getSubjectName().equals(subjectName)) {
                return subject.getSubjectId();
            }
        }
        return "";
    }


    // 기능 구현 - 추종윤님
    // 수강생의 과목별 시험 회차 및 점수 등록
    public static void createScore() {

        // 1.점수 등록할 사람 id입력받기 (저장)
        String studentId = getInputStudentId();

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
            System.out.println("유효한 입력값이 아닙니다.");
            return;
        }
        // 이미 등록한 회차일 경우 예외처리
//        if(inputRound == 1){
//            System.out.println("이미 등록된 회차입니다.");
//      }

//         4. 과목별로 점수 입력받기(사용자)
        System.out.println("시험 점수를 등록합니다...");

        for(String sub :studentSubjects ) {

            // 4 - 1 과목별로 점수 입력받기(사용자) , 과목별로 담을 변수를 담기

            System.out.print( sub + "의 점수를 입력하시오 (1~100) :");
            int score = sc.nextInt();

            // 4 - 2 입력받은 점수가 어느 등급인지 계산
            String subjectType = "";
            String subjectId = "";
            for(Subject subject : subjects){
                if(subject.getSubjectName().equals(sub)) {
                    subjectType = subject.getSubjectType();
                    subjectId = subject.getSubjectId();
                }

            }
            String grade = returnGrade(subjectType, score);

            // 4 - 3 입력받은 값으로 score인스턴스에 생성하기
            String scoreId = CampManagementApp.sequence(CampManagementApp.INDEX_TYPE_SCORE);
             Score setScore = new Score(scoreId, studentId, subjectId, inputRound, score, grade );
            scores.add(setScore);
        }
        System.out.println("\n점수 등록 성공!");
    }

    // 기능 구현 -정승헌님
    // 수강생의 과목별 회차 점수 수정
    public static void updateRoundScoreBySubject() {
        String studentId = getInputStudentId();
        //for문 학생숫자*50회 돌아감
        for (int i = 0; i < CampManagementApp.scoreStore.size(); i++) {
            Score bigScore = CampManagementApp.scoreStore.get(i);
            //studentId 일치하는 학생 찾음 (50 개)
            if (bigScore.getStudentId().equals(studentId)) {
                String subjectId = getInputSubjectId();

                //그중에 입력한 과목 id가 있는지 확인 없으면 안듣는거
                //듣는다면 회차수 10개 만큼 나옴
                if (bigScore.getSubjectId().equals(subjectId)) {

                    System.out.println("회차입력");
                    Scanner sc2 = new Scanner(System.in);
                    int round = sc2.nextInt();

                    //과목 타입 체크!
                    String subjectType = "";
                    for (int j = 0; j < CampManagementApp.subjectStore.size(); j++) {
                        Subject check = CampManagementApp.subjectStore.get(i);
                        if (check.getSubjectId().equals(subjectId)) {
                            subjectType = check.getSubjectType();
                            break;
                        }
                    }

                    //10개중 해당 회차를 찾아서 들어가기
                    if (bigScore.getRound() == round) {
                        System.out.println("(점수, 등급)은 (" + bigScore.getScore() + ", " + bigScore.getGrade() + ")입니다.");
                        System.out.println("");
                        System.out.println("몇 점으로 수정하시겠습니까?");
                        Scanner sc3 = new Scanner(System.in);
                        int newScore = sc2.nextInt();

                        //0<=점수<=100 인지 확인
                        if (0 <= newScore && newScore <= 100) {
                            bigScore.setScore(newScore);

                            if (subjectType.equals("필수")) {
                                if (95 <= newScore) {
                                    bigScore.setGrade("A");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (90 <= newScore) {
                                    bigScore.setGrade("B");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (80 <= newScore) {
                                    bigScore.setGrade("C");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (70 <= newScore) {
                                    bigScore.setGrade("D");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (60 <= newScore) {
                                    bigScore.setGrade("E");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else {
                                    bigScore.setGrade("F");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                }
                            } else if (subjectType.equals("선택")) {
                                if (90 <= newScore) {
                                    bigScore.setGrade("A");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (80 <= newScore) {
                                    bigScore.setGrade("B");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (70 <= newScore) {
                                    bigScore.setGrade("V");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (60 <= newScore) {
                                    bigScore.setGrade("D");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else if (50 <= newScore) {
                                    bigScore.setGrade("E");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                } else {
                                    bigScore.setGrade("F");
                                    System.out.println(newScore + " / " + subjectType + " / " + bigScore.getGrade() + " 로 변경되었습니다. ");
                                }
                            }
                        } else {
                            System.out.println("점수값을 0~100사이로 입력하세요");
                        }
                    } else {
                        System.out.println("해당 회차가 없습니다.");
                    }
                } else {
                    System.out.println("과목ID에 해당하는 과목이 없습니다.");
                }
            } else {
                System.out.println("학생Id에 해당하는 정보가 없습니다.");
            }

        }

    }

    // 기능 구현 - 한지은
    // 수강생의 특정 과목 회차별 등급 조회
    public static void inquireRoundGradeBySubject() {

        //test input data(브랜치 병합 시 삭제 예정)
        students.add(new Student("st1", "수강생1", new String[]{"Java", "Spring"}));
        scores.add(new Score("1", "st1", "SU1", 1, 100, "A"));
        scores.add(new Score("2", "st1", "SU1", 2, 50, "F"));
        scores.add(new Score("3", "st1", "SU3", 1, 90, "B"));
        scores.add(new Score("4", "st1", "SU3", 2, 80, "C"));

        // 조회할 수강생 ID 입력받기
        String studentId = getInputStudentId();

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

        if(includeSubject){   // 수강중인 과목일 경우
            // 과목 ID 얻기
            String subjectId = getSubjectId(subjectName);

            // 수강생의 점수 목록 중 과목 ID가 일치하는 점수 출력
            System.out.println("회차별 등급을 조회합니다...");
            for(Score score : getStudentScores(studentId)){
                if (score.getSubjectId().equals(subjectId)){
                    System.out.println("회차 = " + score.getRound() + " | 점수 = " + score.getScore() + " | 등급 = " + score.getGrade());
                }
            }
            System.out.println("등급 조회 성공!");

        }else { // 수강중인 과목이 아닐경우
            System.out.println("수강 중인 과목이 아닙니다.");
        }

    }


    private static String getInputSubjectId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\n관리할 과목의 번호를 입력하시오 : ");
        return sc.next();
    }

}
