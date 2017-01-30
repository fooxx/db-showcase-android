package cz.koto.misak.dbshowcase.android.mobile.utility;


public class ByteUtility {

	/**
	 * Duplicate sourceByteArray and concat duplicate and original together.
	 *
	 * @param sourceByteArray
	 * @return two identical concatenated arrays.
	 */
	public static byte[] doubleSizeBytes(byte[] sourceByteArray) {
		byte[] doubleSizedByteArray = new byte[sourceByteArray.length * 2];
		System.arraycopy(sourceByteArray, 0, doubleSizedByteArray, 0, sourceByteArray.length);
		System.arraycopy(sourceByteArray, 0, doubleSizedByteArray, sourceByteArray.length, sourceByteArray.length);
		return doubleSizedByteArray;
	}
}
