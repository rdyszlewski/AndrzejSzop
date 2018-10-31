package pl.szop.andrzejshop.models;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "images")
public class Image {

    @Id
    private Long mId;

    @Property(nameInDb = "image")
    private byte[] mImage;

    @Generated(hash = 1907287248)
    public Image(Long mId, byte[] mImage) {
        this.mId = mId;
        this.mImage = mImage;
    }

    @Generated(hash = 1590301345)
    public Image() {
    }

    public Long getId(){
        return mId;
    }

    public void setId(Long id){
        mId = id;
    }

    public byte[] getImage(){
        return mImage;
    }

    public void setImage(byte[] image){
        mImage = image;
    }

    public Long getMId() {
        return this.mId;
    }

    public void setMId(Long mId) {
        this.mId = mId;
    }

    public byte[] getMImage() {
        return this.mImage;
    }

    public void setMImage(byte[] mImage) {
        this.mImage = mImage;
    }
}
