package com.example.practiceproject;

import com.example.practiceproject.entity.User;
import com.example.practiceproject.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void createUserTest() {
        User user = new User();
        user.setEmail("abbat@gmail.com");
        user.setPassword("abbat");
        user.setUsername("abbat222");

        User savedUser = userRepository.save(user);
        User retrievedUser = entityManager.find(User.class, savedUser.getId());

        assert(retrievedUser.getEmail().equals(user.getEmail()));
    }
}
