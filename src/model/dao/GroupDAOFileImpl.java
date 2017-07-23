package model.dao;

import model.entities.Group;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GroupDAOFileImpl implements GroupDAO {
    private final String DEFAULT_FILENAME = "groups.db";

    private String fileName = DEFAULT_FILENAME;
    private Set<Group> groups;

    public boolean update(Set<Group> groups) {
        this.groups = groups;
        return save();
    }

    public boolean save() {
        File dbFile = new File(fileName);
        try {
            if (!dbFile.exists() && !dbFile.createNewFile()) {
                throw new IOException("Output file is inaccessible");
            }
            FileOutputStream fos = new FileOutputStream(dbFile, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(groups);
            oos.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean load() {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            groups = (HashSet<Group>)ois.readObject();
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    public Set<Group> getAll() {
        if (groups == null) {
            load();
        }
        return groups;
    }
}
