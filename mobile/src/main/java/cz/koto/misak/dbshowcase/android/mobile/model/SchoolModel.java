package cz.koto.misak.dbshowcase.android.mobile.model;

import java.util.ArrayList;
import java.util.List;


public class SchoolModel {

	private List<SchoolClassInterface> schoolItems = new ArrayList<>();


	public final List<? extends SchoolClassInterface> getSchoolItems() {
		return schoolItems;
	}


	public void setSchoolItems(List<? extends SchoolClassInterface> list) {
		if((list == null) || (list.size() == 0)) return;
		this.schoolItems.clear();
		this.schoolItems.addAll(list);
	}


	public void addSchoolItems(List<? extends SchoolClassInterface> list) {
		if((list == null) || (list.size() == 0)) return;
		this.schoolItems.addAll(list);
	}
}
