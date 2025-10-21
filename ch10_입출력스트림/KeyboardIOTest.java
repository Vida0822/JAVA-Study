package day05;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class KeyboardIOTest {
    public static void main(String[] args) {
        // byte 단위 스트림
        InputStream keyboard = System.in ;  // 'public static final InputStream in'
//        try{
//            int v = keyboard.read(); // aaaaaa -> 97 : 1 byte만 반환함, 가 -> 234 ('가' 중 1 byte만 읽어옴)
//            System.out.println(v+":"+(char)v); // aaaaaa -> 97 : 1 byte만 반환함, 가 -> 234 ('가' 중 1 byte만 읽어옴) -> 출력 시 : ê (byte 단위 읽기 작업 단점: 한글 깨짐)
//        }catch (IOException e){
//            throw new RuntimeException(e) ;
//        }


        // char 단위 스트림
//        InputStreamReader isr = new InputStreamReader(keyboard) ; // InputStreamReader: Filter Stream
//        try {
//            int v = isr.read() ;
//            System.out.println(v+":"+(char)v); // 문자 단위로 읽음(byte 크기 상관X) ex. 44032:가, 12593:ㄱ
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        // 문자 여러개 입력 받기
//        InputStreamReader isr = new InputStreamReader(keyboard) ; // InputStreamReader: Filter Stream
//        int v = -1 ;
//        try {
//            while((v = isr.read()) != -1){
//                System.out.println(v+":"+(char)v); // 문자 단위로 읽음(byte 크기 상관X) ex. 44032:가, 12593:ㄱ
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
        InputStreamReader isr = new InputStreamReader(keyboard) ;
        char []cArr = new char[5] ;
        try {
            int readCnt = -1 ;
            while((readCnt = isr.read(cArr, 0, cArr.length)) != -1){
                for(int i = 0 ; i < readCnt ; i++){
                    System.out.print(cArr[i]);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
