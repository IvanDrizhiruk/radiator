package ua.dp.ardas.radiator.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A IntegrationTestResult.
 */
@Entity
@Table(name = "integration_test_result")
public class IntegrationTestResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "instances_name")
    private String instancesName;

    @Column(name = "total")
    private Long total;

    @Column(name = "passed")
    private Long passed;

    @Column(name = "pending")
    private Long pending;

    @Column(name = "failed")
    private Long failed;

    @Column(name = "extracting_date")
    private ZonedDateTime extractingDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstancesName() {
        return instancesName;
    }

    public void setInstancesName(String instancesName) {
        this.instancesName = instancesName;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPassed() {
        return passed;
    }

    public void setPassed(Long passed) {
        this.passed = passed;
    }

    public Long getPending() {
        return pending;
    }

    public void setPending(Long pending) {
        this.pending = pending;
    }

    public Long getFailed() {
        return failed;
    }

    public void setFailed(Long failed) {
        this.failed = failed;
    }

    public ZonedDateTime getExtractingDate() {
        return extractingDate;
    }

    public void setExtractingDate(ZonedDateTime extractingDate) {
        this.extractingDate = extractingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntegrationTestResult integrationTestResult = (IntegrationTestResult) o;
        if(integrationTestResult.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, integrationTestResult.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "IntegrationTestResult{" +
            "id=" + id +
            ", instancesName='" + instancesName + "'" +
            ", total='" + total + "'" +
            ", passed='" + passed + "'" +
            ", pending='" + pending + "'" +
            ", failed='" + failed + "'" +
            ", extractingDate='" + extractingDate + "'" +
            '}';
    }
}
