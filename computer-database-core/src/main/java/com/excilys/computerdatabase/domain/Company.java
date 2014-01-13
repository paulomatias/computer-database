package com.excilys.computerdatabase.domain;

/**
 * Project: computer-database
 * Package: com.excilys.computerdatabase.domain
 * User: lortola
 * Date: 15/11/13
 * Description: N/A
 */
public class Company {

    /*
     * Attributes
     */
	private Long id;
    private String name;
    

    /*
     * Constructurs
     */

    public Company() {}

    public Company(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /*
     * toString/hashCode/equals
     */

    @Override
    public String toString() {
        return "Company[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ']';
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Company other = (Company) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

    /*
     * Getter/Setter
     */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}