package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FormRepository extends MongoRepository<Form, String> {
}
