package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group,String> {
}
