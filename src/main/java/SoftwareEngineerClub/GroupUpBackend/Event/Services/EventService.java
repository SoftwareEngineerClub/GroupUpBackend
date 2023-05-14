package SoftwareEngineerClub.GroupUpBackend.Event.Services;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;
import SoftwareEngineerClub.GroupUpBackend.Event.Repos.EventRepository;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeCreatedException;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeDeletedException;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private EventRepository repository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.repository = eventRepository;
    }

    public List<Event> getEvents() {
        return repository.findAll();
    }

    public Event getEvent(long id) throws NotFoundException {
        Optional<Event> event = repository.findById(id);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new NotFoundException(String.format("Event with id {%d} was not found.", id));
        }
    }

    public Event getEvent(String title) throws NotFoundException {
        Optional<Event> event = repository.findByTitle(title);
        if (event.isPresent()) {
            return event.get();
        } else {
            throw new NotFoundException(String.format("Event with title {%s} was not found.", title));
        }
    }

    public Event postEvent(Event event) throws CouldNotBeCreatedException {
        Optional<Event> optionalEvent = repository.findByTitle(event.getTitle());
        if (optionalEvent.isPresent()) {
            throw new CouldNotBeCreatedException("Event with title {%s} already exists.");
        } else {
            repository.save(event);
            Optional<Event> createdEvent = repository.findById(event.getId());
            if (createdEvent.isPresent()) {
                return createdEvent.get();
            } else {
                throw new CouldNotBeCreatedException("Recipe could not be created");
            }
        }
    }

    public void deleteEvent(long id) throws CouldNotBeDeletedException {
        Optional<Event> event = repository.findById(id);
        if (event.isPresent()) {
            repository.deleteById(event.get().getId());
        } else {
            throw new CouldNotBeDeletedException(String.format("Event with id {%s} could not be deleted.", id));
        }
    }

//    public Event updateEvent(long id, Event event) throws CouldNotBeUpdatedException {
//        // TODO finish this tomorrow.
//    }
}
