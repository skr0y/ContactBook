package model.dao;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import model.entities.Group;
import util.Observer;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class GroupDAOFileImpl implements GroupDAO {
    private final String DEFAULT_FILENAME = "groups.db";

    private String fileName = DEFAULT_FILENAME;
    private ObservableSet<Group> groups;

    GroupDAOFileImpl() {
        groups = FXCollections.observableSet(new HashSet<Group>());
    }

    public void addObserver(Observer observer) {
        groups.addListener((InvalidationListener) o -> observer.update(o));
        load();
    }

    public boolean update(Set<Group> groups) {
        this.groups.clear();
        this.groups.addAll(groups);
        return save();
    }

    public boolean save() {
        File dbFile = new File(fileName);
        try {
            dbFile.createNewFile();
            try (FileOutputStream fos = new FileOutputStream(dbFile, false);
                 ObjectOutputStream oos = new ObjectOutputStream(fos)) {
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

    public boolean load() {
        try (FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis)) {
            Group input;
            groups.clear();
            while ((input = (Group)ois.readObject()) != null) {
                groups.add(input);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public Set<Group> getAll() {
        if (groups.isEmpty()) {
            load();
        }
        return groups;
    }
}
