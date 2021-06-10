package br.com.api.timesheet.bdd;

import br.com.api.timesheet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DatabaseActions {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void clear() {
        userRepository.deleteAll();
    }

}
