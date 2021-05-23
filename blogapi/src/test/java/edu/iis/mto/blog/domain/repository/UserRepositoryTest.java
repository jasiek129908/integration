package edu.iis.mto.blog.domain.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import edu.iis.mto.blog.domain.model.AccountStatus;
import edu.iis.mto.blog.domain.model.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    private User user;
    private final String NO_VALUE = "NO_VALUE";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFirstName("Jan");
        user.setLastName("Kowalski");
        user.setEmail("john@domain.com");
        user.setAccountStatus(AccountStatus.NEW);
    }

    @Test
    void shouldFindNoUsersIfRepositoryIsEmpty() {
        List<User> users = repository.findAll();

        assertThat(users, hasSize(0));
    }

    @Test
    void shouldFindOneUsersIfRepositoryContainsOneUserEntity() {
        User persistedUser = entityManager.persist(user);
        List<User> users = repository.findAll();

        assertThat(users, hasSize(1));
        assertThat(users.get(0)
                        .getEmail(),
                equalTo(persistedUser.getEmail()));
    }

    @Test
    void shouldStoreANewUser() {
        User persistedUser = repository.save(user);

        assertThat(persistedUser.getId(), notNullValue());
    }

    @Test
    void shouldFindUserByFirstName() {
        User persistedUser = repository.save(user);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(
                "jan", NO_VALUE, NO_VALUE);

        assertThat(persistedUser, equalTo(users.get(0)));
    }

    @Test
    void shouldFindUserByPartOfEmail() {
        User persistedUser = repository.save(user);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(
                NO_VALUE, NO_VALUE, "john");

        assertThat(persistedUser, equalTo(users.get(0)));
    }

    @Test
    void shouldFindUserByPartOfLastName() {
        User persistedUser = repository.save(user);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(
                NO_VALUE, "KOWAL", NO_VALUE);

        assertThat(persistedUser, equalTo(users.get(0)));
    }

    @Test
    void shouldFindTwoUserByFirstName() {
        User user2 = new User();
        user2.setFirstName("Jakub");
        user2.setLastName("Kolo");
        user2.setEmail("niewazny@domain.com");
        user2.setAccountStatus(AccountStatus.NEW);

        User persistedUser = repository.save(user);
        User persistedUser2 = repository.save(user2);

        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(
                "Ja", NO_VALUE, NO_VALUE);

        assertThat(users, hasSize(2));
        assertThat(users.get(0).getFirstName().startsWith("Ja"),is(true));
        assertThat(users.get(1).getFirstName().startsWith("Ja"),is(true));
    }

    @Test
    void shouldNotFindUserByLastName() {
        User persistedUser = repository.save(user);
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(
                NO_VALUE, "nowak", NO_VALUE);

        assertThat(users, hasSize(0));
    }

    @Test
    void shouldNotFindAnyUserWhenRepositoryIsEmpty() {
        String anyValue = "";
        List<User> users = repository.findByFirstNameContainingOrLastNameContainingOrEmailContainingAllIgnoreCase(
                anyValue, anyValue, anyValue);

        assertThat(users, hasSize(0));
    }
}
