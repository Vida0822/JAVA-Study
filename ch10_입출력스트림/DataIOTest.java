package day05;

import java.io.*;

public class DataIOTest {
    public static void write(String fileName){
        /*
        목적지: fileName 파일
        파일출력스트림: FileOutputStream
        가공용출력스트림: DataOutputStream
         */
        DataOutputStream dos = null ;
        try{
            dos = new DataOutputStream(new FileOutputStream(fileName)) ;
            dos.writeInt(1) ; // 4 byte -> 자료형을 지정해서 입력하니 크기도 조절 가능!
            dos.writeBoolean(true); // 1 byte
            dos.writeDouble(1.2); // 8 byte
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(dos != null){
                try {
                    dos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    } // write

    public static void read(String fileName){
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(new FileInputStream(fileName)) ;
            int i = dis.readInt() ;
            boolean b = dis.readBoolean() ;
            double d = dis.readDouble() ;
            System.out.println(i+":"+ b+":"+d);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }finally {
            if(dis != null){ // 이거 안해주면 NullPointerException 발생 가능
                try {
                    dis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    } // read

    public static void main(String[] args) {
        String fileName = "src/day05/a.dat" ;
        write(fileName) ;
        read(fileName) ;
    } // main
}
