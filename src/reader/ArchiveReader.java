package reader;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ArchiveReader {

    public static int counter ;

    public static void main(String[] args) throws IOException {
        File arcFolder = new File("C:\\Users\\a.chimbeev\\Desktop\\Archives");
        String unArchPath = "C:\\Users\\a.chimbeev\\Desktop\\XMLS\\";
        File[] arcArray = arcFolder.listFiles();
        for (int i = 0; i < arcArray.length; i++) {
            try {
                System.out.println(arcArray[i].getAbsolutePath());
                ZipFile zipFile = new ZipFile(arcArray[i].getAbsolutePath());
                ZipInputStream zin = new ZipInputStream(new FileInputStream(arcArray[i].getAbsolutePath()));
                ZipEntry entry;
                while ((entry = zin.getNextEntry()) != null) {
                    if (entry.getName().endsWith(".xml")) {
                        System.out.println(entry.getName());
                        processFile(zipFile, unArchPath, entry);
                    }
                }
            } catch (ZipException | FileNotFoundException e) {
                System.out.println(e);
            }
        }
    }
    private static void processFile(ZipFile file, String uncompressedDirectory, ZipEntry entry) throws IOException {
        try (
                var is = file.getInputStream(entry);
                var bis = new BufferedInputStream(is)
        ) {
            var uncompressedFileName = uncompressedDirectory + counter + entry.getName() ;
            try (
                    var os = new FileOutputStream(uncompressedFileName);
                    var bos = new BufferedOutputStream(os)
            ) {
                while (bis.available() > 0) {
                    bos.write(bis.read());
                }
            }
        }
        counter++;
        System.out.println("Written: " + entry.getName());
    }
}

