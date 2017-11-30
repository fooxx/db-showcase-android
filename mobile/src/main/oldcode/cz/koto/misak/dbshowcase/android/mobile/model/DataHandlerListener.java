package cz.koto.misak.dbshowcase.android.mobile.model;

public interface DataHandlerListener {
	void handleSuccess();
	void handleFailed(Throwable throwable);
}
