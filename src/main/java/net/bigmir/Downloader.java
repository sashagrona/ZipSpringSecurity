package net.bigmir;

import net.bigmir.model.SimpleUser;
import net.bigmir.model.Zip;
import net.bigmir.services.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Downloader implements Runnable {

    @Autowired
    private ZipService zipService;

    private MultipartFile[] files;
    private File zip;
    private File adminDir;
    private String nameZip;
    private SimpleUser simpleUser;
    private Map<String,byte[]> map;

    public Downloader(Map<String,byte[]> map,ZipService zipService, MultipartFile[] files, File zip, File adminDir, String nameZip, SimpleUser simpleUser) {
        this.files = files;
        this.zip = zip;
        this.adminDir = adminDir;
        this.nameZip = nameZip;
        this.simpleUser = simpleUser;
        this.zipService = zipService;
        this.map=map;
    }

    @Override
    public void run() {
        try (OutputStream fos = new FileOutputStream(zip);
             OutputStream zos = new ZipOutputStream(fos)) {
            List<File> fileList = new LinkedList<>();
            for (MultipartFile file : files) {
                ZipEntry entry = new ZipEntry(file.getOriginalFilename());
                byte[] bytes = map.get(file.getOriginalFilename());
                File convFile = new File(file.getOriginalFilename());
                fileList.add(convFile);
                File f = new File(adminDir + "/" + convFile.getName());
                try (OutputStream os = new FileOutputStream(f)) {
                    os.write(bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ((ZipOutputStream) zos).putNextEntry(entry);
                zos.write(bytes);
                ((ZipOutputStream) zos).closeEntry();
            }
            zos.flush();

            if (files.length == 0) {
                throw new FileNotFoundException();
            }
            zipService.save(new Zip(nameZip), simpleUser, fileList);

        } catch (IOException e) {
            e.printStackTrace();
        }
      
    }
}
