package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.Form;
import be.helha.interf_app.Model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FormRepository extends MongoRepository<Form, String> {

    List<Form> findByIdGroup(String idGroup);
}
