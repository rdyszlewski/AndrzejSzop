package pl.szop.andrzejshop.data.criteria;

import android.os.Parcel;
import android.os.Parcelable;

public class Filter implements Parcelable {

    protected Filter(Parcel in) {
        mField = in.readString();
        int type = in.readInt();
        mType = Type.values()[type];
        switch (mType){
            case STRING:
                mValue = in.readString();
                break;
            case LONG:
                mValue = in.readLong();
                break;
            case DOUBLE:
                mValue = in.readDouble();
                break;
        }
        int option = in.readInt();
        mOption = Option.values()[option];
    }

    public static final Creator<Filter> CREATOR = new Creator<Filter>() {
        @Override
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        @Override
        public Filter[] newArray(int size) {
            return new Filter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mField);
        dest.writeInt(mType.getValue());
        switch (mType){
            case STRING:
                dest.writeString((String) mValue);
                break;
            case LONG:
                dest.writeLong((Long) mValue);
                break;
            case DOUBLE:
                dest.writeDouble((Double) mValue);
                break;
        }
        dest.writeDouble(mOption.getValue());
    }

    public enum Option{
        EQUAL(0),
        IN(1),
        LESS(2),
        GREATER(3);

        private int value;

        Option(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    }

    public enum Type{
        STRING(0),
        LONG(1),
        DOUBLE(2),
        NULL(3);

        private int value;

        Type(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    }

    private String mField;
    private Object mValue;
    private Option mOption;
    private Type mType;

    public Filter(String field, Object value, Option option){
        mField = field;
        mValue = value;
        mOption = option;

        setType(value);
    }

    private void setType(Object value){
        if(value == null){
            mType = Type.NULL;
            return;
        }
        if(value instanceof String){
            mType = Type.STRING;
        } else if(value instanceof Long){
            mType = Type.LONG;
        } else if(value instanceof Double){
            mType = Type.DOUBLE;
        }
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
