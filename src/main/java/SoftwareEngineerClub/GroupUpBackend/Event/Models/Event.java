package SoftwareEngineerClub.GroupUpBackend.Event.Models;

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
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Event {
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    private String location;

    private Date date;

    private String description;

    private EventType eventType;

    private List<User> attendees = new ArrayList<>();

    public int getAttendeesCount() {
        return attendees.size();
    }

    public void setTitle(String title) {
        if (!title.isEmpty() && title != null) {
            this.title = title;
        }
    }

    public void setLocation(String location) {
        if (!location.isEmpty() && location != null) {
            this.location = location;
        }
    }

    // TODO we might have to change this to check against a certain date (for example 50 years back)
    public void setDate(Date date) {
        if (date != null) {
            this.date = date;
        }
    }

    public void addAttendee(User user) {
        attendees.add(user);
    }

    public void deleteAttendee(User user) {
        attendees.remove(user);
    }
}
