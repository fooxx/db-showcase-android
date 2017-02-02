package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;

import android.databinding.Bindable;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.StateListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
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

	ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	private List<InteractionCard> mCardItemList;


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		getBinding().stateful.showProgress();
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
		getBinding().stateful.showProgress();
		ModelProvider.get().loadModel(this);
		notifyPropertyChanged(BR.cardItemList);
		updateToolbar();
	}


	@Override
	public void handleSuccess() {
		getBinding().stateful.showContent();
		schoolModel.set(ModelProvider.get().getSchoolModel());
		notifyPropertyChanged(BR.cardItemList);
		updateToolbar();
	}


	@Override
	public void handleFailed(Throwable throwable) {
		getBinding().stateful.showContent();
		schoolModel.set(ModelProvider.get().getSchoolModel());
		notifyPropertyChanged(BR.cardItemList);
		updateToolbar();
	}


	@Override
	public void setProgress() {
		getBinding().stateful.showProgress();
	}


	@Override
	public void setContent() {
		getBinding().stateful.showContent();
	}


	@Bindable
	public List<? extends InteractionCard> getCardItemList() {
		ModelProvider modelProvider = ModelProvider.get();
		if(modelProvider.getSchoolModel() != null || !modelProvider.getSchoolModel().getSchoolItems().isEmpty()) {
			mCardItemList = Observable.fromIterable(modelProvider.getSchoolModel().getSchoolItems())
					.map(item -> InteractionItemViewModel.getInstance(getContext(), item))
					.toList().blockingGet();
		}
		if(mCardItemList == null) mCardItemList = new ArrayList<>();
		mCardItemList.add(InteractionAddCardViewModel.getInstance(this, this, this));
		return mCardItemList;
	}


}
