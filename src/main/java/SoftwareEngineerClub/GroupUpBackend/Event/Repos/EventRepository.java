package SoftwareEngineerClub.GroupUpBackend.Event.Repos;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public Optional<Event> findByTitle(String title);
}
