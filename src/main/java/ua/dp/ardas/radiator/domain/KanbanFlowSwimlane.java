package ua.dp.ardas.radiator.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A KanbanFlowSwimlane.
 */
@Entity
@Table(name = "kanban_flow_swimlane")
public class KanbanFlowSwimlane implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "index_number")
    private Integer indexNumber;

    @ManyToOne
    private KanbanFlowBoard board;

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

    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }

    public KanbanFlowBoard getBoard() {
        return board;
    }

    public void setBoard(KanbanFlowBoard kanbanFlowBoard) {
        this.board = kanbanFlowBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KanbanFlowSwimlane kanbanFlowSwimlane = (KanbanFlowSwimlane) o;
        if(kanbanFlowSwimlane.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, kanbanFlowSwimlane.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "KanbanFlowSwimlane{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", indexNumber='" + indexNumber + "'" +
            '}';
    }
}
