package in.bushansirgul.springrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@ToString
@Entity  // Entity class is a special type of class that represents the database table inside our application
@Table(name = "tbl_employee_automatically") // to map the class with the table in the db.
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

//    @JsonProperty("full_name") // If we want to give one of the fields a different name
    @Column(name = "name")
    @NotNull(message = "name should not be null") // a custom message when the exception throws because of this
    private String name;

//    @JsonIgnore  // If we don't want to return one of the fields in our JSON, we will add @JsonIgnore
    @Column(name = "age")
    private Long age = 0L;  // default value (if the user left it null.

    @Column(name = "location")
    private String location;

    @Email(message = "Please enter the valid email address")  // For validating the email that is right or not.
    @Column(name = "email")
    private String email;

    @Column(name = "department")
    @NotNull
    private String department;

    @CreationTimestamp
    @Column(name = "created_At", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_At")
    private Date updatedAt;

}
