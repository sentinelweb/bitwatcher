package java.nio.file;

import java.io.File;

public class Paths {
     public static Path get(String base, String... more) {
         return new Path() {

             @Override
             public File toFile() {
                 String path = base;
                 for (String element: more) {
                     path += File.separator+element;
                 }
                 return new File(path);
             }

         };
     }
}