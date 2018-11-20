package pl.szop.andrzejshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

@Entity(nameInDb = "books")
public class Book extends Product {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "author")
    private String author;

    @ToOne(joinProperty = "id")
    private Category category;

    @Property(nameInDb = "price")
    private Double price;

    @Property(nameInDb = "pomotional_price")
    private Double promotionalPrice;

    @Property(nameInDb = "cover")
    private byte[] cover;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1097957864)
    private transient BookDao myDao;

    @Generated(hash = 1372501278)
    private transient Long category__resolvedKey;

    public Book() {

    }

    public Book(String title, String autor, Category category, Double price) {
        setTitle(title);
        setAuthor(autor);
        setCategory(category);
        setPrice(price);
    }

    @Generated(hash = 1903199057)
    public Book(Long id, String title, String author, Double price,
            Double promotionalPrice, byte[] cover) {
        this.id = id;
        this.title = title;
        this.author = author;
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

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1152182047)
    public Category getCategory() {
        Long __key = this.id;
        if (category__resolvedKey == null || !category__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDao targetDao = daoSession.getCategoryDao();
            Category categoryNew = targetDao.load(__key);
            synchronized (this) {
                category = categoryNew;
                category__resolvedKey = __key;
            }
        }
        return category;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1178303271)
    public void setCategory(Category category) {
        synchronized (this) {
            this.category = category;
            id = category == null ? null : category.getId();
            category__resolvedKey = id;
        }
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1115456930)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBookDao() : null;
    }


}
