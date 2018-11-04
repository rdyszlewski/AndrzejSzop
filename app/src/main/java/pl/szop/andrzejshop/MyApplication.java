package pl.szop.andrzejshop;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

import pl.szop.andrzejshop.data.IDataProvider;
import pl.szop.andrzejshop.data.database.DatabaseInitializer;
import pl.szop.andrzejshop.data.database.ProductDAO;
import pl.szop.andrzejshop.models.Book;
import pl.szop.andrzejshop.models.BookDetails;
import pl.szop.andrzejshop.models.BooksImages;
import pl.szop.andrzejshop.models.DaoMaster;
import pl.szop.andrzejshop.models.DaoSession;
import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.utils.ImageUtils;

public class MyApplication extends Application {

    public static final int OFFLINE_MODE = 0;
    public static final int ONLINE_MODE = 1;

    private IDataProvider mDataProvider;
    private int mCurrentDataMode;

    private SharedPreferences mPreferences;

    private static MyApplication mInstance;

    // method starts when the application stars
    @Override
    public void onCreate(){
        super.onCreate();
        mPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        setDataProvider(OFFLINE_MODE);

        if(isFirstRun()){
            DatabaseInitializer.init(((ProductDAO)mDataProvider).getDaoSession(),getApplicationContext());
            mPreferences.edit().putBoolean("firstrun", false).apply();
        }

        mInstance = this;
    }

    public static MyApplication instance(){
        return mInstance;
    }

    public void setDataProvider(int mode){
        mDataProvider = createDataProvider(mode);
        mCurrentDataMode = mode;
    }

    public IDataProvider createDataProvider(int mode){
        switch (mode){
            case OFFLINE_MODE:
                return createOfflineDataProvider();
                default:
                    throw new IllegalArgumentException();
        }
    }

    private IDataProvider createOfflineDataProvider(){
        DaoSession daoSession = createDaoSession();
        return new ProductDAO(daoSession);
    }

    private DaoSession createDaoSession() {
        return new DaoMaster(new DaoMaster.DevOpenHelper(this, "products_db").getWritableDb()).newSession();
    }

    public IDataProvider getDataProvider() {
        return mDataProvider;
    }

    public static IDataProvider dataProvider(Application application){
        return ((MyApplication)application).getDataProvider();
    }

    private boolean isFirstRun(){
        return mPreferences.getBoolean("firstrun", true);
    }
}
