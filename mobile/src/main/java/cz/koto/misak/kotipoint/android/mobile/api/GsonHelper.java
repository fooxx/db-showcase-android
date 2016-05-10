package cz.koto.misak.kotipoint.android.mobile.api;

import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cz.koto.misak.kotipoint.android.mobile.db.dbflow.DbFlowExclusionStrategy;


public class GsonHelper
{
	private static Gson mGson;


	public static Gson getInstance()
	{
		if(mGson == null)
		{
			mGson = new GsonBuilder().disableHtmlEscaping()
					.setExclusionStrategies(new ExclusionStrategy[] {new DbFlowExclusionStrategy()}).create();
		}
		return mGson;
	}


	private GsonHelper()
	{
	}
}
