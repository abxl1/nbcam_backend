import java.util.ArrayList;

public class Library {

    // 속성 - 책 목록
    private ArrayList<Book> books;

    // 생성자 - 책 목록 초기화
    public Library() {
        books = new ArrayList<>();
    }

    // 책 추가
    public void addBook(Book book) {
        books.add(book);
        System.out.println(book.getTitle() + "을 추가했습니다.");
    }

    // 책 제거
    public void removeBook(Book book) {
        books.remove(book);
        System.out.println(book.getTitle() + "을 삭제했습니다.");
    }

    // 책 이름으로 검색
    // 책 목록 불러오기 -> 책이 있는지 체크 -> 있다면 출력
    public void getBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                System.out.println(title + "가 있습니다.");
            }
        }
    }

    // 모든 책 조회
    public void getAllBooks() {
        for (Book book : books) {
            System.out.println("제목 : " + book.getTitle() + " / 작가 : " + book.getAuthor());
        }
    }

    // 책 대여
    // 책을 찾음 -> 책이 있는지 체크 -> 대여 가능한지 체크 -> 대여하기(true -> false)
    public void bookRent(Book book) {
        for (Book b : books) {
            if (book.getTitle().equals(b.getTitle()) && book.getIsAvailable()) {
                System.out.println("도서를 대여했습니다.");
                book.setIsAvailable();
            }
        }
    }

// 대여와 반납은 대여 상태에 따른 if-else문으로 바꿀 수 있는지?

    // 책 반납
    // 책을 찾음 -> 책이 있는지 체크 -> 대여 가능한지 체크 -> 대여하기(false -> true)
    public void bookServe(Book book) {
        for (Book b : books) {
            if (book.getTitle().equals(b.getTitle()) && !book.getIsAvailable()) {
                    System.out.println("도서를 반납했습니다.");
                    book.setIsAvailable();
            }
        }
    }
}
