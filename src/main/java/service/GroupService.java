package service;

import dao.ContactDAO;
import dao.GroupDAO;
import entities.EntityFactory;
import entities.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("groupService")
@Transactional
public class GroupService {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private ContactDAO contactDAO;

    @Autowired
    private EntityFactory entityFactory;

    public boolean add(Map<String, Object> params) {
        Group group = (Group) entityFactory.getGroup(params);
        return groupDAO.add(group);
    }

    public boolean delete(int groupId) {
        Group group = groupDAO.get(groupId);
        return group != null && contactDAO.deleteGroup(group.getId()) && groupDAO.delete(group);
    }

    public boolean update(Map<String, Object> params) {
        Group group = (Group) entityFactory.getGroup(params);
        return groupDAO.update(group);
    }

    public Set<Map<String, Object>> getAll() {
        Set<Map<String, Object>> all = new HashSet<>();
        for (Group group : groupDAO.getAll()) {
            Map<String, Object> groupMap = new HashMap<>();
            groupMap.put("id", group.getId());
            groupMap.put("groupName", group.getGroupName());
            all.add(groupMap);
        }
        return all;
    }

    public Set<Map<String, Object>> getAll(int userId) {
        return getAll().stream().filter(x -> (int) x.get("userId") == userId).collect(Collectors.toSet());
    }
}
