package model.entities;

import java.io.Serializable;

public class Group extends Entity implements Serializable {
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    void setGroupName(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            throw new IllegalArgumentException("Group name is not specified");
        }
        this.groupName = groupName;
    }

    public String getToString() {
        return groupName;
    }
}
