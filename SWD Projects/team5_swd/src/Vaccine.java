/**
 * This class is used as a blueprint for Vaccine objects, which store information regarding an individuals (designated
 * by vaccinationID) vaccination information, including whether they have their first dose, second dose, and booster.
 *
 * @author Danny Bodin, John Elwart, Tucker Dickson
 * @version 1.0
 * @since 12/3/21
 */
public class Vaccine {
    private int vaccinationID;
    private boolean hasFirstDose;
    private boolean hasSecondDose;
    private boolean hasBooster;

    /**
     * Constructor, initialize all private member variables.
     *
     * @param vaccinationID Used to initialize vaccinationID.
     * @param hasFirstDose Used to initialize hasFirstDose.
     * @param hasSecondDoes Used to initialize hasSecondDose.
     * @param hasBooster Used to initialize hasBooster.
     */
    public Vaccine(int vaccinationID, boolean hasFirstDose, boolean hasSecondDoes, boolean hasBooster) {
        this.vaccinationID = vaccinationID;
        this.hasFirstDose = hasFirstDose;
        this.hasSecondDose = hasSecondDoes;
        this.hasBooster = hasBooster;
    }

    public int getVaccinationID() {
        return vaccinationID;

    }

    public void setVaccinationID(int vaccinationID) {
        this.vaccinationID = vaccinationID;
    }

    public boolean isHasFirstDose() {
        return hasFirstDose;
    }

    public void setHasFirstDose(boolean hasFirstDose) {
        this.hasFirstDose = hasFirstDose;
    }

    public boolean isHasSecondDose() {
        return hasSecondDose;
    }

    public void setHasSecondDose(boolean hasSecondDoes) {
        this.hasSecondDose = hasSecondDoes;
    }

    public boolean isHasBooster() {
        return hasBooster;
    }

    public void setHasBooster(boolean hasBooster) {
        this.hasBooster = hasBooster;
    }

    @Override
    public String toString() {
        return "Vaccine{" +
                "vaccinationID=" + vaccinationID +
                ", hasFirstDose=" + hasFirstDose +
                ", hasSecondDoes=" + hasSecondDose +
                ", hasBooster=" + hasBooster +
                '}';
    }
}
