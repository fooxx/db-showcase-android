package cz.koto.misak.dbshowcase.android.mobile.model;


public class DataLoadAbility {

    private DataLoadType[] loadTypeArray;

    private DataLoadRequirement loadRequirement;

    private DataLoadAbility() {
    }

    /**
     * @param loadRequirement - define requirement politic
     * @param loadTypeArray - define all load types (the most important first)
     */
    public DataLoadAbility(DataLoadRequirement loadRequirement, DataLoadType... loadTypeArray) {
        this.loadTypeArray = loadTypeArray;
        this.loadRequirement = loadRequirement;
    }

    public DataLoadType[] getLoadTypeArray() {
        return loadTypeArray;
    }

    public DataLoadRequirement getLoadRequirement() {
        return loadRequirement;
    }
}
