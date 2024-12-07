package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.Reponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReponseRepository extends MongoRepository<Reponse, String> {
}
