package ua.dp.ardas.radiator.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Commiter.
 */
@Entity
@Table(name = "commiter")
public class Commiter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commiter commiter = (Commiter) o;
        if(commiter.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commiter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Commiter{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            '}';
    }
}
