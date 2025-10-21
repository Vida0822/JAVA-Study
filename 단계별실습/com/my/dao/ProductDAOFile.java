package com.my.dao;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOFile implements ProductDAO{
    /*
    ProductDAO인터페이스를 구현한 ProductDAOFile클래스를 완성합니다
    파일저장소 : C:\scsa202510\products.txt
    파일의 구성
    C0001:아메리카노:1000
    C0002:아이스아메리카노:1000
    C0004:아이스라테:1500
    C0003:라테:1500
     */

    private String fileName ;

    public ProductDAOFile(String fileName) {
        this.fileName = fileName ;

        File file = new File(fileName) ;
        try {
            file.createNewFile() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(Product product) throws AddException {
        List<Product> products = selectAllList() ;
        for (int i = 0; i < products.size(); i++) {
            if (product.equals(products.get(i))) {
                throw new AddException("이미 저장된 상품입니다.") ;
            }
        }

        FileWriter fw = null ;
        try {
            fw = new FileWriter(fileName, true) ;
            fw.write(product.getProdNo()+":"+product.getProdName()+":"+product.getProdPrice());
            fw.write("\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    } // insert

    @Override
    public List<Product> selectAllList() {
        List<Product> products = new ArrayList<>() ;
        FileReader fr = null;
        try {
            fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(new FileReader(fileName)) ;
            String line = null ;
            while((line = br.readLine()) != null){
                String []inputs = line.split(":") ;
                String prodNo = inputs[0] ;
                String prodName = inputs[1] ;
                int prodPrice = Integer.parseInt(inputs[2]) ;
                products.add(new Product(prodNo, prodName, prodPrice)) ;
            }
            return products ;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    } // selectAllList

    @Override
    public Product[] selectAllArray() throws FindException {
        return new Product[0];
    }

    @Override
    public Product selectByProdNo(String prodNo) throws FindException {
        List<Product> products = selectAllList() ;
        for(Product p: products){
            if(p.getProdNo().equals(prodNo)){
                return p;
            }
        }
        throw new FindException("찾으시는 상품이 없습니다.") ;
    }

    @Override
    public int update(Product product) throws ModifyException {
        List<Product> products = selectAllList();
        boolean updated = false;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProdNo().equals(product.getProdNo())) {
                products.set(i, product); // 수정된 상품으로 교체
                updated = true;
                break;
            }
        }

        if (!updated) {
            throw new ModifyException("수정할 상품이 존재하지 않습니다.");
        }

        // 수정된 리스트를 파일에 전체 저장
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Product p : products) {
                bw.write(p.getProdNo() + ":" + p.getProdName() + ":" + p.getProdPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1; // 성공 시 1 반환
    }


    @Override
    public int delete(String prodNo) throws RemoveException {
        List<Product> products = selectAllList();
        boolean removed = false;

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProdNo().equals(prodNo)) {
                products.remove(i);
                removed = true;
                break;
            }
        }

        if (!removed) {
            throw new RemoveException("삭제할 상품이 존재하지 않습니다.");
        }

        // 남은 리스트를 파일에 전체 저장
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Product p : products) {
                bw.write(p.getProdNo() + ":" + p.getProdName() + ":" + p.getProdPrice());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return 1; // 성공 시 1 반환
    }

    @Override
    public void terminate() {
        File file = new File(fileName);
        if(file.exists()){
            file.delete() ;
        }
    }
}
