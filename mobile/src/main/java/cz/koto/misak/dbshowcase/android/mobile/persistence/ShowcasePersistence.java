package cz.koto.misak.dbshowcase.android.mobile.persistence;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.model.DataHandlerListener;
import cz.koto.misak.dbshowcase.android.mobile.model.SchoolClassInterface;


public interface ShowcasePersistence {

	/**
	 * Persist complete model.
	 * Persistence should run that transaction on a background thread and report back when the transaction is done.
	 *
	 * @param schoolModel
	 * @param dataHandlerListener
	 */
	public void saveOrUpdateSchoolClass(List<? extends SchoolClassInterface> schoolModel,
										DataHandlerListener dataHandlerListener);
}
