package uz.pdp.appcompany.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcompany.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByCorpName(String corpName);

    boolean existsByCorpNameAndIdNot(String corpName, Integer id);
}
