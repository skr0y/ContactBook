package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import service.ContactService;
import service.GroupService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestfulController {
    @Autowired
    private ContactService contactService;

    @Autowired
    private GroupService groupService;

    @RequestMapping("/addContact")
    public Map<String, String> addContact(@RequestParam(value = "firstName", required = false) String firstName,
                                          @RequestParam(value = "lastName", required = false) String lastName,
                                          @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                          @RequestParam(value = "userId", required = false) String userId) {
        Map<String, Object> contact = new HashMap<>();
        contact.put("firstName", firstName);
        contact.put("lastName", lastName);
        contact.put("phoneNumber", phoneNumber);
        contact.put("userId", userId);
        boolean result = contactService.add(contact);

        Map<String, String> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("/addGroup")
    public Map<String, String> addGroup(@RequestParam(value = "groupName", required = false) String groupName,
                                        @RequestParam(value = "userId", required = false) String userId) {
        Map<String, Object> group = new HashMap<>();
        group.put("groupName", groupName);
        group.put("userId", userId);
        boolean result = groupService.add(group);

        Map<String, String> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("/deleteContact/{id}")
    public Map<String, String> deleteContact(@PathVariable("id") int id) {
        boolean result = contactService.delete(id);
        Map<String, String> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("/deleteGroup/{id}")
    public Map<String, String> deleteGroup(@PathVariable("id") int id) {
        boolean result = groupService.delete(id);
        Map<String, String> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("/updateContact")
    public Map<String, String> updateContact(@RequestParam(value = "contactId", required = false) String contactId,
                                             @RequestParam(value = "firstName", required = false) String firstName,
                                             @RequestParam(value = "lastName", required = false) String lastName,
                                             @RequestParam(value = "phoneNumber", required = false) String phoneNumber,
                                             @RequestParam(value = "userId", required = false) String userId) {
        Map<String, Object> contact = new HashMap<>();
        contact.put("contactId", contactId);
        contact.put("firstName", firstName);
        contact.put("lastName", lastName);
        contact.put("phoneNumber", phoneNumber);
        contact.put("userId", userId);
        boolean result = contactService.update(contact);

        Map<String, String> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("/updateGroup")
    public Map<String, String> updateGroup(@RequestParam(value = "groupId", required = false) String groupId,
                                           @RequestParam(value = "groupName", required = false) String groupName,
                                           @RequestParam(value = "userId", required = false) String userId) {
        Map<String, Object> group = new HashMap<>();
        group.put("groupId", groupId);
        group.put("groupName", groupName);
        group.put("userId", userId);
        boolean result = groupService.update(group);

        Map<String, String> resultMap = new HashMap<>();
        if (result) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }
        return resultMap;
    }

    @RequestMapping("/getContact/{id}")
    public Map<String, Object> getContact(@PathVariable("id") int id) {
        return contactService.get(id);
    }

    @RequestMapping("/getGroup/{id}")
    public Map<String, Object> getGroup(@PathVariable("id") int id) {
        return groupService.get(id);
    }

    @RequestMapping("/getAllContacts/")
    public List<Map<String, Object>> getAllContacts() {
        return contactService.getAll();
    }

    @RequestMapping("/getAllGroups/")
    public List<Map<String, Object>> getAllGroups() {
        return groupService.getAll();
    }
}
