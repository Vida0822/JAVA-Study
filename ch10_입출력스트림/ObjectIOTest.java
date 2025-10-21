package day05;

import com.my.vo.Product;

import java.io.*;

public class ObjectIOTest {
    public static void write(String fileName){
        /*
        목적지: fileName
        파일출력스트림: FileOutputStream
        가공출력스트림: ObjectOutputStream
         */
        ObjectOutputStream oos = null ;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName)) ;
            Object obj = new java.util.Date(); // 연월~시분초
//            System.out.println(obj); // Date.toString()
            oos.writeObject(obj);
            // ыsrjava.util.Datehj?KYtxp?@q}x : heap 메모리 공간에 있던 객체 정보를 파일에 일렬로 저장됨 ('직렬화')
            // ㄴ Datatype + 멤버변수 순서대로
            // 객체 - 직렬화 -> 파일 출력
            // ※ NotSerializableException
            // : Some object to be serialized does not implement the java.io.Serializable interface.
            // ㄴ 특정 객체가 Serializable interface 구현 안하면 오류 (직렬화 불가) : Object 클래스가 직렬화 구현 안되어있기 때문에 직접 직렬화 구현해줘야함
//            Object obj2 = new Object() ;
//            oos.writeObject(obj2) ;
             Product p = new Product("C0001", "아메리카노", 1000) ;
             oos.writeObject(p); // '객체 얼리기'

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    } // write

    public static void read(String fileName){
        ObjectInputStream ois = null ;
        try {
            ois = new ObjectInputStream(new FileInputStream(fileName)) ;
            // 객체 역직렬화(객체 녹이기)
            System.out.println(ois.readObject()); // Tue Oct 21 15:06:30 KST 2025 (Date)
            System.out.println(ois.readObject()); // 상품번호: C0001, 상품명: 아메리카노, 가격: 1000 (Product)
            // throw ClassNotFoundException: 담아줄 객체(클래스 타입)이 없을 때 예외 발생

            // [직렬화 제외]
            // 1. static 변수 : 객체 얼리기 안됨 (왜냐하면 객체 직렬화 자체가 heap 메모리 안의 객체를 얼리는것이므로 stack 영역의 static 변수는 직렬화 X)
            // 2. transient 예약어 : 객체 직렬화 제외  ex. transient private int prodPrice
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if(ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    } // read

    public static void main(String[] args) {
        String fileName = "src/day05/a.ser" ;
        write(fileName) ;
        read(fileName) ;
    } // main
}
