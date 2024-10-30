import jakarta.persistence.*;

@Entity
@Table(name = plants)
public class Plant {
    private int id;
    private String name;   
}
