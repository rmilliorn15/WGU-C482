package model;

/**
 * @author Richard Milliorn
 * Wgu C482
 *
 * Has to extend part so that it can get info from part class.
 *
 * copied and pasted from outsourced and modified.
 */
public class Inhouse extends Part {

    /**
     * Variable for machineId for In-house part
     */
    private int machineId ;

    /**
     * sets the inhouse part
     * @param id sets id
     * @param name sets name
     * @param price sets price
     * @param stock sets stock
     * @param max sets max inv
     * @param min sets min inv
     * @param machineId sets machineId for In-house part
     *
     * super needed because it is calling variables from Part.
     * machineId added individually because it is not in the Part class.
     *  RUMTIME ERROR. inv max and min kept switching because i had max and min in different order on here from part.
     */
    public Inhouse(int id, String name, double price,int stock, int min,int max, int machineId) {
        super(id, name,price,stock,min,max);
        this.machineId = machineId;
    }


    /**
     * Gets machineId
     *
     * Had to have this above set otherwise it tried to return a void type and gave error
     * @return
     */
    public int getMachineId() {
        return machineId;
    }


    /** Sets machineId
     *
     * @param machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

}
