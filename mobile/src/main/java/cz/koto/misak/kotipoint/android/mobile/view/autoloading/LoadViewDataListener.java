package cz.koto.misak.kotipoint.android.mobile.view.autoloading;

/**
 * LoadViewDataListener serve for View (e.g. AutoLoadingRecyclerView) as fallback for the fragment
 * to invoke specific loading action.
 *
 */
public interface LoadViewDataListener {

    void doLoadingOnline();

    void doLoadingOffline();
}
