package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.StateListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter2.OnItemBind;
//import me.tatarka.bindingcollectionadapter.BaseItemViewSelector;
//import me.tatarka.bindingcollectionadapter.ItemView;
//import me.tatarka.bindingcollectionadapter.ItemViewSelector;


public class InteractionRootViewModel extends BaseViewModel<cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentInteractionRootBinding>
		implements DataHandlerListener, StateListener {

//	public final ItemViewSelector<InteractionCard> cardItemView = new BaseItemViewSelector<InteractionCard>() {
//		@Override
//		public void select(ItemView itemView, int position, InteractionCard item) {
//			itemView.set(BR.viewModel, item.getPagerLayoutResource());
//		}
//	};


	public final OnItemBind<InteractionCard> cardItemView = (itemBinding, position, item) -> itemBinding.set(BR.viewModel, item.getPagerLayoutResource());


	public final ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	public final ObservableList<InteractionCard> cardItemList = new ObservableArrayList<>();


	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		//TODO getBinding().stateful.showProgress();
		ModelProvider.get().loadModel(this);
	}


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
		//TODO updateToolbar();
	}


	@Override
	public void onResume() {
		super.onResume();
		//TODO getBinding().stateful.showProgress();
		ModelProvider.get().loadModel(this);
		refreshCardItemList();
		//TODO updateToolbar();
	}


	@Override
	public void handleSuccess() {
		//TODO getBinding().stateful.showContent();
		schoolModel.set(ModelProvider.get().getSchoolModel());
		refreshCardItemList();
		//TODO updateToolbar();
	}


	@Override
	public void handleFailed(Throwable throwable) {
		//TODO getBinding().stateful.showContent();
		schoolModel.set(ModelProvider.get().getSchoolModel());
		refreshCardItemList();
		//TODO updateToolbar();
	}


	@Override
	public void setProgress() {
		//TODO getBinding().stateful.showProgress();
	}


	@Override
	public void setContent() {
		//TODO getBinding().stateful.showContent();
	}


	public void refreshCardItemList() {
		ModelProvider modelProvider = ModelProvider.get();
		if(modelProvider.getSchoolModel() != null || !modelProvider.getSchoolModel().getSchoolItems().isEmpty()) {
			cardItemList.addAll(Observable.fromIterable(modelProvider.getSchoolModel().getSchoolItems())
					.map(item -> InteractionItemViewModel.getInstance(getContext(), item))
					.toList().blockingGet());
		}
		cardItemList.add(InteractionAddCardViewModel.getInstance(this, this, this));
	}


}
