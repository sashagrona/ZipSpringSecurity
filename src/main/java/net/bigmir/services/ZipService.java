package net.bigmir.services;

import net.bigmir.model.DBFile;
import net.bigmir.model.SimpleUser;
import net.bigmir.model.Zip;
import net.bigmir.repositories.FileRepository;
import net.bigmir.repositories.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class ZipService {
    @Autowired
    private ZipRepository zipRepository;

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public void save(Zip zip, SimpleUser user, List<File> files) {
        List<DBFile> list = new LinkedList<>();
        double sum = 0L;
        for (File file : files) {

            File f = new File("AllFiles/"+file.getName());
            sum += f.length();
            BigDecimal size = BigDecimal.valueOf(f.length()).divide(BigDecimal.valueOf(1024)).setScale(1,BigDecimal.ROUND_HALF_UP);
            list.add(new DBFile(file.getName(), size.doubleValue(), zip));
        }

        zip.addFiles(list);
        zip.addUser(user);
        sum = BigDecimal.valueOf(sum).divide(BigDecimal.valueOf(1024)).setScale(1,BigDecimal.ROUND_HALF_UP).doubleValue();
        zip.addSize(sum);
        zipRepository.save(zip);
    }

    @Transactional
    public List<Zip> findMyZips(SimpleUser user) {
        return zipRepository.findMyZips(user);
    }
    @Transactional
    public List<DBFile> findAllFiles(SimpleUser user){
        return fileRepository.findAllFilesByUser(user);
    }

    @Transactional
    public void deleteAllZips(SimpleUser user){
        zipRepository.deleteAllByUser(user);
    }

}
