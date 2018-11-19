package pl.szop.andrzejshop.data;

public class Filter {

    private String mText;
    private Sort sort;

    public String getText(){
        if(mText == null || mText.isEmpty()){
            return null;
        }
        return mText;
    }

    public Sort getSort(){
        return sort;
    }

    public void setSorting(Sort sort){
        this.sort = sort;
    }

    public void setText(String text){
        mText = text;
    }

    public boolean isEmpty(){
        return getText() == null && getSort() == null;
    }

    // TODO może nazwa niezbyt odpowiednia
    public boolean hasCondition(){
        return getText() != null ; // TODO dodać filtry
    }

    public boolean hasSorting(){
        return getSort() != null;
    }

}