package library.entities;

public class Book {

    private final int id;
    private final String title;
    private final String author;
    private boolean available;
    private Integer borrowedReaderId;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.available = true;
        this.borrowedReaderId = null;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public Integer getBorrowedReaderId() {
        return borrowedReaderId;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBorrowedReaderId(Integer borrowedReaderId) {
        this.borrowedReaderId = borrowedReaderId;
    }
}
