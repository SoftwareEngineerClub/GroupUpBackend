package SoftwareEngineerClub.GroupUpBackend.Event.Controllers;

import SoftwareEngineerClub.GroupUpBackend.Event.Models.Event;
import SoftwareEngineerClub.GroupUpBackend.Event.Services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/events")
public class EventController {
    @Autowired
    private EventService service;

//    @GetMapping
//    public List<Event>() {
//        return service.
//    }
}
