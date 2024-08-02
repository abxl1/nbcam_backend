
import model.Score;
import model.Subject;

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
        String studentId = getInputStudentId();




        System.out.println("회차별 등급을 조회합니다...");















        System.out.println("\n등급 조회 성공!");
    }


        private static String getInputSubjectId() {
            Scanner sc = new Scanner(System.in);
            System.out.print("\n관리할 과목의 번호를 입력하시오 : ");
            return sc.next();
        }

}
