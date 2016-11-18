package cz.koto.misak.dbshowcase.android.mobile.api;

public interface OnLoadResultListener {
	void loadSuccess();
	void loadFailed(Throwable throwable);
}
