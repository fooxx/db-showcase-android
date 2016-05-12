package cz.koto.misak.dbshowcase.android.mobile.listener;

public interface OnClassItemClickListener
{
	void onAddStudentClick(int position);
	void onAddTeacherClick(int position);
	void onRemoveStudentClick(int position);
	void onRemoveTeacherClick(int position);
}
