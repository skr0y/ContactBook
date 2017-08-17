package model.entities;

import java.io.Serializable;

public class Group extends Entity implements Serializable {
    private int id;
    private String groupName;
    private int userId;

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getGroupName() {
        return groupName;
    }

    void setGroupName(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            throw new IllegalArgumentException("Group name is not specified");
        }
        this.groupName = groupName;
    }

    public int getUserId() {
        return userId;
    }

    void setUserId(int userId) {
        this.userId = userId;
    }
}
