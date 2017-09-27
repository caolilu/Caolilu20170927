package com.example.cll.caolilu20170927;

/**
 * Created by cll on 2017/9/27.
 */
public class bean {
    private String name;
    private String shi;
    private String image;

    @Override
    public String toString() {
        return "bean{" +
                "name='" + name + '\'' +
                ", shi='" + shi + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
