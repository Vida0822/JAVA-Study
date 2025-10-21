package com.my.dao;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Product;

import java.util.List;

public interface ProductDAO {
    /**
     * 저장소에 상품을 추가한다
     *
     * @param product 저장할 상품
     * @throws AddException 저장할 상품의 상품번혹다 이미 저장소에 있다면 "이미 저장된 상품입니다" 메세지를 갖는 예외 발생한다.
     */
//    public void insert(Product product) throws Exception; ERROR : 부모쪽에서 throws 한 예외만 자식쪽에서 throws 예외 할 수 있다.
    public void insert(Product product) throws AddException;


    /**
     * 저장소의 모든 상품을 반환한다
     * @return List타입으로 반환한다
     * @throws FindException 상품의 조회가 실패되면 예외발생한다.
     */
    public List<Product> selectAllList() throws FindException;

    /**
     * 저장소의 모든 상품을 반환한다
     * @return 배열타입으로 반환한다
     * @throws FindException 상품의 조회가 실패되면 예외발생한다.
     */
    public Product[] selectAllArray() throws FindException ;

    /**
     * 상품번호에 해당하는 상품을 반환한다
     * 상품이 없으면 null을 반환한다
     * @param prodNo 상품번호
     * @throws FindException 상품의 조회가 실패되면 예외발생한다.
     * @return 상품
     */
    public Product selectByProdNo(String prodNo) throws FindException;

    /**
     * 상품을 수정한다
     * @param product 변경할 상품정보가 담겨있는 상품
     * @return 수정된 상품 수
     * @throws ModifyException
     */
    public int update(Product product) throws ModifyException;

    /**
     * 상품을 삭제한다
     * @param prodNo 삭제할 상품번호
     * @return 삭제된 상품 수
     * @throws RemoveException
     */
    public int delete(String prodNo) throws RemoveException;
}