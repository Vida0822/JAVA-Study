package com.my;

import com.my.dao.ProductDAO;
import com.my.dao.ProductDAOArray;
import com.my.dao.ProductDAOList;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.RemoveException;
import com.my.vo.Product;

import java.util.List;
import java.util.Scanner;

public class ProductUser {
    private ProductDAO dao;
    static private Scanner sc = new Scanner(System.in);;
    public ProductUser(){
//        dao = new ProductDAOArray(5);
        dao = new ProductDAOList(5);
    }

    public void add(){
        System.out.println(">>상품추가작업<<");
        System.out.print("상품번호:");
        String prodNo = sc.nextLine(); // Enter 값까지 다 읽어서 문자 받아옴
        System.out.print("상품명:");
        String prodName = sc.nextLine();
        System.out.print("가격:");

        int prodPrice = Integer.parseInt(sc.nextLine());
        try{
            dao.insert(new Product(prodNo, prodName, prodPrice));
        }catch (AddException e){
            System.out.println(e.getMessage());
        }
    }

    public void findAll(){
        System.out.println(" >>상품전체조회작업<<");
//        Product[] all = dao.selectAllArray();
//        for(Product p: all) {
//            System.out.println(p);
//        }
        List<Product> all = null;
        try {
            all = dao.selectAllList();
            for(Product p : all){
                System.out.println(p);
            }
        } catch (FindException e) {
            System.out.println(e.getMessage());
        }
    }

    public void remove(){
        System.out.println(">>상품삭제작업<<");
        System.out.print("상품번호:");
        String prodNo = sc.nextLine();
        int deleteCnt = 0;
        try {
            deleteCnt = dao.delete(prodNo);
            System.out.println("삭제된 상품 수:"+deleteCnt);
        } catch (RemoveException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        ProductUser user = new ProductUser();
        String opt;
        do {
            System.out.println("작업을 선택하세요: 1-상품추가, 2-상품전체조회, 3-상품삭제, 9-종료");
            System.out.print("작업구분 : ");
            opt = sc.nextLine();
            switch (opt) {
                case "1":
                    user.add();
                    break;
                case "2":
                    user.findAll();
                    break;
                case "3":
                    user.remove();
                    break;
                case "9":
                    System.out.println("작업을 종료합니다");
            }
        }while(!opt.equals("9"));
    }
}