package model.dao;

import model.entities.Group;

import java.util.Set;

public interface GroupDAO {
    boolean load();
    boolean save();
    boolean update(Set<Group> groups);
    Set<Group> getAll();
}
