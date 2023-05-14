package SoftwareEngineerClub.GroupUpBackend.Event.Services;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;
import SoftwareEngineerClub.GroupUpBackend.Event.Models.EventType;
import SoftwareEngineerClub.GroupUpBackend.Event.Repos.EventRepository;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.NotFoundException;
import SoftwareEngineerClub.GroupUpBackend.User.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
    @Mock
    EventRepository eventRepository;

    EventService eventService;

    List<Event> eventsList = new ArrayList<>();

    List<User> usersList = new ArrayList<>();

    Optional<Event> optionalEvent;

    @BeforeEach
    public void setup() {
        // Stub eventRepository
        eventService = new EventService(eventRepository);

        // Add an empty event to the eventsList
        this.eventsList.add(new Event());

        // Re-create optional event for the tests that use it
        optionalEvent = Optional.of(new Event(
                1, "Birthday party", "Manteca, CA",
                Date.valueOf(LocalDate.of(2023, Month.DECEMBER, 25)),
                "A birthday party", EventType.BIRTHDAY, usersList));
    }


    @Test
    void getsAllEvents() {
        // Arrange
        when(eventRepository.findAll()).thenReturn(eventsList);

        // Act
        List<Event> result = eventService.getEvents();

        // Assert
        verify(eventRepository).findAll();
        assertEquals(1, result.size());
        assertEquals(eventsList, result);
    }

    @Test
    void getsEventById() throws NotFoundException {
        // Arrange
        when(eventRepository.findById(anyLong())).thenReturn(optionalEvent);

        // Act
        Event event = eventService.getEvent(1);

        // Assert
        verify(eventRepository).findById(anyLong());
        assertEquals(event, optionalEvent.get());
        assertEquals(event.getId(), optionalEvent.get().getId());
    }

    @Test
    void getsEventByTitle() throws NotFoundException {
        // Arrange
        when(eventRepository.findByTitle(anyString())).thenReturn(optionalEvent);

        // Act
        Event event = eventService.getEvent("Birthday party");

        // Assert
        verify(eventRepository).findByTitle(anyString());
        assertEquals(event, optionalEvent.get());
        assertEquals(event.getTitle(), optionalEvent.get().getTitle());
    }

    @Test
    void willThrowWhenEventWithGivenIdDoesNotExist() {
        // Arrange
        long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThrows(NotFoundException.class, () -> eventService.getEvent(1L));
        NotFoundException exception = assertThrows(NotFoundException.class, () -> eventService.getEvent(1L));
        assertEquals("Event with id {1} was not found.", exception.getMessage());
    }

//    @Test
//    void willThrowWhenEventWithGivenTitleDoesNotExist() {
//
//    }
}
