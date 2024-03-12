package com.example.yoyo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName goods
 */
@Data
public class Goods implements Serializable {
    /**
     * "商品id"
     */
    private Integer id;

    /**
     * "商品名称"
     */
    private String name;

    /**
     * "商品的单价"
     */
    private Double price;

    /**
     * "商品的描述"
     */
    private String intro;

    /**
     * "封面图片的url地址"
     */
    private String cover;

    /**
     * "商品细节图片1的url地址"
     */
    private String image1;

    /**
     * "商品细节图片2的url地址"
     */
    private String image2;

    /**
     * "商品库存数量"
     */
    private Integer stock;

    /**
     * "商品分类的id，关联type表"
     */
    private Integer typeId;

    /**
     * "商品的状态，1表示在售，0表示删除"
     */
    private Integer status;

    private Type type;

    private boolean topToday ;
    private boolean topHot ;
    private boolean topNew;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Goods other = (Goods) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
            && (this.getIntro() == null ? other.getIntro() == null : this.getIntro().equals(other.getIntro()))
            && (this.getCover() == null ? other.getCover() == null : this.getCover().equals(other.getCover()))
            && (this.getImage1() == null ? other.getImage1() == null : this.getImage1().equals(other.getImage1()))
            && (this.getImage2() == null ? other.getImage2() == null : this.getImage2().equals(other.getImage2()))
            && (this.getStock() == null ? other.getStock() == null : this.getStock().equals(other.getStock()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getIntro() == null) ? 0 : getIntro().hashCode());
        result = prime * result + ((getCover() == null) ? 0 : getCover().hashCode());
        result = prime * result + ((getImage1() == null) ? 0 : getImage1().hashCode());
        result = prime * result + ((getImage2() == null) ? 0 : getImage2().hashCode());
        result = prime * result + ((getStock() == null) ? 0 : getStock().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", price=").append(price);
        sb.append(", intro=").append(intro);
        sb.append(", cover=").append(cover);
        sb.append(", image1=").append(image1);
        sb.append(", image2=").append(image2);
        sb.append(", stock=").append(stock);
        sb.append(", typeId=").append(typeId);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}