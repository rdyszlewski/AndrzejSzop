package pl.szop.andrzejshop.data;

public class Filter {

    private String mText;

    public String getText(){
        if(mText == null || mText.isEmpty()){
            return null;
        }
        return mText;
    }

    public void setText(String text){
        mText = text;
    }

    public boolean isEmpty(){
        return getText() == null;
    }
}
