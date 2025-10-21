package day05;

import java.io.*;

public class FileIOTest {
    /**
     * 함수 읽기 함수
     * @param fileName
     * 절대 경로
     * String fileName = "C:\\SCSA_JAVA\\SW\\myJAVA\\basic\\basic\\src\\day05\\a.txt";
     * 상대 경로
     * System.out.println(System.getProperty("user.dir")); // 상대 경로 기준 디렉토리: C:\SCSA_JAVA\SW\myJAVA\basic\basic
     * String fileName = "src/day05/a.txt"; // 그 기준 위치부터 참조해주면 됨
     */
    public static void read(String fileName){

        FileInputStream fis ;
        try {
//            fis = new FileInputStream(fileName) ; // byte 단위 스트림
            FileReader fr = new FileReader(fileName) ; // java는 open 이 없기 때문에 객체 생성만 해주면 자동으로 연결됨
            int v = -1 ;
            while((v=fr.read()) != -1){
                System.out.print((char)v); // 가나다라마 -> ìëíì¸ì : 한글 깨짐 ! (byte 단위로 읽기 때문, 한글을 표현하는 byte는 3byte 필요)
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 함수 쓰기 함수
     * @param fileName
     */
    public static void write(String fileName){
        // Byte 단위 스트림 : FileOutputStream
    //        FileOutputStream fos = null ;
    ////        fos = new FileOutputStream(fileName, true) ;  // 새로운 파일 생성, 기존 파일 이어쓰기
    //        try {
    //            fos = new FileOutputStream(fileName); // 새로운 파일 생성, 기존 파일 덮어쓰기
    //            for(int i = 'A'; i < 'G' ; i++){
    //                fos.write(i);
    //            }
    ////        } catch (IOException e) {
    ////            throw new RuntimeException(e);  -> ERROR : 자식 Exception 객체가 먼저 catch 와야함
    ////      }
    //        }catch (FileNotFoundException e) {
    //            String msg = e.getMessage();
    //            System.out.println(msg); // src\day0\a.txt (지정된 경로를 찾을 수 없습니다)
    //        } catch (IOException e) {
    ////            throw new RuntimeException(e);
    //            String msg = e.getMessage() ;
    //            System.out.println(msg);
    ////        }
    //        }

        // 문자 단위 스트림: FileWriter
        FileWriter fw = null ;
        try {
            fw = new FileWriter(fileName,false) ;
            fw.write("Hello");
            fw.write("안녕하세요");
//            fw.flush(); // 중간 저장됨
            fw.write("\n");
            fw.write("12345!@#"); // 이렇게만 하면 파일에 반영X: 내부 버퍼에 먼저 쓰이고 flush가 안된 상태!
//            fw.close() ; // flush() 호출 + 목적지와의 연결 끊기 (안해주면 쓸모없는 메모리 작업 계속 수행됨)
        } catch (IOException e) {
//            fw.close();
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

    }

    public static void main(String[] args) {
        write("src/day05/a.txt") ;
    }

}
