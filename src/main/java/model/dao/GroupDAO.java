package model.dao;

import model.entities.Group;

import java.util.Set;

public interface GroupDAO {
    boolean add(Group group);
    boolean update(Group group);
    boolean delete(Group group);
    Group get(int id);
    Set<Group> getAll();
}
