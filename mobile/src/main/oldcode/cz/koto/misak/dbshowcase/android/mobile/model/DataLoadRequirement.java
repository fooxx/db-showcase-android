package cz.koto.misak.dbshowcase.android.mobile.model;


public enum DataLoadRequirement {
    REQUIIRED_ONE(true),
    REQUIRED_ALL(true),
    NONE(false);

    private boolean load;

    DataLoadRequirement(boolean load) {
        this.load = load;
    }


    public boolean hasDataLoad() {
        return load;
    }
}