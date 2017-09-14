package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.ContactService;
import service.GroupService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @GetMapping("/")
    public String mainPage() {
        if (isLoggedIn()) {
            return "menu";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String doLogout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "login";
    }

    @GetMapping("/menu")
    public String menuPage(ModelMap modelMap) {
        int userId = (int) userService.get(getPrincipal()).get("id");
        modelMap.addAttribute("contacts", contactService.getAll(userId));
        modelMap.addAttribute("groups", groupService.getAll(userId));
        return "menu";
    }

    @PostMapping("/menu")
    public String menuAction(@RequestParam(value = "deleteContact", required = false) String deleteContact,
                             @RequestParam(value = "deleteGroup", required = false) String deleteGroup,
                             @RequestParam(value = "contactId", required = false) String contactId,
                             @RequestParam(value = "firstName", required = false) String firstName,
                             @RequestParam(value = "lastName", required = false) String lastName,
                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                             @RequestParam(value = "groupId", required = false) String groupId,
                             @RequestParam(value = "groupName", required = false) String groupName,
                             @RequestParam(value = "newFirstName", required = false) String newFirstName,
                             @RequestParam(value = "newLastName", required = false) String newLastName,
                             @RequestParam(value = "newPhoneNumber", required = false) String newPhoneNumber,
                             @RequestParam(value = "newGroupName", required = false) String newGroupName) {
        int userId = (int) userService.get(getPrincipal()).get("id");
        if (deleteContact != null) {
            contactService.delete(Integer.parseInt(deleteContact));
        } else if (deleteGroup != null) {
            groupService.delete(Integer.parseInt(deleteGroup));
        } else if (contactId != null) {
            Map<String, Object> contact = new HashMap<>();
            contact.put("id", Integer.parseInt(contactId));
            contact.put("firstName", firstName);
            contact.put("lastName", lastName);
            contact.put("phoneNumber", phoneNumber);
            contact.put("userId", userId);
            contactService.update(contact);
        } else if (groupId != null) {
            Map<String, Object> group = new HashMap<>();
            group.put("id", groupId);
            group.put("groupName", groupName);
            group.put("userId", userId);
            groupService.update(group);
        } else if (newFirstName != null) {
            Map<String, Object> contact = new HashMap<>();
            contact.put("firstName", newFirstName);
            contact.put("lastName", newLastName);
            contact.put("phoneNumber", newPhoneNumber);
            contact.put("userId", userId);
            contactService.add(contact);
        } else if (newGroupName != null) {
            Map<String, Object> group = new HashMap<>();
            group.put("groupName", newGroupName);
            group.put("userId", userId);
            groupService.add(group);
        }
        return "menu";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }


    private String getPrincipal(){
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }

    private boolean isLoggedIn() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !authenticationTrustResolver.isAnonymous(authentication);
    }
}
