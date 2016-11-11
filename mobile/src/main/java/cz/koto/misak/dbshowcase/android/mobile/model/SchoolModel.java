package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.ArrayList;
import java.util.List;


public class SchoolModel {

	private static final List<SchoolClassInterface> list = new ArrayList<>();


	public static final List<SchoolClassInterface> getModel() {
		return list;
	}

}
