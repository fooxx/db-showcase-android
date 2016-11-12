package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;

import android.databinding.Bindable;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.api.OnDataLoadedListener;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentInteractionRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionRootViewModel extends BaseViewModel<FragmentInteractionRootBinding> implements OnDataLoadedListener {

	public final ItemViewSelector<InteractionItemViewModel> cardItemView = new BaseItemViewSelector<InteractionItemViewModel>() {
		@Override
		public void select(ItemView itemView, int position, InteractionItemViewModel item) {
			itemView.set(BR.viewModel, item.getPagerLayoutResource());
		}
	};
	ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	private List<InteractionItemViewModel> mCardItemList;


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		ModelProvider.initModelFromApi(this);
	}


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		getNavigationManager().configureToolbar(getToolbar(), "DB", false);
	}


	@Override
	public void loadSuccess() {
		schoolModel.set(ModelProvider.getSchoolModel());
		notifyPropertyChanged(BR.cardItemList);
	}


	@Bindable
	public List<InteractionItemViewModel> getCardItemList() {
		if(ModelProvider.getSchoolModel() == null || ModelProvider.getSchoolModel().getSchoolItems().isEmpty())
			return new ArrayList<>();

		mCardItemList = Observable.fromIterable(ModelProvider.getSchoolModel().getSchoolItems())
				.map(item -> new InteractionItemViewModel(item))
				.toList().blockingGet();
		if(mCardItemList == null) return new ArrayList<>();
		return mCardItemList;
	}
}
