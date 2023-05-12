package SoftwareEngineerClub.GroupUpBackend.Community.Models;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;
import SoftwareEngineerClub.GroupUpBackend.User.Models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Community {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private List<User> members = new ArrayList<>();

    private List<Event> events = new ArrayList<>();

    @Column(nullable = false)
    private User owner;

    // TODO what is a post going to have?
//    private List<Post> posts = new ArrayList<>();

    public static class gffgd {
    }
}
