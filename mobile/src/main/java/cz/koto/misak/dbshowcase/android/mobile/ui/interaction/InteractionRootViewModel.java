package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;

import android.databinding.Bindable;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import cz.kinst.jakub.view.StatefulLayout;
import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentInteractionRootBinding;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceSyncState;
import cz.koto.misak.dbshowcase.android.mobile.persistence.PersistenceType;
import cz.koto.misak.dbshowcase.android.mobile.ui.StateListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
import me.tatarka.bindingcollectionadapter.ItemView;
import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionRootViewModel extends BaseViewModel<cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentInteractionRootBinding>
		implements DataHandlerListener, StateListener {

	public final ItemViewSelector<InteractionCard> cardItemView = new BaseItemViewSelector<InteractionCard>() {
		@Override
		public void select(ItemView itemView, int position, InteractionCard item) {
			itemView.set(BR.viewModel, item.getPagerLayoutResource());
		}
	};
	public ObservableField<StatefulLayout.State> state = new ObservableField<>();
	ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	private List<InteractionCard> mCardItemList;


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		state.set(StatefulLayout.State.PROGRESS);
		ModelProvider.get().loadModel(this);
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
	public void handleSuccess() {
		state.set(StatefulLayout.State.CONTENT);
		schoolModel.set(ModelProvider.get().getSchoolModel());
		notifyPropertyChanged(BR.cardItemList);
		updateToolbar();
	}


	@Override
	public void handleFailed(Throwable throwable) {
		state.set(StatefulLayout.State.CONTENT);
		schoolModel.set(ModelProvider.get().getSchoolModel());
		notifyPropertyChanged(BR.cardItemList);
		updateToolbar();
	}


	@Override
	public void setProgress() {
		state.set(StatefulLayout.State.PROGRESS);
	}


	@Override
	public void setContent() {
		state.set(StatefulLayout.State.CONTENT);
	}


	@Bindable
	public List<? extends InteractionCard> getCardItemList() {
		ModelProvider modelProvider = ModelProvider.get();
		if(modelProvider.getSchoolModel() != null || !modelProvider.getSchoolModel().getSchoolItems().isEmpty()) {
			mCardItemList = Observable.fromIterable(modelProvider.getSchoolModel().getSchoolItems())
					.map(item -> InteractionItemViewModel.getInstance(item))
					.toList().blockingGet();
		}
		if(mCardItemList == null) mCardItemList = new ArrayList<>();
		mCardItemList.add(InteractionAddViewModel.getInstance(this, this));
		return mCardItemList;
	}


	private void updateToolbar() {
		PersistenceType activePersistenceType = ModelProvider.get().getActivePersistenceType();
		PersistenceSyncState activePersistenceSyncState = ModelProvider.get().getActivePersistenceSyncState();
		getNavigationManager().configureToolbar(getToolbar(),
				activePersistenceType == null ? null : ContextProvider.getString(activePersistenceType.getStringRes()),
				activePersistenceType == null ? null : activePersistenceType.getIconRes(),
				activePersistenceSyncState == null ? null : ContextProvider.getString(activePersistenceSyncState.getDescRes()),
				activePersistenceSyncState == null ? null : activePersistenceSyncState.getIconRes(),
				false);
	}
}
