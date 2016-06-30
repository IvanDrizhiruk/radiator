package ua.dp.ardas.radiator.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A KanbanFlowCellInfo.
 */
@Entity
@Table(name = "kanban_flow_cell_info")
public class KanbanFlowCellInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "total_seconds_estimated")
    private Long totalSecondsEstimated;

    @Column(name = "total_seconds_spent")
    private Long totalSecondsSpent;

    @Column(name = "extracting_date")
    private ZonedDateTime extractingDate;

    @ManyToOne
    private KanbanFlowBoard board;

    @ManyToOne
    private KanbanFlowColumn column;

    @ManyToOne
    private KanbanFlowSwimlane swimlane;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalSecondsEstimated() {
        return totalSecondsEstimated;
    }

    public void setTotalSecondsEstimated(Long totalSecondsEstimated) {
        this.totalSecondsEstimated = totalSecondsEstimated;
    }

    public Long getTotalSecondsSpent() {
        return totalSecondsSpent;
    }

    public void setTotalSecondsSpent(Long totalSecondsSpent) {
        this.totalSecondsSpent = totalSecondsSpent;
    }

    public ZonedDateTime getExtractingDate() {
        return extractingDate;
    }

    public void setExtractingDate(ZonedDateTime extractingDate) {
        this.extractingDate = extractingDate;
    }

    public KanbanFlowBoard getBoard() {
        return board;
    }

    public void setBoard(KanbanFlowBoard kanbanFlowBoard) {
        this.board = kanbanFlowBoard;
    }

    public KanbanFlowColumn getColumn() {
        return column;
    }

    public void setColumn(KanbanFlowColumn kanbanFlowColumn) {
        this.column = kanbanFlowColumn;
    }

    public KanbanFlowSwimlane getSwimlane() {
        return swimlane;
    }

    public void setSwimlane(KanbanFlowSwimlane kanbanFlowSwimlane) {
        this.swimlane = kanbanFlowSwimlane;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KanbanFlowCellInfo kanbanFlowCellInfo = (KanbanFlowCellInfo) o;
        if(kanbanFlowCellInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, kanbanFlowCellInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "KanbanFlowCellInfo{" +
            "id=" + id +
            ", totalSecondsEstimated='" + totalSecondsEstimated + "'" +
            ", totalSecondsSpent='" + totalSecondsSpent + "'" +
            ", extractingDate='" + extractingDate + "'" +
            '}';
    }
}
