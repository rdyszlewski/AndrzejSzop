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

    private SharedPreferences mPreferences;

    private static MyApplication mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        setDataProvider(OFFLINE_MODE);

        if(isFirstRun()){
            initApplication();
        }

        mInstance = this;
    }

    private void initApplication(){
        DatabaseInitializer.init(((ProductDAO)mDataProvider).getDaoSession(),getApplicationContext());
        mPreferences.edit().putBoolean("firstrun", false).apply();
    }

    public static MyApplication instance(){
        assert mInstance != null;
        return mInstance;
    }

    public void setDataProvider(int mode){
        mDataProvider = createDataProvider(mode);
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

    private boolean isFirstRun(){
        return mPreferences.getBoolean("firstrun", true);
    }
}
