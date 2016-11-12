package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.ArrayList;
import java.util.List;


public class SchoolModel {

	private List<SchoolClassInterface> list = new ArrayList<>();


	public void setList(List<SchoolClassInterface> list) {
		this.list.addAll(list);
	}


	public final List<SchoolClassInterface> getSchoolItems() {
		return list;
	}

}
