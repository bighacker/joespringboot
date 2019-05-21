package com.joe.joespringboot.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileIoDemo {
    public static void main(String[] args){
        try {
//            FileChannel fc = new FileOutputStream("e:/test.txt").getChannel();
//            fc.write(ByteBuffer.wrap("33333".getBytes()));
//            fc.close();

            FileChannel in = new FileInputStream("e:/1.txt").getChannel();
            FileChannel out = new FileOutputStream("e:/2.txt").getChannel();

            ByteBuffer bb = ByteBuffer.allocate(1024);
            while(in.read(bb) != -1){
                bb.flip();
                out.write(bb);
                bb.clear();
            }

            in.transferTo(0,in.size(),out);

        }catch (FileNotFoundException e){
            System.out.println(11111);
        }catch (IOException e){
            System.out.println(22222);
        }

    }

}
