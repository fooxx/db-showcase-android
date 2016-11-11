package cz.koto.misak.dbshowcase.android.mobile.persistence;


public enum DbShowcase {

    DB_FLOW,
    REALM_IO;


    public static DbShowcase parse(String dbShowcase){
        DbShowcase ret = null;
        for(DbShowcase dbShowcaseEnum: DbShowcase.values()) {
            if (dbShowcaseEnum.name().equals(dbShowcase)) {
                ret = dbShowcaseEnum;
                break;
            }
        }
        return ret;
    }
}
