package pl.szop.andrzejshop.data.criteria;

public class Filter {

    public enum Option{
        EQUAL,
        IN,
        LESS,
        GREATER
    }

    private String mField;
    private Object mValue;
    private Option mOption;

    public Filter(String field, Object value, Option option){
        mField = field;
        mValue = value;
        mOption = option;
    }

    public String getField(){
        return mField;
    }

    public Object getValue(){
        return mValue;
    }

    public String getStringValue(){
        assert mValue instanceof String;
        return (String)mValue;
    }

    public Double getDoubleValue() {
        assert mValue instanceof Double;
        return (Double)mValue;
    }

    public Long getLongValue() {
        assert mValue instanceof Long;
        return (Long)mValue;
    }

    public Option getOption(){
        return mOption;
    }
}
