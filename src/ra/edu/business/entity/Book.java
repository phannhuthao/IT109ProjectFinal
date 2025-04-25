package ra.edu.business.entity;

public class Book {
    private int id;
    private String title;
    private String author;
    private int publisherYear;
    private int quantity;
    private String category;

    public Book() {}
    public Book(int id, String title, String author, int publisherYear, int quantity, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisherYear = publisherYear;
        this.quantity = quantity;
        this.category = category;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public int getPublisherYear() {
        return publisherYear;
    }
    public void setPublisherYear(int publisherYear) {
        this.publisherYear = publisherYear;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
