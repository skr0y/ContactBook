package model.dao;

import model.entities.Group;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GroupDAOFileImpl implements GroupDAO {
    private final String DEFAULT_FILENAME = "groups.db";

    private String fileName = DEFAULT_FILENAME;

    private boolean save(Set<Group> groups) {
        File dbFile = new File(fileName);
        try {
            dbFile.createNewFile();
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile, false))) {
                for (Group group : groups) {
                    oos.writeObject(group);
                }
            } catch (Exception e) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Set<Group> load() {
        Set<Group> groups = new HashSet<>();
        int lastId = 0;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            Group input;
            while ((input = (Group)ois.readObject()) != null) {
                groups.add(input);
                if (input.getId() > lastId) {
                    lastId = input.getId();
                }
            }
        } catch (Exception e) {
            //
        }
        Group.lastId = lastId;
        return groups;
    }

    public boolean add(Group group) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName, true))) {
            oos.writeObject(group);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean update(Group group) {
        Set<Group> groups = load();
        groups.stream().filter(x -> x.getId() == group.getId()).findFirst().ifPresent(groups::remove);
        groups.add(group);
        return save(groups);
    }

    public boolean delete(Group group) {
        Set<Group> groups = load();
        groups.stream().filter(x -> x.getId() == group.getId()).findFirst().ifPresent(groups::remove);
        return save(groups);
    }

    public Group get(int id) {
        Set<Group> groups = load();
        return groups.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public Set<Group> getAll() {
        return load();
    }
}
