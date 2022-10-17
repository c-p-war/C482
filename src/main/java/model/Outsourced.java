package model;

/**
 * Outsourced class, extends Part class. Used to get and set the Company Name
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Outsourced part constructor
     *
     * @param id          part id
     * @param name        part name
     * @param price       part price
     * @param stock       part stock
     * @param min         minimum part stock
     * @param max         maximum part stock
     * @param companyName manufacturing companies name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name for an Outsourced part
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Retrieves the companies name for an Outsourced part
     */
    public String getCompanyName() {
        return this.companyName;
    }
}
