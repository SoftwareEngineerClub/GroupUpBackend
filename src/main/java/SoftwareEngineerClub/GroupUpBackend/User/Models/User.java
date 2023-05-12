package SoftwareEngineerClub.GroupUpBackend.User.Models;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    private String description;

    private List<Event> events = new ArrayList<>();

    public void setFirstName(String firstName) {
        if (!firstName.isEmpty() && firstName != null) {
            this.firstName = firstName;
        }
    }

    public void setPassword(String password) {
        if (!password.isEmpty() && password != null) {
            this.password = password;
        }
    }

    public void setUsername(String username) {
        if (!username.isEmpty() && username != null) {
            this.username = username;
        }
    }

    public void setEmail(String email) {
        if (!email.isEmpty() && email != null) {
            this.email = email;
        }
    }

    public void setDescription(String description) {
        if (!description.isEmpty() && description != null) {
            this.description = description;
        }
    }
}
