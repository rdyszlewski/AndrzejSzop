package pl.szop.andrzejshop;

import android.app.Application;

import java.util.Arrays;
import java.util.List;

import pl.szop.andrzejshop.models.Book;
import pl.szop.andrzejshop.models.BookDetails;
import pl.szop.andrzejshop.models.BooksImages;
import pl.szop.andrzejshop.models.DaoMaster;
import pl.szop.andrzejshop.models.DaoSession;
import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.utils.ImageUtils;

public class MyApplication extends Application {

    private DaoSession mDaoSession;

    // method starts when the application stars
    @Override
    public void onCreate(){
        super.onCreate();

        mDaoSession = new DaoMaster(new DaoMaster.DevOpenHelper(this, "products_db").getWritableDb()).newSession();

        if(isDatabaseEmpty()){
            insertDataToDb();
        }
    }

    private void insertDataToDb() {
        Book product1 = new Book("Władca pierścieni", "Tolkien", "Fantastyka", 30.99);
        Book product2 = new Book("Hyperion", "Simmons", "Science fiction", 99.99);
        byte[] image = ImageUtils.getBytesFromResource(getApplicationContext(), R.drawable.game);
        product1.setCover(image);
        product2.setCover(image);

        mDaoSession.getBookDao().insert(product1);
        mDaoSession.getBookDao().insert(product2);

        // saving details
        BookDetails details = new BookDetails();
        details.setDescription("“Władca Pierścieni” to niesamowita przygoda mówiąca o przyjaźni, poświęceniu i walce o dobro. Przenieś się w świat porywającego Śródziemia, miejsca, gdzie można spotkać nie tylko ludzi, lecz dumne krasnoludy, piękne elfy i, co najważniejsze, dzielne hobbity. Poznaj historię niepozornego niziołka, Froda Bagginsa, na którego barki niespodziewanie spada ogromna odpowiedzialność. Dzięki życzliwości swoich towarzyszy podejmuje się niebezpiecznego zadania i zabiera nas do magicznego świata.");
        Image image1 = new Image();
        image1.setMImage(image);
        Image image2 = new Image();
        image2.setMImage(image);
        Image image3 = new Image();
        image3.setMImage(image);

        mDaoSession.getImageDao().insert(image1);
        mDaoSession.getImageDao().insert(image2);
        mDaoSession.getImageDao().insert(image3);

        // to nie działa
        List<Image> images = Arrays.asList(image1, image2, image3);
        details.setImages(images);

        details.setBook(product1);
        mDaoSession.getBookDetailsDao().insert(details);

        BooksImages booksImages = new BooksImages();
        booksImages.setBook(details.getId());
        booksImages.setImage(image1.getId());
        mDaoSession.getBooksImagesDao().insert(booksImages);
    }

    private boolean isDatabaseEmpty() {
        return mDaoSession.getBookDao().loadAll().size() == 0;
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
