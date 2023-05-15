package SoftwareEngineerClub.GroupUpBackend.Event.Services;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;
import SoftwareEngineerClub.GroupUpBackend.Event.Models.EventType;
import SoftwareEngineerClub.GroupUpBackend.Event.Repos.EventRepository;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeCreatedException;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeDeletedException;
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

    Optional<Event> optionalEvent;

    Event event;

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
                "A birthday party", EventType.BIRTHDAY, new ArrayList<>()));

        // Set an event for the tests that use it
        event = optionalEvent.get();
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
        assertEquals(event, event);
    }

    @Test
    void getsEventByTitle() throws NotFoundException {
        // Arrange
        when(eventRepository.findByTitle(anyString())).thenReturn(optionalEvent);

        // Act
        Event event = eventService.getEvent("Birthday party");

        // Assert
        verify(eventRepository).findByTitle(anyString());
        assertEquals(event, event);
    }

    @Test
    void willThrowWhenEventWithGivenIdDoesNotExist() {
        // Arrange
        long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> eventService.getEvent(1L));

        // Assert
        verify(eventRepository).findById(anyLong());
        assertThrows(NotFoundException.class, () -> eventService.getEvent(1L));
        assertEquals("Event with id {1} was not found.", exception.getMessage());
    }

    @Test
    void willThrowWhenEventWithGivenTitleDoesNotExist() {
        // Arrange
        String title = "DoesNotExist";
        when(eventRepository.findByTitle(title)).thenReturn(Optional.empty());

        // Act
        NotFoundException exception = assertThrows(NotFoundException.class, () -> eventService.getEvent(title));

        // Assert
        verify(eventRepository).findByTitle(anyString());
        assertThrows(NotFoundException.class, () -> eventService.getEvent(title));
        assertEquals("Event with title {DoesNotExist} was not found.", exception.getMessage());
    }

    @Test
    void postsEvent() throws CouldNotBeCreatedException {
        // Arrange
        when(eventRepository.findByTitle(anyString())).thenReturn(Optional.empty());
        when(eventRepository.save(any())).thenReturn(event);

        // Act
        Event postedEvent = eventService.postEvent(event);

        // Assert
        verify(eventRepository).save(any());
        assertEquals(postedEvent, event);
    }

    @Test
    void willThrowWhenEventAlreadyExists() {
        // Arrange
        when(eventRepository.findByTitle(anyString())).thenReturn(optionalEvent);

        // Act
        CouldNotBeCreatedException exception =
                assertThrows(CouldNotBeCreatedException.class, () -> eventService.postEvent(event));

        // Assert
        verify(eventRepository).findByTitle(anyString());
        assertThrows(CouldNotBeCreatedException.class, () -> eventService.postEvent(event));
        assertEquals("Event with title {Birthday party} already exists.", exception.getMessage());
    }

    @Test
    void deletesEvent() throws CouldNotBeDeletedException {
        // Arrange
        when(eventRepository.findById(anyLong())).thenReturn(optionalEvent);

        // Act
        eventService.deleteEvent(anyLong());

        // Assert
        verify(eventRepository).findById(anyLong());
    }

    @Test
    void throwsWhenEventToDeleteDoesNotExist() {
        // Arrange
        when(eventRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        CouldNotBeDeletedException exception =
                assertThrows(CouldNotBeDeletedException.class, () -> eventService.deleteEvent(anyLong()));

        // Assert
        verify(eventRepository).findById(anyLong());
        assertThrows(CouldNotBeDeletedException.class, () -> eventService.deleteEvent(anyLong()));
        assertEquals("Event with id {0} could not be deleted.", exception.getMessage());
    }
}
