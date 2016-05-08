package cz.koto.misak.kotipoint.android.mobile.entity;


public enum DataLoadRequirement {
    REQUIIRED_ONE(true),
    REQUIRED_ALL(true),
    NONE(false);

    DataLoadRequirement(boolean load) {
        this.load = load;
    }

    private boolean load;

    public boolean hasDataLoad() {
        return load;
    }
}