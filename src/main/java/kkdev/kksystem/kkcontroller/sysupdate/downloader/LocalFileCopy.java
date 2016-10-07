/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kkdev.kksystem.kkcontroller.sysupdate.downloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author blinov_is
 */
public class LocalFileCopy {

    // copy Directory method
    public static boolean copyDirectory(File source, File target) throws IOException {
        // check first if source file exists or not
        if (!source.exists()) {
            return false;
        }
        if (source.isDirectory()) {
            if (!target.exists()) {
                target.mkdir();
            }

            File[] listFile = source.listFiles();
            for (File f : listFile) {
                File sourceFile = new File(source, f.getName());
                File outputFile = new File(target, f.getName());
                if (f.isDirectory()) {
                    new LocalFileCopy().copyDirectory(sourceFile,
                            outputFile);
                } else {
                    new LocalFileCopy().copyFile(sourceFile, outputFile);
                }
            }

        }

        return true;
    }

    // Copy file method
    public static void copyFile(File input, File output) throws IOException {
        FileInputStream inputStream = new FileInputStream(input);
        // target file declaration
        FileOutputStream outputStream = new FileOutputStream(output);
        int lengthStream;
        byte[] buff = new byte[1024];
        while ((lengthStream = inputStream.read(buff)) > 0) {
            // writing to the target file contents of the source file
            outputStream.write(buff, 0, lengthStream);
        }
        outputStream.close();
        inputStream.close();
    }
}
