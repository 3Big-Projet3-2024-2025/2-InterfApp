package be.helha.interf_app.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Reponse")
public class Reponse {
    @Id
    private String id_reponse;
    private String id_Form;
    private String id_User;
    private Map<String,Object> reponse;
}
