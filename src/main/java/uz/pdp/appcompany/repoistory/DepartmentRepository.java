package uz.pdp.appcompany.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcompany.entity.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Integer id);
}
