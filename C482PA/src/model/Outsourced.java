package model;


/**
 * @author Richard Milliorn
 *
 * Has to extend part so that it can get info from part class.
 */
public class Outsourced extends Part {

    /**
     * Variable for comapnyName for outsourced part
     */
    private String companyName ;

    /**
     * sets the outsourced part
     * @param id sets id
     * @param name sets name
     * @param price sets price
     * @param stock sets stock
     * @param max sets max inv
     * @param min sets min inv
     * @param companyName sets company name for outsourced
     *
     * super needed because it is calling variables from Part.
     * companyName added individually because it is not in the Part class.
     *
     *  RUMTIME ERROR. inv max and min kept switching because i had max and min in different order on here from part.
     */
    public Outsourced(int id, String name, double price,int stock, int min,int max, String companyName) {
        super(id, name,price,stock,min,max);
        this.companyName = companyName;
    }


    /**
     * Gets companyName
     *
     * Had to have this above set otherwise it tried to return a void type and gave error
     */
    public String getCompanyName() {
        return companyName;
    }

    /** Sets companyName
     *
     * @param companyName
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
