package com.my.dao;

import com.my.exception.AddException;
import com.my.vo.Product;

import java.sql.SQLOutput;
import java.util.List;

/**
 * DataAccessObject: 자료 처리용 객체
 */
public class ProductDAOArray implements ProductDAO{
    public Product []products ; // 저장소
    private int totalCnt = 0; // 저장소에 저장된 실제 상품 수

    public ProductDAOArray(){}

    public ProductDAOArray(int size){
        this.products = new Product[size] ;
    }

    @Override
    public void insert(Product product) throws AddException{
        for(int i = 0 ; i < totalCnt ; i++){
            if(product.equals(products[i])){
//                System.out.println("이미 저장된 상품입니다.");
//                return ;
                throw new AddException("이미 저장된 상품입니다.") ;
            }
        }
        products[totalCnt] = product ;
        totalCnt ++;
        System.out.println("상품이 추가되었습니다.");
        return ;
    }

    @Override
    public Product[] selectAllArray() {
        Product[] newProducts = new Product[totalCnt] ;
        System.arraycopy(products, 0, newProducts, 0, totalCnt);
        return newProducts ;
    }

    @Override
    public Product selectByProdNo(String prodNo) {
        return null;
    }

    @Override
    public List<Product> selectAllList() {
        return List.of();
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public int delete(String prodNo){
        for(int i = 0 ; i < totalCnt ; i++){
            if(prodNo.equals(products[i].getProdNo())){
                products[i].setRemoved(true);
                System.out.println("상품이 삭제되었습니다.");
                return 1 ;
            }
        }
        System.out.println("삭제할 상품이 없습니다.");
        return 0 ;
    }

    @Override
    public void terminate() {

    }
}

/**
 * [상속]
 * - 자바는 단일 상속
 * - 설계 시 IS A 관계 또는 Kind Of 관계가 성립해야한다 (원은 도형이다, 사각형은 도형이다)
 * - class 사원 extends 사람 (O)
 * - class 학생 extends 사람 (O)
 * - class 계좌 extends 사람 (X) -> 사람 타입의 멤버 변수에 계좌 정보 포함 (has-a 관계)
 *
 * [최상위 클래스 :Object]
 * - 대표 메서드 : equals(), hashCode(), toString()
 * ex. equals() : x문자열과 y문자열이 같은 경우 true 반환 (String, StringBuilder)
 * String: Object의 하위 클래스, equals() 재정의 됨 vs StringBuilder: Object의 하위 클래스, equals() 재정의 안됨
 *
 * ㄴ 다형성
 * Object obj = new String("a");  -> upcasting (자동으로 됨)
 * String str = (String)obj ;  -> downcasting (강제 형변환)
 *
 * [Override()]
 * : 메서드의 다양성
 *
 */








