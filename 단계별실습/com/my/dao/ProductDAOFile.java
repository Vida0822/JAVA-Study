package com.my.dao;

import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.exception.RemoveException;
import com.my.vo.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A) 파일의 내용을 멤버 변수 list로 관리+프로그램 종료 시 리스트 반영
 * -> 해당 프로그램이 실행되는 동안 외부에서 해당 변경 내용을 볼 수 없음 (Transaction 원칙 위반, 수정/조회/삭제가 여러번 일어날 수 있는데 그 변경 사항은 각 트랜잭션 작업 끝날 때마다 보여야함)
 * B) 각 메서드마다 파일 열기/쓰기/닫기 -> 리소스 낭비 but 파일 <-> 자바 프로그램 간 정보 연동 실시간으로 (수정/조회/삭제 시 파일에 실시간으로 반영됨)
 */

/**
 * DataAccessObject
 * 저장소는 List를 활용한다
 */
public class ProductDAOFile implements ProductDAO{
    private File file;     // Meta 클래스 : Object(객체), Class (클래스), File(파일)

    /**
     * 사용할 파일이 기본 경로는 src/com/my
     * 파일명은 products.txt이다
     */
    public ProductDAOFile() {
        this("src/com/my", "product.txt");
    }

    /**
     * 파일이 없다면 파일을 만든다
     * @param path 파일의 경로
     * @param fileName 파일명
     */
    public ProductDAOFile(String path, String fileName) {
        file = new File(path, fileName);
        if(!file.exists()){ // 파일이 없으면
            try {
                file.createNewFile(); // 파일 새로 생성
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 파일의 내용을 List에 담아 반환한다 ( -> Override 한 메서드들이 공통적으로 이용하는 기능으로 동일하게 호출하기 위함)
     * @return List 파일이 없거나 파일에 상품이 없으면 size가 0인 List가 반환된다
     */
    private List<Product> getList() throws IOException{
        BufferedReader br = null;
        List<Product> products = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));
            // FileReader 단독으로는 readLine(), 불가능 -> 줄 단위 읽으려면 BufferedReader 필수
            String line = null;
            while((line = br.readLine()) != null){
                String []arr = line.split(":");
                products.add(new Product(arr[0], arr[1], Integer.parseInt(arr[2])));
            }
            return products;
        } finally{
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * List를 파일로 저장한다
     * @param products 상품들
     */
    private void saveFile(List<Product> products) throws IOException{
        FileWriter fw = null; //줄단위로 쓰기 write
        try {
            fw = new FileWriter(file);
            // FileWriter만으로도 줄 단위 쓰기 가능 (BufferedWriter 필요 없음)

            for(Product p: products){
                fw.write(p.getProdNo()+":" + p.getProdName() + ":" + p.getProdPrice());
                fw.write("\n");
            }
        } finally{
            if(fw != null){
                try {
                    fw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void insert(Product product) throws AddException {
        try {
            List<Product> products = getList(); //1. 파일 -> List
            for (int i = 0; i < products.size(); i++) {
                if (product.equals(products.get(i))) {
                    throw new AddException("이미 저장된 상품입니다");
                }
            }
            products.add(product);//2. 상품을 List에 추가한다
            saveFile(products); //3. List ->파일
        }catch (IOException e){
            throw new AddException(e.getMessage());
        }
    }

    @Override
    public List<Product> selectAllList() throws FindException {
        try {
            return getList(); //파일 -> List
        }catch(IOException e){
            throw new FindException(e.getMessage());
        }
    }

    @Override
    public Product[] selectAllArray() throws FindException {
//        return (Product[])products.toArray(); //??
        try{
            List<Product> products = getList(); //파일 -> List
            return products.toArray(new Product[products.size()]);
        }catch(IOException e){
            throw new FindException(e.getMessage());
        }
    }

    @Override
    public Product selectByProdNo(String prodNo) throws FindException{
        try{
            List<Product> products = getList(); // 파일 -> List
            for(Product p: products){  //상품번호에 해당하는 상품을 반환
                if(p.getProdNo().equals(prodNo)){
                    return p;
                }
            }
            return null;
        }catch(IOException e){
            throw new FindException(e.getMessage());
        }
    }

    @Override
    public int update(Product product) throws ModifyException {
        int updateCnt = 0;
        try {
            List<Product> products = getList(); // 1. 파일 -> List
            for (Product p : products) {        // 2. List에서 상품찾아 수정
                if (p.equals(product)) {
                    if (product.getProdName() != null) {
                        p.setProdName(product.getProdName());
                        updateCnt++;
                    }
                    if (product.getProdPrice() > 0) {
                        p.setProdPrice(product.getProdPrice());
                        if (updateCnt == 0) {
                            updateCnt++;
                        }
                    }
                }
            }
            saveFile(products); // 3. 수정된 내용이 담겨있는 List -> 파일
        }catch (IOException e){
            throw new ModifyException(e.getMessage());
        }
        return updateCnt;
    }

    @Override
    public int delete(String prodNo) throws RemoveException{
        int deleteCnt = 0;
//        for(Product p: products){ //ConcurrentModificationException발생
//            if(p.getProdNo().equals(prodNo)){
//                products.remove(p);
//                deleteCnt++;
//            }
//        }
        try {
            List<Product> products = getList(); //1. 파일 -> List
            boolean flag = products.remove(new Product(prodNo, null, 0)); //2. 상품 삭제
            if (flag) {
                deleteCnt++;
            }
            saveFile(products); // 3. 수정된 내용이 담겨있는 List -> 파일
            return deleteCnt;
        }catch (IOException e){
            throw new RemoveException(e.getMessage());
        }
    }
}