package uz.pdp.appcompany.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.appcompany.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsByHomeNumber(String homeNumber);

    boolean existsByHomeNumberAndIdNot(String homeNumber, Integer id);
}
