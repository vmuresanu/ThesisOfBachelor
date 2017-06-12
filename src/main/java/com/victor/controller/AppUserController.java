package com.victor.controller;

import com.victor.model.UserDocument;
import com.victor.model.enums.DocumentType;
import com.victor.model.enums.State;
import com.victor.model.User;
import com.victor.model.UserProfile;
import com.victor.service.UserDocumentService;
import com.victor.service.UserProfileService;
import com.victor.service.UserService;
import com.victor.view.PdfBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by vmuresanu on 4/10/2017.
 */
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppUserController {

    @Autowired
    UserService userService;

    @Autowired
    UserProfileService userProfileService;

    @Autowired
    UserDocumentService userDocumentService;

    @Autowired
    MessageSource messageSource;

    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

    @Autowired
    AuthenticationTrustResolver trustResolver;

    static final Logger logger = LoggerFactory.getLogger(AppUserController.class);

    /*@InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(dateFormat, false));
    }*/

    @SuppressWarnings("unchecked")
    @Transactional
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap model) {

        List<User> users = userService.findAllUsers();

        model.addAttribute("users", users);

        model.addAttribute("loggedinuser", getPrincipal());
        return "userslist";
    }

    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap model) {
        User user = new User();
        model.addAttribute("states", Arrays.asList(State.values()) );
        model.addAttribute("user", user);
        model.addAttribute("edit", false);
        model.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }

    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            return "registration";
        }

        if (!userService.isUserSSOUnique(user.getId(), user.getSsoId())) {
            FieldError ssoError = new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }
        userService.saveUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " +
                user.getLastName() + " registered successfully");
        model.addAttribute("loggedinuser", getPrincipal());

        return "registrationsuccess";
    }

    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap model) {
        User user = userService.findBySSO(ssoId);
        //TODO: create service method to check if one user can edit another, and throw an exception.
        model.addAttribute("states", Arrays.asList(State.values()));
        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        model.addAttribute("loggedinuser", getPrincipal());
        System.out.println(getPrincipal());
        return "registration";
    }

    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.POST)
    public String updateUser(@Valid User user, BindingResult result,
                             ModelMap model, @PathVariable String ssoId) {


        if (result.hasErrors()) {
            return "registration";
        }

        userService.updateUser(user);

        model.addAttribute("success", "User " + user.getFirstName() + " " +
                user.getLastName() + " updated successfully");
        model.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }

    @RequestMapping(value = {"/delete-user-{ssoId}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId) {
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }


    @RequestMapping(value = {"/user-{ssoId}-add-newDocument"}, method = RequestMethod.GET)
    public String addNewEntry(@PathVariable String ssoId, ModelMap model){

        User user = userService.findBySSO(ssoId);
        model.addAttribute("documents", Arrays.asList(DocumentType.values()));
        model.addAttribute("user", user);
        model.addAttribute("loggedinuser", getPrincipal());

        UserDocument userDocument = new UserDocument();
        model.addAttribute("userDocument", userDocument);

        /*List<UserDocument> files = userDocumentService.findAllByUserId(userId);
        model.addAttribute("files", files);*/

        return "newentry";
    }

    @RequestMapping(value = {"/user-{ssoId}-add-newDocument"}, method = RequestMethod.POST)
    public String saveNewEntry(@Valid UserDocument userDocument, ModelMap model,
                               @PathVariable String ssoId){
        /*if (result.hasErrors()){
            System.out.println("Validation errors!");
            return "accessDenied";
        } else {
            User user = userService.findBySSO(ssoId);
            System.out.println(ssoId);
            model.addAttribute("user", user);

            userDocumentService.saveDocument(userDocument);

            return "registrationsuccess";
        }*/

            System.out.println("Fetching Document");
        User user = userService.findBySSO(ssoId);
        System.out.println(ssoId);
        model.addAttribute("user", user);

        userDocumentService.saveDocument(userDocument);

        return "registrationsuccess";
    }



    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles() {
        return userProfileService.findAll();
    }

    /**
     * This method handles Access-Denied redirect.
     */
    @RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }

    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/list";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest req, HttpServletResponse res){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            persistentTokenBasedRememberMeServices.logout(req, res, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return trustResolver.isAnonymous(authentication);
    }


    /*private void saveDocument(UserDocument newDocument, User user) {

        UserDocument document = new UserDocument();

        document.setFirstName(document.getFirstName());
        file.setDescription(fileBucket.getDescription());
        file.setType(multipartFile.getContentType());
        file.setContent(multipartFile.getBytes());
        file.setUser(user);
        userFileService.saveDocument(file);
    }*/


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

    @RequestMapping(value = "/newDoc-{ssoId}", method = RequestMethod.GET)
    public String add(Model model, @PathVariable String ssoId) {

        User user = userService.findBySSO(ssoId);
        model.addAttribute("user", user);

        UserDocument userDoc = new UserDocument();

        model.addAttribute("loggedinuser", getPrincipal());
        model.addAttribute("documents", Arrays.asList(DocumentType.values()));
        model.addAttribute("userDocument", userDoc);

        return "entry";
    }

    @RequestMapping(value = "/newDoc-{ssoId}", method = RequestMethod.POST)
    public String saveOrUpdate(@Valid UserDocument userDocument, @PathVariable String ssoId, BindingResult result, Model model) {

        User user = userService.findBySSO(ssoId);

        if(result.hasErrors()) {
            System.out.println("omg");
            System.out.println(userDocument.getUser().getId());
            return "entry";

        } else {
            userDocument.setUser(user);
            userDocumentService.saveDocument(userDocument);
            model.addAttribute("ssoId", user.getSsoId());
            model.addAttribute("docId", userDocument.getId());
            model.addAttribute("successDoc", getPrincipal() +" you have successfully added a new record!");
            logger.info("User {} has added a new document for {} with a price of {} MDL",
                    user.getSsoId(),
                    userDocument.getLastName(),
                    userDocument.getPrice()
            );
            return "addeddocsuccess";
        }
        //Logic to save data from userForm
    }

    @RequestMapping(value = "/downloadPDF-{ssoId}-{docId}")
    public void downloadPDF(@PathVariable String ssoId, @PathVariable int docId, Model model,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserDocument document = userDocumentService.findById(docId);
        model.addAttribute("doc", document);
       // System.out.println(document);
       // System.out.println(ssoId);
        PdfBuilder pdfBuilder = new PdfBuilder();
        try {
            pdfBuilder.render((Map<String, ?>) model, request, response);
            logger.info("User {} has downloaded the document with id = {}", ssoId, docId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
