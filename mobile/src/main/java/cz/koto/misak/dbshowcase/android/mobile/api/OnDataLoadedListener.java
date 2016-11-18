package cz.koto.misak.dbshowcase.android.mobile.api;

public interface OnDataLoadedListener {
	void loadSuccess();
	void loadFailed(Throwable throwable);
}
