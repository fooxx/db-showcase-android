package cz.koto.misak.dbshowcase.android.mobile.ui.interaction;


import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;


public class ListScrollController {
	RecyclerView mRecyclerView;


	@BindingAdapter("scrollController")
	public static void setController(RecyclerView recyclerView, ListScrollController controller) {
		controller.setRecyclerView(recyclerView);
	}


	public void setRecyclerView(RecyclerView recyclerView) {
		mRecyclerView = recyclerView;
	}


	public void smoothScrollToPosition(int position) {
		mRecyclerView.smoothScrollToPosition(position);
	}


	public void smoothScrollToEnd() {
		mRecyclerView.smoothScrollToPosition(mRecyclerView.getAdapter().getItemCount());
	}
}
