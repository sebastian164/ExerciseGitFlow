package habitat.exercise.application.service;

import org.springframework.stereotype.Service;
import habitat.exercise.application.dto.UserDto;

@Service
public class UserService {

    public UserDto getUserById(Long id) {
        if (id == null) return null;
        if (id.equals(1L)) {
            return new UserDto(1L, "Juan", "Pérez", "juan.perez@example.com", "+34123456789");
        }
        return null;
    }
}

