package pl.szop.andrzejshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "books_images")
public class BooksImages {

    @Id
    private Long id;

    private Long book;
    private Long image;


    @Generated(hash = 1934316833)
    public BooksImages(Long id, Long book, Long image) {
        this.id = id;
        this.book = book;
        this.image = image;
    }
    @Generated(hash = 735433656)
    public BooksImages() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getBook() {
        return this.book;
    }
    public void setBook(Long book) {
        this.book = book;
    }
    public Long getImage() {
        return this.image;
    }
    public void setImage(Long image) {
        this.image = image;
    }

}
