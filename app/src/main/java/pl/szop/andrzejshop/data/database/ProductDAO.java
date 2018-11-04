package pl.szop.andrzejshop.data.database;

import android.app.Application;

import java.util.List;

import pl.szop.andrzejshop.MyApplication;
import pl.szop.andrzejshop.data.Filter;
import pl.szop.andrzejshop.data.IDataProvider;
import pl.szop.andrzejshop.models.BookDetails;
import pl.szop.andrzejshop.models.DaoSession;
import pl.szop.andrzejshop.models.Product;

public class ProductDAO implements IDataProvider {

    private DaoSession mDaoSession;

    public ProductDAO(DaoSession daoSession){
        mDaoSession = daoSession;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public  List<? extends Product> getProducts(Filter filter) {
        Class productClass = ProductDAOSettings.PRODUCT_CLASS;
        if(filter == null || filter.isEmpty()){
            return (List<? extends Product>) mDaoSession.getDao(productClass).loadAll();
        }
        String where = createWhere(filter);
        String[] arguments = getWhereArguments(filter);
        return (List<? extends Product>) mDaoSession.getDao(productClass).queryRaw(where, arguments);
    }

    public List<? extends Product> getProducts(){
        return getProducts(null);
    }

    @Override
    public Product getDetails(Long id) {
        return mDaoSession.getBookDetailsDao().loadDeep(id);
    }

    private String createWhere(Filter filter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WHERE ");
        String[] whereColumns = ProductDAOSettings.SEARCH_COLUMN;
        for(int i=0; i<whereColumns.length; i++){
            stringBuilder.append(whereColumns[i]);
            stringBuilder.append(" LIKE ?");
            if(i != whereColumns.length -1){
                stringBuilder.append(" OR ");
            }
        }

        return stringBuilder.toString();
    }

    private String[] getWhereArguments(Filter filter){
        String[] arguments = new String[ProductDAOSettings.SEARCH_COLUMN.length];
        String filterText = "%"+filter.getText()+"%";
        for(int i=0; i < arguments.length; i++){
            arguments[i] = filterText;
        }

        return arguments;
    }
}
