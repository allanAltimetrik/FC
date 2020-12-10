package com.example.springboot.helper;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class ZipUtil {

//    public void copyFolder(String srcPath,String destPath)
        public void copyFolder()
        {
            File src = new File("./dummyPath");
            File dest = new File("./tempInput");
            try{
                FileUtils.copyDirectory(src,dest);}catch (Exception e){e.printStackTrace();}
            System.out.println("Copy process done");
        }

        //unzip folder
        //public void unZipFolder(String source,String destination){
        public void unZipFolder(){
            String source =  "./tempInput/records.zip"; String destination = "./tempProcessing/records";
            try {
                ZipFile zipFile = new ZipFile(source);
                zipFile.extractAll(destination);
            } catch (ZipException e) {
                e.printStackTrace();
            }
            System.out.println("Unzip process done");
        }

        //Zip folder
        // public  void zipFolder(String outputZipFile,String srcFolder) {
        public  void zipFolder() {
            String outputZipFile = "./tempProcessing/records.zip";
            String srcFolder = "./tempProcessing/records"; // SourceFolder path
            ZipUtil obj = new ZipUtil();
            obj.generateFileList(new File(srcFolder),srcFolder);
            obj.zipIt(outputZipFile,srcFolder);
        }

        public List<String> fileList;
        public ZipUtil() {
            fileList = new ArrayList< String >();
        }

        public void zipIt(String zipFile, String srcFolder) {
            byte[] buffer = new byte[1024];
            String source = new File(srcFolder).getName();
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(zipFile);
                zos = new ZipOutputStream(fos);

                System.out.println("Output to Zip : " + zipFile);
                FileInputStream in = null;

                for (String file: this.fileList) {
                    System.out.println("File Added : " + file);
                    ZipEntry ze = new ZipEntry(source + File.separator + file);
                    zos.putNextEntry(ze);
                    try {
                        in = new FileInputStream(srcFolder + File.separator + file);
                        int len;
                        while ((len = in .read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                        }
                    } finally {
                        in.close();
                    }
                }

                zos.closeEntry();
                System.out.println("Folder successfully compressed");

            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    zos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void generateFileList(File node,String srcFolder) {
            // add file only
            if (node.isFile()) {
                fileList.add(generateZipEntry(node.toString(),srcFolder));
            }

            if (node.isDirectory()) {
                String[] subNote = node.list();
                for (String filename: subNote) {
                    generateFileList(new File(node, filename),srcFolder);
                }
            }
        }

        private String generateZipEntry(String file,String srcFolder) {
            return file.substring(srcFolder.length() + 1, file.length());
        }
    }

