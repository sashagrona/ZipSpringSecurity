package net.bigmir.controllers;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.bigmir.Downloader;
import net.bigmir.model.DBFile;
import net.bigmir.model.SimpleUser;
import net.bigmir.model.UserRole;
import net.bigmir.model.Zip;
import net.bigmir.services.UserService;
import net.bigmir.services.ZipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MyController {
    @Autowired
    private UserService userService;

    @Autowired
    private ShaPasswordEncoder shaPasswordEncoder;

    @Autowired
    private ZipService zipService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/sign_in")
    public String sign(@RequestParam String login,
                       @RequestParam String password,Model model) {
        String hashPass = shaPasswordEncoder.encodePassword(password, null);
        if(login.equals("")||password.equals("")) {
            model.addAttribute("required", true);
        }else if (userService.isNotAuthorized(login)) {
            userService.addUser(login, hashPass, UserRole.USER);
            return "redirect:/login";
        }else {
            model.addAttribute("exists", true);
        }
        return "register";
    }

    @RequestMapping("/")
    public String cabinet(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("admin", isAdmin(user));
        String login = user.getUsername();
        SimpleUser simpleUser = userService.findByLogin(login);
        List<Zip> list = getMyZips(simpleUser);
        model.addAttribute("zips", list);
        model.addAttribute("log", login);
        model.addAttribute("role", user.getAuthorities());
        return "index";
    }

    private List<Zip> getMyZips(SimpleUser user) {
        List<Zip> list = zipService.findMyZips(user);
        return list;
    }

    @RequestMapping("/unknown")
    public String forbide() {
        return "unknown";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/get_files")
    public String getFiles(Model model, @RequestParam("name") String login) {
        SimpleUser simpleUser = userService.findByLogin(login);
        List<DBFile> files = zipService.findAllFiles(simpleUser);
        model.addAttribute("name", login);
        model.addAttribute("files", files);
        return "files";

    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(Model model, @RequestParam(value = "toDelete", required = false) long [] id){
        if(!(id.length<=0)){
            userService.deleteUsers(id);
        }
        return "redirect:/";
    }

    @RequestMapping("/admin")
    public String admin(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("username", user.getUsername());
        List<SimpleUser> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }


    private boolean isAdmin(User user) {
        Set<GrantedAuthority> roles = (Set<GrantedAuthority>) user.getAuthorities();
        for (GrantedAuthority ga : roles) {
            if ("ROLE_ADMIN".equals(ga.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping(value = "/get")
    public ResponseEntity<byte[]> getZip(Model model, @RequestParam("name") String zipName) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        File zip = new File("Users/" + user.getUsername() + "/" + zipName);
        try (InputStream is = new FileInputStream(zip);
             OutputStream os = new ByteOutputStream()) {
            int r = 0;
            byte[] buff = new byte[1024];
            while ((r = is.read(buff)) != -1) {
                os.write(buff);
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-disposition", "attachment; filename=Archive" + ".zip");
            return new ResponseEntity<>(((ByteOutputStream) os).getBytes(), httpHeaders, HttpStatus.OK);
        } catch (IOException o) {
            o.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/get_f")
    public ResponseEntity<byte[]> getFile(Model model, @RequestParam("name") String fileName) {
        File zip = new File("AllFiles/" + fileName);
        try (InputStream is = new FileInputStream(zip);
             OutputStream os = new ByteOutputStream()) {
            int r = 0;
            byte[] buff = new byte[1024];
            while ((r = is.read(buff)) != -1) {
                os.write(buff);
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Content-disposition", "attachment; filename=" + fileName);
            return new ResponseEntity<>(((ByteOutputStream) os).getBytes(), httpHeaders, HttpStatus.OK);
        } catch (IOException o) {
            o.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/make", method = RequestMethod.POST)
    public synchronized String makeZip(@RequestParam("files") MultipartFile[] files) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        System.out.println(login);
        SimpleUser simpleUser = userService.findByLogin(login);
        String nameZip = "ArcNo" + System.currentTimeMillis() + ".zip";
        String path = "Users/" + simpleUser.getLogin() + "/" + nameZip;
        File directory = new File("Users/" + simpleUser.getLogin());
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File adminDir = new File("AllFiles");
        if (!adminDir.exists()) {
            adminDir.mkdir();
        }
        File zip = new File(path);
        Map<String,byte [] > map = new HashMap<>();
        for (MultipartFile file:files) {
            try {
                byte [] bytes = file.getBytes();
                map.put(file.getOriginalFilename(),bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Downloader downloader = new Downloader(map,zipService,files,zip,adminDir,nameZip,simpleUser);
        Thread thread = new Thread(downloader);
        thread.start();
//        try (OutputStream fos = new FileOutputStream(zip);
//             OutputStream zos = new ZipOutputStream(fos)) {
//            List<File> fileList = new LinkedList<>();
//            for (MultipartFile file : files) {
//                ZipEntry entry = new ZipEntry(file.getOriginalFilename());
//                byte[] bytes = file.getBytes();
//
//                File convFile = new File(file.getOriginalFilename());
//                file.transferTo(convFile);
//                fileList.add(convFile);
//
//                File f = new File(adminDir + "/" + convFile.getName());
//                try (OutputStream os = new FileOutputStream(f)) {
//                    os.write(bytes);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                ((ZipOutputStream) zos).putNextEntry(entry);
//                zos.write(bytes);
//                ((ZipOutputStream) zos).closeEntry();
//            }
//            zos.flush();
//
//            if (files.length == 0) {
//                throw new FileNotFoundException();
//            }
//
//
//            zipService.save(new Zip(nameZip), simpleUser, fileList);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return "redirect:/";

    }

}
