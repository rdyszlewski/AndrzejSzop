package pl.szop.andrzejshop.data.criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Criteria {

    private String mText;
    private Sort mSort;
    private Map<String, Filter> mFilters;

    public Criteria() {
        mFilters = new HashMap<>();
    }

    public String getText(){
        if(mText == null || mText.isEmpty()){
            return null;
        }
        return mText;
    }

    public Collection<Filter> getFilters() {
        return mFilters.values();
    }

    public Filter getFilter(String name) {
        return mFilters.get(name);
    }

    public boolean hasText(){
        return mText != null && !mText.isEmpty();
    }

    public Sort getSort(){
        return mSort;
    }

    public void setSorting(Sort sort){
        this.mSort = sort;
    }

    public void setText(String text){
        mText = text;
    }

    public boolean isEmpty(){
        return !hasConditions() && !hasSorting();
    }

    public boolean hasSorting(){
        return getSort() != null;
    }

    public boolean hasConditions() {
        return hasText() || hasFilters();
    }

    public boolean hasFilters(){
        return !mFilters.isEmpty();
    }


}