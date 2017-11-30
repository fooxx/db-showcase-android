package cz.koto.misak.dbshowcase.android.mobile.persistence.dbflow.model;

import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;

import com.raizlabs.android.dbflow.structure.BaseModel;


public class BaseDbFlowModel extends BaseModel implements Observable
{

	private transient PropertyChangeRegistry mCallbacks;

	public BaseDbFlowModel() {
	}

	@Override
	public synchronized void addOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
		if (mCallbacks == null) {
			mCallbacks = new PropertyChangeRegistry();
		}
		mCallbacks.add(callback);
	}

	@Override
	public synchronized void removeOnPropertyChangedCallback(Observable.OnPropertyChangedCallback callback) {
		if (mCallbacks != null) {
			mCallbacks.remove(callback);
		}
	}

	/**
	 * Notifies listeners that all properties of this instance have changed.
	 */
	public synchronized void notifyChange() {
		if (mCallbacks != null) {
			mCallbacks.notifyCallbacks(this, 0, null);
		}
	}

	/**
	 * Notifies listeners that a specific property has changed. The getter for the property
	 * that changes should be marked with {@link Bindable} to generate a field in
	 * <code>BR</code> to be used as <code>fieldId</code>.
	 *
	 * @param fieldId The generated BR id for the Bindable field.
	 */
	public void notifyPropertyChanged(int fieldId) {
		if (mCallbacks != null) {
			mCallbacks.notifyCallbacks(this, fieldId, null);
		}
	}

}
