package pl.szop.andrzejshop.data.database;

import java.util.ArrayList;
import java.util.List;

import pl.szop.andrzejshop.data.criteria.Criteria;
import pl.szop.andrzejshop.data.IDataProvider;
import pl.szop.andrzejshop.data.criteria.Filter;
import pl.szop.andrzejshop.data.criteria.Sort;
import pl.szop.andrzejshop.models.DaoSession;
import pl.szop.andrzejshop.models.Favorites;
import pl.szop.andrzejshop.models.Image;
import pl.szop.andrzejshop.models.Product;

public class ProductDAO implements IDataProvider {

    private DaoSession mDaoSession;

    public ProductDAO(DaoSession daoSession){
        mDaoSession = daoSession;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    @Override
    public  List<? extends Product> getProducts(Criteria criteria) {
        Class productClass = ProductDAOSettings.PRODUCT_CLASS;
        // if filter is empty, load all products
        if(criteria == null || criteria.isEmpty()){
            return (List<? extends Product>) mDaoSession.getDao(productClass).loadAll();
        }
        // else create where statement and load data from database
        String where = criteria.hasConditions() ? createWhere(criteria) : "";
        String[] arguments = criteria.hasConditions()? getWhereArguments(criteria) : new String[0];

        if (criteria.hasSorting()){
            where += createOrderBy(criteria);
        }
        return (List<? extends Product>) mDaoSession.getDao(productClass).queryRaw(where, arguments);
    }


    @Override
    public List<? extends Product> getProducts(){
        return getProducts(null);
    }

    @Override
    public Product getDetails(Long id) {
        return ProductDAOSettings.getDetailsDao(mDaoSession).loadDeep(id);
    }

    @Override
    public List<Image> getImages(Long productId) {
        return getDaoSession().getImageDao().queryRaw("WHERE product = ?", new String[]{String.valueOf(productId)});
    }

    @Override
    public boolean isFavorite(Long id) {
        Favorites favorites = getDaoSession().getFavoritesDao().load(id);
        return favorites != null;
    }

    @Override
    public void setFavorite(Long id, boolean favorite) {
        Favorites favorites = new Favorites(id);
        if(favorite){
            // TODO chyba należało by dodać sprawdzanie, czy taka wartość już nie istnieje w bazie danych
            getDaoSession().getFavoritesDao().insert(favorites);
        } else {
            getDaoSession().getFavoritesDao().delete(favorites);
        }
    }

    @Override
    public List<? extends Product> getFavorites() {
        return getDaoSession().getFavoritesDao().loadAll();
    }

    private String createWhere(Criteria filter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WHERE ");

        String filtersConditions = getFilterConditions(filter);
        stringBuilder.append(filtersConditions);
        if(filter.hasText() && !filtersConditions.isEmpty()){
            stringBuilder.append(" AND ");
        }
        stringBuilder.append(getTextConditions());

        return stringBuilder.toString();
    }

    private String getTextConditions(){
        StringBuilder stringBuilder = new StringBuilder();
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

    private String getFilterConditions(Criteria criteria) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean firstCondition = true;
        for(Filter filter : criteria.getFilters()) {
            if(!firstCondition){
                stringBuilder.append(" AND ");
            }
            firstCondition = false;

            stringBuilder.append(filter.getField());
            stringBuilder.append(getOption(filter.getOption()));
            stringBuilder.append("?");
        }
        return stringBuilder.toString();
    }

    private String getOption(Filter.Option option) {
        switch (option){
            case EQUAL:
                return " = ";
            case IN:
                return " IN ";
            case LESS:
                return " < ";
            case GREATER:
                return " > ";
                default:
                throw new IllegalArgumentException("Unknown filter option");
        }
    }

    private String createOrderBy(Criteria filter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ORDER BY ");
        Sort sort = filter.getSort();
        stringBuilder.append(sort.getName());
        if(sort.isDesc()){
            stringBuilder.append(" ").append("DESC");
        }
        return stringBuilder.toString();
    }

    private String[] getWhereArguments(Criteria criteria){
        List<String> arguments = new ArrayList<>();
        if(criteria.hasFilters()) {
            List<String> filterArguments = new ArrayList<>();
            for(Filter filter : criteria.getFilters()) {
                filterArguments.add(filter.getValue().toString());
            }
            arguments.addAll(filterArguments);
        }
        if(criteria.hasText()) {
            int searchArgumentsSize = ProductDAOSettings.SEARCH_COLUMN.length;
            String filterText = "%"+criteria.getText()+"%";
            List<String> searchArguments = new ArrayList<>();
            for(int i=0; i<searchArgumentsSize; i++){
                searchArguments.add(filterText);
            }
            arguments.addAll(searchArguments);
        }

        return arguments.toArray(new String[0]);
    }
}
