package be.helha.interf_app.Repository;

import be.helha.interf_app.Model.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AnswerRepository extends MongoRepository<Answer, String> {

    List<Answer> getAnswerByIdForm (String idFrom);
}
