package com.my.dao;

import com.my.exception.AddException;
import com.my.vo.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * DataAccessObject
 * 저장소는 List를 활용한다
 */
public class ProductDAOList implements ProductDAO{
    //private List products;는 요소가 모두 Object타입으로 저장된다
    //Product p = (Product)products.get(0);
    private List<Product> products; //요소가 모두 Product타입으로 저장된다
    //Product p = products.get(0);

    public ProductDAOList() {
        products = new ArrayList<>(); //10개의 index가 만들어짐
    }

    public ProductDAOList(int size) {
        products = new ArrayList<>(size); //size갯수만큼의 index가 만들어짐
    }

    public void insert(Product product) throws AddException {
        for (int i = 0; i < products.size(); i++) {
            if (product.equals(products.get(i))) {
                throw new AddException("이미 저장된 상품입니다.") ;
            }
        }
        products.add(product);
    }

    @Override
    public List<Product> selectAllList() {
        return products;
    }

    @Override
    public Product[] selectAllArray() {
        return (Product[])products.toArray(); //??
    }

    @Override
    public Product selectByProdNo(String prodNo) {
        for(Product p: products){
            if(p.getProdNo().equals(prodNo)){
                return p;
            }
        }
        return null;
    }

    @Override
    public int update(Product product) {
        int updateCnt = 0;
        for(Product p: products){
            if(p.equals(product)){
                //p = product;
                if(product.getProdName() != null) {
                    p.setProdName(product.getProdName());
                    updateCnt++;
                }
                if(product.getProdPrice() > 0) {
                    p.setProdPrice(product.getProdPrice());
                    if(updateCnt==0){   updateCnt++; }
                }
            }
        }
        return updateCnt;
    }

    @Override
    public int delete(String prodNo) {
        int deleteCnt = 0;
//        for(Product p: products){ //ConcurrentModificationException발생
//            if(p.getProdNo().equals(prodNo)){
//                products.remove(p);
//                deleteCnt++;
//            }
//        }
        boolean flag = products.remove(new Product(prodNo, null, 0));
        if(flag){
            deleteCnt++;
        }
        return deleteCnt;
    }
}