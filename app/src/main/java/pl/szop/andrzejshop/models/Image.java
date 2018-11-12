package pl.szop.andrzejshop.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "images")
public class Image implements Parcelable {

    @Id
    private Long id;

    @Property(nameInDb = "product")
    private Long productId;

    @Property(nameInDb = "image")
    private byte[] image;

    @Generated(hash = 146471692)
    public Image(Long id, Long productId, byte[] image) {
        this.id = id;
        this.productId = productId;
        this.image = image;
    }

    @Generated(hash = 1590301345)
    public Image() {
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(productId);
        dest.writeByteArray(image);
    }

    private Image(Parcel in){
        this.id = in.readLong();
        this.productId = in.readLong();
        in.readByteArray(image);
    }
}
