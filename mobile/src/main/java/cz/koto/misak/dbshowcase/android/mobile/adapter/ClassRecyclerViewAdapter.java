package cz.koto.misak.dbshowcase.android.mobile.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.koto.misak.dbshowcase.android.mobile.R;
import cz.koto.misak.dbshowcase.android.mobile.databinding.ListItemBinding;
import cz.koto.misak.dbshowcase.android.mobile.entity.entityinterface.SchoolClassInterface;
import cz.koto.misak.dbshowcase.android.mobile.listener.OnClassItemClickListener;

import static android.view.LayoutInflater.from;
import static android.databinding.DataBindingUtil.inflate;


public class ClassRecyclerViewAdapter extends RecyclerView.Adapter<android.support.v7.widget.RecyclerView.ViewHolder>
{
	private static final int VIEW_TYPE_USER = 0;

	private List<SchoolClassInterface> mClassList;
	private final OnClassItemClickListener mOnItemClickListener;


	public ClassRecyclerViewAdapter(List<SchoolClassInterface> schoolClassList, OnClassItemClickListener onItemClickListener)
	{
		this.mOnItemClickListener = onItemClickListener;
		this.mClassList = schoolClassList;
	}


	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		LayoutInflater inflater = from(parent.getContext());

		switch(viewType)
		{
			case VIEW_TYPE_USER:
				ListItemBinding binding = inflate(inflater, R.layout.list_item, parent, false);
				return new ClassViewHolder(binding.getRoot(), binding, mOnItemClickListener);
			default:
				throw new RuntimeException("No such type found");
		}
	}


	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
	{
		if(holder instanceof ClassViewHolder)
		{
			SchoolClassInterface entity = this.mClassList.get(position);

			if(entity != null)
			{
				((ClassViewHolder) holder).bindData(entity);
			}
		}
	}


	@Override
	public int getItemCount()
	{
		return this.mClassList.size();
	}


	public SchoolClassInterface getItem(int position)
	{
		return mClassList.get(position);
	}


	public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
	{
		private ListItemBinding mBinding;
		private OnClassItemClickListener mListener;

		public ClassViewHolder(final View itemView, ListItemBinding binding, final OnClassItemClickListener listener)
		{
			super(itemView);

			mBinding = binding;
			mListener = listener;

			itemView.setOnClickListener(this);
			mBinding.addStudent.setOnClickListener(this);
			mBinding.addTeacher.setOnClickListener(this);
			mBinding.removeStudent.setOnClickListener(this);
			mBinding.removeTeacher.setOnClickListener(this);
		}


		@Override
		public void onClick(View v)
		{
			final int adapterPosition = getAdapterPosition();

			if(adapterPosition >= 0)
			{
				switch(v.getId()) {
					case R.id.add_student:
						mListener.onAddStudentClick(adapterPosition);
						break;
					case R.id.add_teacher:
						mListener.onAddTeacherClick(adapterPosition);
						break;
					case R.id.remove_student:
						mListener.onRemoveStudentClick(adapterPosition);
						break;
					case R.id.remove_teacher:
						mListener.onRemoveTeacherClick(adapterPosition);
						break;
				}

			}
		}


		public void bindData(SchoolClassInterface entity)
		{
			mBinding.className.setText(entity.getName());
			mBinding.teachers.setText(entity.getTeacherListString());
			mBinding.students.setText(entity.getStudentListString());
			mBinding.executePendingBindings();
		}
	}


	public void refill(List<SchoolClassInterface> list) {
		mClassList = list;
		notifyDataSetChanged();
	}
}
