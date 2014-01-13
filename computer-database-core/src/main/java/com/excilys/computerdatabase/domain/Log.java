package com.excilys.computerdatabase.domain;

import com.excilys.computerdatabase.common.LogOperationType;
import org.joda.time.DateTime;

/**
 * Created with IntelliJ IDEA.
 * User: lortola
 * Date: 31/12/13
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class Log {
    private Long id;
    private LogOperationType operationType;
    private DateTime operationDate;
    private String description;


    /*
     * Constructors
     */

    public Log() {}


    /*
     * toString/hashCode/equals
     */

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", operationType=" + operationType +
                ", operationDate=" + operationDate +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Log log = (Log) o;

        if (description != null ? !description.equals(log.description) : log.description != null) return false;
        if (id != null ? !id.equals(log.id) : log.id != null) return false;
        if (operationDate != null ? !operationDate.equals(log.operationDate) : log.operationDate != null) return false;
        if (operationType != log.operationType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (operationType != null ? operationType.hashCode() : 0);
        result = 31 * result + (operationDate != null ? operationDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    /*
     * Builder
     */
    public static class Builder {

        Log log;

        private Builder() {
            log = new Log();
        }

        public Builder id(Long id) {
            if(id != null)
                this.log.id = id;
            return this;
        }

        public Builder operationType(LogOperationType operationType) {
            this.log.operationType = operationType;
            return this;
        }

        public Builder operationDate(DateTime operationDate) {
            this.log.operationDate = operationDate;
            return this;
        }

        public Builder description(String description) {
            this.log.description = description;
            return this;
        }

        public Log build() {
            return this.log;
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    /*
     * Getter/Setter
     */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LogOperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(LogOperationType operationType) {
        this.operationType = operationType;
    }

    public DateTime getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(DateTime operationDate) {
        this.operationDate = operationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
