package geektime.tdd.model;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StudentRepositoryTest {
    private EntityManagerFactory factory;

    private EntityManager manager;

    private StudentRepository repository;
    private Student john;

    @BeforeEach
    public void before() {
        factory = Persistence.createEntityManagerFactory("student");
        manager = factory.createEntityManager();
        repository = new StudentRepository(manager);

        manager.getTransaction().begin();
        john = repository.save(new Student("john", "smith", "john.smith@gmail.com"));
        manager.getTransaction().commit();
    }

    @AfterEach
    public void after() {
        manager.clear();
        manager.close();
        factory.close();
    }

    @Test
    public void should_generate_id_for_save_entity() {

        Assertions.assertNotEquals(0, john.getId());
    }

    @Test
    public void should_be_able_to_load_saved_student_by_id() {
        Optional<Student> loaded = repository.findById(john.getId());

        Assertions.assertTrue(loaded.isPresent());

        Assertions.assertEquals(john.getId(), loaded.get().getId());
        
        Assertions.assertEquals(john.getEmail(), loaded.get().getEmail());

        Assertions.assertEquals(john.getFirstName(), loaded.get().getFirstName());

        Assertions.assertEquals(john.getLastName(), loaded.get().getLastName());
    }


}
