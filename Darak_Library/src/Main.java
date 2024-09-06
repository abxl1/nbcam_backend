public class Main {
    public static void main(String[] args) {
        Library library = new Library(); // 도서관 객체 생성
        System.out.println("도서관의 객체 생성이 완료되었습니다.");


        Book bookA = new Book("도서A", "authorA", true); // 책 객체 생성
        Book bookB = new Book("도서B", "authorB", true);
        Book bookC = new Book("도서C", "authorC", true);
        Book bookD = new Book("도서D", "authorD", true);
        Book bookTest = new Book("test", "authorTest", true);
        System.out.println("책의 객체 생성이 완료됐습니다.");

        library.addBook(bookA); // 도서관에 책 추가
        library.addBook(bookB);
        library.addBook(bookC);
        library.addBook(bookD);
        library.addBook(bookTest);
        System.out.println("도서관에 책 추가가 완료되었습니다.");

        library.removeBook(bookD); // 도서관에서 책 삭제

        library.getAllBooks(); // 도서관의 모든 책 출력
        System.out.println("도서관의 모든 책이 출력되었습니다.");

        library.getBook("도서A");

        // 도서관에서 책 대여
        // !isAvailable 상태로 변경
        library.bookRent(bookA);

        // 도서관에서 책 반납
        // !isAvailable 상태로 변경
        library.bookServe(bookA);

    }
}