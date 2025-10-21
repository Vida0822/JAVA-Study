package com.my.vo;

import java.io.Serializable;
import java.util.Objects;

/**
 * ValueObject: 값 객체
 */
public class Product implements Serializable { // extends Object
    private String prodNo ;
    private String prodName ;
    private int prodPrice;
    private boolean removed ;

    public Product(String prodNo, String prodName, int prodPrice) {
        this.prodNo = prodNo ;
        this.prodName = prodName ;
        this.prodPrice = prodPrice ;
        this.removed = false ;
    }

    // getter, setter
    public String getProdNo() {
        return prodNo;
    }

    public void setProdNo(String prodNo) {
        this.prodNo = prodNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(int prodPrice) {
        this.prodPrice = prodPrice;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    /**
     * 현재 객체와 매개변수 객체의 상품 번호가 같으면 true를 반환, 다르면 false를 반환
     * 그외(매개변수 객체가 null인 경우,
     *      매개변수 객체가 Product가 아닌 경우,
     *      매개변수 객체가 Product이고 상품번호가 다른 경우)는 false를 반환
     * @param obj   the reference object with which to compare.
     * @return
     */
//    @Override // alt+insert : 메서드 재정의
//    public boolean equals(Object obj) {
//        if(obj == null) return false ;
//        if(!(obj instanceof Product)) return false ;
//        if(obj instanceof Product){
//            ((Product) obj).getprodNo() : Object가 getprodNo()가 없기 때문에 다운캐스팅해서 비교
//            if(this.prodNo.equals(((Product) obj).getprodNo())){
//                return false ;
//            }
//            return true ;
//        }
//    }
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Product product)) return false;
        return Objects.equals(prodNo, product.prodNo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(prodNo);
    }


    @Override
    public String toString() {
        return "상품번호: "+prodNo+", "
                +"상품명: "+prodName+", "
                +"가격: "+prodPrice ;
    }

    // Serializable : 구현할 메서드 없음
}
