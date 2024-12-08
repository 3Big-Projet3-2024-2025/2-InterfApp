package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
}
