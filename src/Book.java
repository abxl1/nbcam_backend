public class Book {

    // 속성
    private String title;
    private String author;
    private boolean isAvailable;

    // 생성자
    public Book(String title, String author, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    // 제목 getter 메서드
    public String getTitle() {
        return title;
    }

    // 저자 getter 메서드
    public String getAuthor() {
        return author;
    }

    // 책 대여여부 조회
    public boolean getIsAvailable() {
        return isAvailable;
    }

    // 책 대여여부 설정
    // 대여했다면 true, 하지 않았다면 false
    public void setIsAvailable() {
        isAvailable = !isAvailable;
    }

    // 책 상세 정보 출력
    public void getBookDescription() {
        System.out.println("책 제목 : " + title + "/ 책 저자 : " + author);
    }
}
