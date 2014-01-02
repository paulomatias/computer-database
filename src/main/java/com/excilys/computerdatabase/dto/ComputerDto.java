package com.excilys.computerdatabase.dto;

import com.excilys.computerdatabase.domain.Company;

import java.util.Calendar;
import java.util.Date;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.dto
 * User: lortola
 * Date: 25/11/13
 * Description: N/A
 */
public class ComputerDto {

    /*
     * Attributes
     */
    private Long id;
    private String name;
    private String introduced;
    private String discontinued;
    private Long companyId;



     /*
      * toString/hashCode/equals
      */

    @Override
    public String toString() {
        return "ComputerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduced='" + introduced + '\'' +
                ", discontinued='" + discontinued + '\'' +
                ", companyId=" + companyId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComputerDto that = (ComputerDto) o;

        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (discontinued != null ? !discontinued.equals(that.discontinued) : that.discontinued != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (introduced != null ? !introduced.equals(that.introduced) : that.introduced != null) return false;
        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + (introduced != null ? introduced.hashCode() : 0);
        result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        return result;
    }

    /*
     * Builder
     */
    public static class Builder {

        ComputerDto computer;

        private Builder() {
            computer = new ComputerDto();
        }

        public Builder id(Long id) {
            if(id != null)
                this.computer.id = id;
            return this;
        }

        public Builder name(String name) {
            this.computer.name = name;
            return this;
        }

        public Builder introduced(String introduced) {
           this.computer.introduced = introduced;
            return this;
        }

        public Builder discontinued(String discontinued) {
            this.computer.discontinued = discontinued;
            return this;
        }

        public Builder companyId(Long companyId) {
            this.computer.companyId = companyId;
            return this;
        }

        public ComputerDto build() {
            return this.computer;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
