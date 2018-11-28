package pl.szop.andrzejshop.data.criteria;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

public class Sort implements Parcelable {
    private String name;
    private boolean desc;

    public Sort(String name, boolean desc){
        this.name = name;
        this.desc = desc;
    }

    protected Sort(Parcel in) {
        name = in.readString();
        desc = in.readByte() != 0;
    }

    public static final Creator<Sort> CREATOR = new Creator<Sort>() {
        @Override
        public Sort createFromParcel(Parcel in) {
            return new Sort(in);
        }

        @Override
        public Sort[] newArray(int size) {
            return new Sort[size];
        }
    };

    public String getName(){
        return name;
    }

    public boolean isDesc(){
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sort sort = (Sort) o;
        return desc == sort.desc &&
                Objects.equals(name, sort.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, desc);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeByte((byte) (desc ? 1 : 0));
    }
}
