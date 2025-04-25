package ra.edu.business.entity;

public class BorrowDetails {
    private int borrowId;
    private int bookId;
    private int quantity;

    public BorrowDetails() {}
    public BorrowDetails(int borrowId, int bookId, int quantity) {
        this.borrowId = borrowId;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public int getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(int borrowId) {
        this.borrowId = borrowId;
    }
    public int getBookId() {
        return bookId;
    }
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
