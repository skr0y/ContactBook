package model.dao;

import model.entities.Group;
import util.Observer;

import java.util.Set;

public interface GroupDAO {
    void addObserver(Observer observer);
    boolean load();
    boolean save();
    boolean update(Set<Group> groups);
    Set<Group> getAll();
}
