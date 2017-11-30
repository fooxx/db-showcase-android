package cz.koto.misak.dbshowcase.android.mobile.persistence.realm.utility;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cz.koto.misak.dbshowcase.android.mobile.utility.ContextProvider;


public class RealmMigrationUtility {

	/**
	 * This copying from the res/raw resource directory to filesDir is not necessary since realm has
	 * configuration option .assetFile
	 * But it could be good to use this method still for the testing purpose.
	 *
	 * @param inputStream
	 * @param outFileName
	 * @return
	 */
	public static String copyBundledRealmFile(InputStream inputStream,
											  String outFileName) {
		try {
			File file = new File(ContextProvider.getContext().getFilesDir(), outFileName);
			FileOutputStream outputStream = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int bytesRead;
			while((bytesRead = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, bytesRead);
			}
			outputStream.close();
			return file.getAbsolutePath();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
