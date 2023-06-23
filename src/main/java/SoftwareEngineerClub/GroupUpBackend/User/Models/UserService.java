package SoftwareEngineerClub.GroupUpBackend.User.Models;

import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeUpdatedException;
import SoftwareEngineerClub.GroupUpBackend.User.Models.User;
import SoftwareEngineerClub.GroupUpBackend.User.Models.UserRepository;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeCreatedException;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.CouldNotBeDeletedException;
import SoftwareEngineerClub.GroupUpBackend.Exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getUsers() {
        return repository.findAll();
    }

    public User getUser(long id) throws NotFoundException {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()) {
            return user.get();
        } else {
            throw new NotFoundException(String.format("User with id {%d} was not found.", id));
        }
    }

    public User postUser(User user) throws CouldNotBeCreatedException {
        Optional<User> optionalUser = repository.findByUsername(user.getUsername());
        if(optionalUser.isPresent()) {
            throw new CouldNotBeCreatedException(String.format("User with username {%s} already exists.", user.getUsername()));
        } else {
            User newUser = repository.save(user);
            return newUser;
        }
    }

    public User deleteUser(long id) throws CouldNotBeDeletedException {
        Optional<User> user = repository.findById(id);
        if(user.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new CouldNotBeDeletedException(String.format("User with id {%d} could not be deleted.", id));
        }
    }

    public User updateUser(User user, long id) throws CouldNotBeUpdatedException {
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isPresent()) {
            // add new features
            User updatedUser = optionalUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setDescription(user.getDescription());

            repository.save(updatedUser);
            return updatedUser;
        } else {
            throw new CouldNotBeUpdatedException(String.format("User with id {%d} could not be updated.", id));
            // could return empty Optional here if you prefer --- still researching advantages
        }
    }


}
