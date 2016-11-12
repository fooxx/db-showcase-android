package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;


public class InteractionItemViewModel {

	private SchoolClassInterface mSchoolModelItem;


	public InteractionItemViewModel(SchoolClassInterface schoolModelItem) {
		mSchoolModelItem = schoolModelItem;
	}


	public int getPagerLayoutResource() {
		return R.layout.item_interaction;
	}


	public String getName() {
		if(mSchoolModelItem == null)
			return "";
		return mSchoolModelItem.getName();
	}

}
