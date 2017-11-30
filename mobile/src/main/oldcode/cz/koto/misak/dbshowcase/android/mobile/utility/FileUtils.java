package cz.koto.misak.dbshowcase.android.mobile.utility;


import java.io.File;

import timber.log.Timber;


public class FileUtils {

	public static long folderSize(File directory) {
		Timber.d("Getting size of folder %s", directory);
		long length = 0;
		if(directory == null || directory.listFiles() == null)
			return length;
		for(File file : directory.listFiles()) {
			Timber.d("File: %s", file);
			if(file.isFile())
				length += file.length();
			else
				length += folderSize(file);
		}
		return length;
	}


	public static String humanReadableByteCount(long bytes, boolean si) {
		int unit = si ? 1000 : 1024;
		if(bytes < unit) return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "i");
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
}
