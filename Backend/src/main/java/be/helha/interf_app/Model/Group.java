package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Groups")
public class Group {
    @Id
    private String id;
    private String name;
    private List<String> listManagers;
    private List<String> listMembers;
    private List<String> listForms;
}
