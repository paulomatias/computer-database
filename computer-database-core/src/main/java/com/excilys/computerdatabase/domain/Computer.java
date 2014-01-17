package com.excilys.computerdatabase.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.domain
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
@Entity
@Table(name="computer")
public class Computer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
     * Attributes
     */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name="name")
    private String name;
    @Column(name="introduced")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime introduced;
    @Column(name="discontinued")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime discontinued;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id")
    private Company company;

    /*
     * Constructors
     */

    public Computer() {}
    public Computer(Long id, String name, DateTime introduced, DateTime discontinued, Company company) {
        super();
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }


    /*
     * toString/hashCode/equals
     */

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced="
                + introduced + ", discontinued=" + discontinued + ", company="
                + company + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((company == null) ? 0 : company.hashCode());
        result = prime * result
                + ((discontinued == null) ? 0 : discontinued.hashCode());
        result = prime * result
                + ((introduced == null) ? 0 : introduced.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Computer other = (Computer) obj;
        if(this.id == other.getId())
            return true;
        if (company == null) {
            if (other.company != null)
                return false;
        } else if (!company.equals(other.company))
            return false;
        if (discontinued == null) {
            if (other.discontinued != null)
                return false;
        } else if (!discontinued.equals(other.discontinued))
            return false;
        if (introduced == null) {
            if (other.introduced != null)
                return false;
        } else if (!introduced.equals(other.introduced))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }


    /*
     * Builder
     */
    public static class Builder {

        Computer computer;

        private Builder() {
            computer = new Computer();
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

        public Builder introduced(DateTime introduced) {
            this.computer.introduced = introduced;
            return this;
        }

        public Builder discontinued(DateTime discontinued) {
            this.computer.discontinued = discontinued;
            return this;
        }

        public Builder company(Company company) {
            this.computer.company = company;
            return this;
        }

        public Computer build() {
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
        if(id != null)
            this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DateTime getIntroduced() {
        return introduced;
    }

    public void setIntroduced(DateTime introduced) {
        this.introduced = introduced;
    }

    public DateTime getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(DateTime discontinued) {
        this.discontinued = discontinued;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


}