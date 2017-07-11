package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.view.LayoutInflater;

import cz.kinst.jakub.view.SimpleStatefulLayout;
import cz.kinst.jakub.view.StatefulLayout;
import cz.koto.misak.dbshowcase.android.mobile.BR;
import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.ModelProvider;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolModel;
import cz.koto.misak.dbshowcase.android.mobile.ui.StateListener;
import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;
import cz.koto.misak.dbshowcase.android.mobile.utility.ApplicationEvent;
import cz.koto.misak.dbshowcase.android.mobile.utility.EventsUtilityKt;
import io.reactivex.Observable;
import me.tatarka.bindingcollectionadapter2.OnItemBind;


public class InteractionRootViewModel extends BaseViewModel<cz.koto.misak.dbshowcase.android.mobile.databinding.FragmentInteractionRootBinding>
		implements DataHandlerListener, StateListener {



	public final OnItemBind<InteractionCard> cardItemView = (itemBinding, position, item) -> itemBinding.set(BR.viewModel, item.getPagerLayoutResource());


	public final ObservableField<SchoolModel> schoolModel = new ObservableField<>();
	public final ObservableList<InteractionCard> cardItemList = new ObservableArrayList<>();

	public StatefulLayout.StateController stateController = null;

	@Override
	public void onViewModelCreated() {
		super.onViewModelCreated();
		stateController = StatefulLayout.StateController.create()
				.withState(SimpleStatefulLayout.State.PROGRESS, LayoutInflater.from(getActivity()).inflate(R.layout.include_progress, null))
				.build();
		ModelProvider.get().loadModel(this);
	}


	@Override
	public void onViewAttached(boolean firstAttachment) {
		super.onViewAttached(firstAttachment);
	}


	@Override
	public void onResume() {
		super.onResume();
		setProgress();
		ModelProvider.get().loadModel(this);
		refreshCardItemList();
	}


	@Override
	public void handleSuccess() {
		refreshCardItemList();
		schoolModel.set(ModelProvider.get().getSchoolModel());
		setContent();
		EventsUtilityKt.getApplicationEvents().onNext(ApplicationEvent.StateUpdateRequest.INSTANCE);
	}


	@Override
	public void handleFailed(Throwable throwable) {
		schoolModel.set(ModelProvider.get().getSchoolModel());
		refreshCardItemList();
		setContent();
		EventsUtilityKt.getApplicationEvents().onNext(ApplicationEvent.StateUpdateRequest.INSTANCE);
	}


	@Override
	public void setProgress() {
		stateController.setState(SimpleStatefulLayout.State.PROGRESS);
	}


	@Override
	public void setContent() {
		stateController.setState(SimpleStatefulLayout.State.CONTENT);
	}


	public void refreshCardItemList() {
		ModelProvider modelProvider = ModelProvider.get();
		if(modelProvider.getSchoolModel() != null || !modelProvider.getSchoolModel().getSchoolItems().isEmpty()) {
			cardItemList.clear();
			cardItemList.addAll(Observable.fromIterable(modelProvider.getSchoolModel().getSchoolItems())
					.map(item -> InteractionItemViewModel.getInstance(getContext(), item))
					.toList().blockingGet());
			cardItemList.add(InteractionAddCardViewModel.getInstance(this, this, this));

		}
	}


}
