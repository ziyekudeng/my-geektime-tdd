package geektime.tdd.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Optional;

public class TestApplication {
    /*
     * 执行完成后删除项目下的 Project00/target
     */
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("student");
        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin();

        StudentRepository studentRepository = new StudentRepository(entityManager);
        Student john = studentRepository.save(new Student("john", "smith", "john.smith@gmail.com"));

        entityManager.getTransaction().commit();

        System.out.println(john.getId());

        Optional<Student> loaded = studentRepository.findById(john.getId());

        System.out.println(loaded);
    }
}
