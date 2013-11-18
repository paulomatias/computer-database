package com.excilys.computerdatabase.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.domain
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class Computer {

    /*
     * Attributes
     */

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy");

    private Long id;
    private String name;
    private Calendar introduced;
    private Calendar discontinued;
    private Company company;



    /*
     * Constructurs
     */

    public Computer() {}
    public Computer(Long id, String name, Calendar introduced, Calendar discontinued, Company company) {
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

        public Builder introduced(Calendar introduced) {
            this.computer.introduced = introduced;
            return this;
        }

        public Builder introduced(Date introduced) {
            if(introduced != null) {
                this.computer.introduced = Calendar.getInstance();
                this.computer.introduced.setTime(introduced);
            }
            return this;
        }

        public Builder discontinued(Calendar discontinued) {
            this.computer.discontinued = discontinued;
            return this;
        }

        public Builder discontinued(Date discontinued) {
            if(discontinued != null) {
                this.computer.discontinued = Calendar.getInstance();
                this.computer.discontinued.setTime(discontinued);
            }
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

    public Calendar getIntroduced() {
        return introduced;
    }

    public String getIntroducedFormated() {
        if(introduced != null)
            return DATE_FORMAT.format(introduced.getTime());
        return "N/A";
    }

    public void setIntroduced(Calendar introduced) {
        this.introduced = introduced;
    }

    public void setIntroduced(Date introduced) {
        if(this.introduced == null)
            this.introduced = Calendar.getInstance();
        this.introduced.setTime(introduced);
    }

    public Calendar getDiscontinued() {
        return discontinued;
    }

    public String getDiscontinuedFormated() {
        if(discontinued != null)
            return DATE_FORMAT.format(discontinued.getTime());
        return "N/A";
    }

    public void setDiscontinued(Calendar discontinued) {
        this.discontinued = discontinued;
    }

    public void setDiscontinued(Date discontinued) {
        if(this.discontinued == null)
            this.discontinued = Calendar.getInstance();
        this.discontinued.setTime(discontinued);
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


}