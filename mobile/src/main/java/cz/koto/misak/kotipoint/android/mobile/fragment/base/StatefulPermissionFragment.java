package cz.koto.misak.kotipoint.android.mobile.fragment.base;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cz.koto.misak.kotipoint.android.mobile.entity.AppPermissionEnum;
import cz.koto.misak.kotipoint.android.mobile.entity.DataLoadAbility;
import cz.koto.misak.kotipoint.android.mobile.entity.DataLoadRequirement;
import cz.koto.misak.kotipoint.android.mobile.entity.DataLoadType;
import cz.koto.misak.kotipoint.android.mobile.util.NetworkUtils;
import cz.koto.misak.kotipoint.android.mobile.util.ReloadViewObserver;
import cz.koto.misak.kotipoint.android.mobile.view.StatefulLayout;
import timber.log.Timber;

public abstract class StatefulPermissionFragment extends PermissionFragment {

    private StatefulLayout mStatefulLayout;

    protected StatefulLayout getFragmentView() {
        return (StatefulLayout) mFragmentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // setup stateful layout
        setupStatefulLayout(savedInstanceState, getFragmentView());

        if (savedInstanceState == null) {
            //Prevent unnecessary reloading!
            requestContent(savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ReloadViewObserver<Void> reloadViewObserver = new ReloadViewObserver<Void>() {
            @Override
            public void onCompleted() {
                reloadFragmentView();
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Unable to complete reloadViewObserver!");
            }
        };

        getFragmentView().setupObservers(reloadViewObserver);
    }

    private boolean isAbleLoadData(DataLoadAbility dataLoadAbility) {
        if ((dataLoadAbility == null) || (dataLoadAbility.getLoadRequirement() == null) || (dataLoadAbility.getLoadTypeArray() == null)) {
            Timber.e("Unexpected NULL for dataLoadAbility in isAbleLoadData()");
        }
        return ((dataLoadAbility != null) &&
                (dataLoadAbility.getLoadRequirement() != null) &&
                (dataLoadAbility.getLoadTypeArray() != null) &&
                (dataLoadAbility.getLoadRequirement() != DataLoadRequirement.NONE) &&
                (dataLoadAbility.getLoadTypeArray().length > 0) &&
                (dataLoadAbility.getLoadTypeArray()[0] != DataLoadType.NONE));
    }

    /**
     * Do request for content.
     * Permission request is called only when savedInstanceState is null.
     *
     * @param savedInstanceState
     */
    protected final void requestContent(Bundle savedInstanceState) {

        DataLoadAbility loadAbility = getDataLoadAbility();

        if (isAbleLoadData(loadAbility)) {
            boolean loadAbleOnline = false;
            boolean loadAbleOffline = false;
            showProgress();
            for (DataLoadType loadType : loadAbility.getLoadTypeArray()) {
                switch (loadType) {
                    case ONLINE:
                        loadAbleOnline = NetworkUtils.isOnline(getActivity());
                        break;
                    case OFFLINE:
                        loadAbleOffline = true;
                        break;
                    case NONE:
                    default:
                }
            }
            switch (loadAbility.getLoadRequirement()) {
                case REQUIIRED_ONE:
                    if (loadAbleOnline || loadAbleOffline) {
                        accessContent(savedInstanceState);
                    }else{
                        showOffline();
                    }
                    break;
                case REQUIRED_ALL:
                    if (loadAbleOnline && loadAbleOffline) {
                        accessContent(savedInstanceState);
                    }else{
                        showOffline();
                    }
                    break;
                case NONE:
                default:
                    Timber.w("Load ability without requirement defined!");
                    accessContent(savedInstanceState);
            }
        } else {
            accessContent(savedInstanceState);
        }
    }

    private void accessContent(Bundle savedInstanceState) {
    /*
     * Request all permissions defined in getMandatoryPermissionList and
     * call doWithMandatoryPermissions() after all of them are granted.
     */
        if (savedInstanceState == null) {
            requestMandatoryPermissions();
        } else {
            showContent();
            Timber.d("KoTiNode reloaded not necessary.");
        }
    }


    /**
     * Do request for content.
     * Permission request is called regardless the savedInstanceState.
     */
    protected final void requestContent() {
        requestContent(null);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveLayoutState(outState);
    }


    void setupStatefulLayout(Bundle savedInstanceState, StatefulLayout fragmentView) {
        // reference
        mStatefulLayout = fragmentView;


        // state change listener
        mStatefulLayout.setOnStateChangeListener(new StatefulLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(View v, StatefulLayout.State state) {
                Timber.d("***StateChange:%s", state);

                if (state == StatefulLayout.State.NOPERMISSION) {
                    Timber.i("***StateChange>>:%s", state);
                }
            }
        });

        // restore state
        mStatefulLayout.restoreInstanceState(savedInstanceState);
    }

    protected final void showOffline() {
        mStatefulLayout.showOffline();
    }

    protected final void showEmpty() {
        mStatefulLayout.showEmpty();
    }

    protected final void showContent() {
        mStatefulLayout.showContent();
    }

    protected final boolean isContentLayoutVisible() {
        return (mStatefulLayout.getState() == StatefulLayout.State.CONTENT);
    }

    protected final boolean isProgressLayoutVisible() {
        return (mStatefulLayout.getState() == StatefulLayout.State.PROGRESS);
    }

    protected final void showProgress() {
        mStatefulLayout.showProgress();
    }

    protected final void showNoPermission() {
        mStatefulLayout.showNoPermission();
    }

    void saveLayoutState(Bundle outState) {
        // stateful layout state
        if (mStatefulLayout != null) mStatefulLayout.saveInstanceState(outState);
    }

    @Override
    protected void mandatoryPermissionNotGranted() {
        showNoPermission();
    }


    @Override
    protected List<AppPermissionEnum> getMandatoryPermissionList() {
        List<AppPermissionEnum> ret = new ArrayList<>();
        ret.add(AppPermissionEnum.ACCESS_NETWORK_STATE);
        return ret;
    }

    /**
     * Let's define data load ability for the fragment.
     * <p>
     * Use DataLoadRequirement to define requirement politic.
     * Use DataLoadType to define all load types (the most important first)
     *
     * @return
     */
    @NonNull
    protected abstract DataLoadAbility getDataLoadAbility();

    public abstract void reloadFragmentView();
}
