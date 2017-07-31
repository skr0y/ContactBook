package model.entities;

import java.io.Serializable;

public class Group extends Entity implements Serializable {
    public static int lastId = 0;

    private int id;
    private String groupName;

    public int getId() {
        return id;
    }

    void setId() {
        this.id = ++lastId;
    }

    void setId(int id) { this.id = id; }

    public String getGroupName() {
        return groupName;
    }

    void setGroupName(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            throw new IllegalArgumentException("Group name is not specified");
        }
        this.groupName = groupName;
    }
}
