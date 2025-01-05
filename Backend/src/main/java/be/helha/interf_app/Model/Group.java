package be.helha.interf_app.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * Represents a group in the application with the following attributes:
 *
 * Attributes:
 * - id: The unique identifier of the group.
 * - name: The name of the group.
 * - listManagers: A list of user IDs representing the managers of the group.
 * - listMembers: A list of user IDs representing the members of the group.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Groups")
public class Group {

    /**
     * The unique identifier of the group.
     */
    private String id;

    /**
     * The name of the group.
     */
    private String name;
    /**
     *  A map representing subgroups within the group.
     */
    private Map<String,List<String>> listSubGroups;

}
