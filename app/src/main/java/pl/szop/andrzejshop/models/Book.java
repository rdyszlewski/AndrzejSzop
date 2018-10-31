package pl.szop.andrzejshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "books")
public class Book extends Product {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "author")
    private String author;

    @Property(nameInDb = "category")
    private String category;

    @Property(nameInDb = "price")
    private Double price;

    @Property(nameInDb = "pomotional_price")
    private Double promotionalPrice;

    @Property(nameInDb = "cover")
    private byte[] cover;

    public Book() {

    }

    public Book(String title, String autor, String category, Double price) {
        setTitle(title);
        setAuthor(autor);
        setCategory(category);
        setPrice(price);
    }

    @Generated(hash = 1986047379)
    public Book(Long id, String title, String author, String category, Double price,
                Double promotionalPrice, byte[] cover) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.price = price;
        this.promotionalPrice = promotionalPrice;
        this.cover = cover;
    }

    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return this.price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }


    public Double getPromotionalPrice() {
        return this.promotionalPrice;
    }
    public void setPromotionalPrice(Double promotionalPrice) {
        this.promotionalPrice = promotionalPrice;
    }

    public byte[] getCover() {
        return this.cover;
    }
    public void setCover(byte[] cover) {
        this.cover = cover;
    }
}
