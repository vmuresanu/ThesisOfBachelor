package com.victor.controller;

import com.victor.model.FileBucket;
import com.victor.model.User;
import com.victor.model.UserFile;
import com.victor.service.UserFileService;
import com.victor.service.UserService;
import com.victor.util.FileValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Created by vmuresanu on 4/27/2017.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    UserService userService;

    @Autowired
    UserFileService userFileService;

    @Autowired
    FileValidator fileValidator;

    static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @InitBinder("fileBucket")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(fileValidator);
    }

    @RequestMapping(value = {"/add-file-{userId}"}, method = RequestMethod.GET)
    public String addDocuments(@PathVariable int userId, ModelMap model){
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());

        FileBucket fileBucket = new FileBucket();
        model.addAttribute("fileBucket", fileBucket);
        System.out.println(fileBucket.getFile());

        List<UserFile> files = userFileService.findAllByUserId(userId);
        model.addAttribute("files", files);

        return "managefiles";
    }

    @RequestMapping(value = {"/download-file-{userId}-{docId}"}, method = RequestMethod.GET)
    public String downloadDocument(@PathVariable int userId, @PathVariable int docId,
                                   HttpServletResponse response)throws IOException{
        UserFile document = userFileService.findById(docId);
        response.setContentType(document.getType());
        response.setContentLength(document.getContent().length);
        response.setHeader("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"");

        FileCopyUtils.copy(document.getContent(), response.getOutputStream());

        return "redirect:/add-file-"+userId;
    }

    @RequestMapping(value = {"/delete-file-{userId}-{docId}"}, method = RequestMethod.GET)
    public String deleteDocument(@PathVariable int userId, @PathVariable int docId){
        userFileService.deleteById(docId);
        return "redirect:/file/add-file-"+userId;
    }

    @RequestMapping(value = {"/add-file-{userId}"}, method = RequestMethod.POST)
    public String uploadDocument(@Valid FileBucket fileBucket, BindingResult result, ModelMap model,
                                 @PathVariable int userId){
        if(result.hasErrors()){
            System.out.println("Validation errors!");
            User user = userService.findById(userId);
            model.addAttribute("user", user);

            List<UserFile> files = userFileService.findAllByUserId(userId);
            model.addAttribute("files", files);

            return "managefiles";
        } else {
            System.out.println("Fetching File");

            User user = userService.findById(userId);
            model.addAttribute("user", user);

            try {
                saveDocument(fileBucket, user);
                logger.info("User {} has uploaded the document to the database", user.getSsoId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "redirect:/file/add-file-"+userId;
        }
    }

    private void saveDocument(FileBucket fileBucket, User user)throws IOException{

        UserFile file = new UserFile();

        MultipartFile multipartFile = fileBucket.getFile();

        file.setName(multipartFile.getOriginalFilename());
        file.setDescription(fileBucket.getDescription());
        file.setType(multipartFile.getContentType());
        file.setContent(multipartFile.getBytes());
        file.setUser(user);
        userFileService.saveDocument(file);
    }

    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
