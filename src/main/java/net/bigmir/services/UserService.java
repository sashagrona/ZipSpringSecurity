package net.bigmir.services;

import net.bigmir.model.SimpleUser;
import net.bigmir.model.UserRole;
import net.bigmir.repositories.FileRepository;
import net.bigmir.repositories.UserRepo;
import net.bigmir.repositories.ZipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ZipRepository zipRepository;

    @Autowired
    private FileRepository fileRepository;

    @Transactional
    public SimpleUser findByLogin(String login){
        return userRepo.findByLogin(login);
    }

    @Transactional
    public boolean addUser(String login,String hashPass, UserRole role){
        if(userRepo.existsByLogin(login)){
            return false;
        }
        SimpleUser user = new SimpleUser(login,hashPass,role);
        userRepo.save(user);
        return true;
    }
    @Transactional
    public boolean isNotAuthorized(String login){
        if(userRepo.existsByLogin(login)){
            return false;
        }
        return true;
    }
    @Transactional
    public List<SimpleUser> findAllUsers(){
        return userRepo.findAll();
    }
    @Transactional
    public void deleteUsers(long[] ids) {
        if (ids == null) return;
        List<Long> admins = userRepo.findByRole(UserRole.ADMIN);
        for (long id : ids) {
            if(admins.contains(id)){
                continue;
            }
            SimpleUser user = userRepo.findOne(id);
            fileRepository.deleteAllByZip(user);
            zipRepository.deleteAllByUser(user);
            userRepo.delete(id);
        }
    }

}
