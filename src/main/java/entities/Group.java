package entities;

import javax.persistence.*;
import java.io.Serializable;

@javax.persistence.Entity
@Table(name = "Groups")
public class Group extends Entity implements Serializable {
    private int id;
    private String groupName;
    private int userId;

    private User user;

    @Id
    @SequenceGenerator(name = "seq_gen_group", sequenceName = "Groups_GroupID_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq_gen_group")
    @Column(name = "GroupID", nullable = false, unique = true)
    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    @Column(name = "GroupName", nullable = false)
    public String getGroupName() {
        return groupName;
    }

    void setGroupName(String groupName) {
        if (groupName == null || groupName.isEmpty()) {
            throw new IllegalArgumentException("Group name is not specified");
        }
        this.groupName = groupName;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID", nullable = false)
    public User getUser() {
        return user;
    }

    void setUser(User user) {
        this.user = user;
    }

    @Column(name = "userID")
    public int getUserId() {
        return userId;
    }

    void setUserId(int userId) {
        this.userId = userId;
    }
}
