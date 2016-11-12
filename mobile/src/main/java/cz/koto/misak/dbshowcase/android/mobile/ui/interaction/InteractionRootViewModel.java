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
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionRootViewModel extends BaseViewModel<FragmentInteractionRootBinding> implements OnDataLoadedListener {

	public final ItemViewSelector<InteractionCard> cardItemView = new BaseItemViewSelector<InteractionCard>() {
		@Override
		public void select(ItemView itemView, int position, InteractionCard item) {
			itemView.set(BR.viewModel, item.getPagerLayoutResource());
		}
	};
	ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	private List<InteractionCard> mCardItemList;


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		ModelProvider.initModelFromApi(this);
	}


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		updateToolbar();
	}


	@Override
	public void onResume() {
		super.onResume();
		updateToolbar();
	}


	@Override
	public void loadSuccess() {
		schoolModel.set(ModelProvider.getSchoolModel());
		notifyPropertyChanged(BR.cardItemList);
	}


	@Bindable
	public List<? extends InteractionCard> getCardItemList() {
		if(ModelProvider.getSchoolModel() != null || !ModelProvider.getSchoolModel().getSchoolItems().isEmpty()) {
			mCardItemList = Observable.fromIterable(ModelProvider.getSchoolModel().getSchoolItems())
					.map(item -> InteractionItemViewModel.getInstance(item))
					.toList().blockingGet();
		}
		if(mCardItemList == null) mCardItemList = new ArrayList<>();
		mCardItemList.add(InteractionAddViewModel.getInstance());
		return mCardItemList;
	}


	private void updateToolbar() {
		getNavigationManager().configureToolbar(getToolbar(),
				ContextProvider.getString(ModelProvider.getPersistenceType().getStringRes()),
				ModelProvider.getPersistenceType() == null ? null : ModelProvider.getPersistenceType().getIconRes(),
				ModelProvider.getPersistenceSyncState() == null ? null : ModelProvider.getPersistenceSyncState().getIconRes(),
				false);
	}
}
