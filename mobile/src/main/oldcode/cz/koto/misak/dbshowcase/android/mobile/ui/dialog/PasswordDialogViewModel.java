package cz.koto.misak.dbshowcase.android.mobile.ui.dialog;

import android.databinding.ObservableField;
import android.support.v7.app.AlertDialog;

import cz.koto.misak.dbshowcase.android.mobile.ui.base.BaseViewModel;


public class PasswordDialogViewModel extends BaseViewModel {

	public ObservableField<String> password = new ObservableField();
	PasswordDialogListener mDialogListener;
	AlertDialog mAlertDialog;


	public PasswordDialogViewModel(AlertDialog dialog, PasswordDialogListener dialogListener) {
		this.mAlertDialog = dialog;
		this.mDialogListener = dialogListener;
	}


	public void onSubmit() {
		mAlertDialog.dismiss();
		mDialogListener.onSubmitPassword(password.get());
	}


	public void onCancel() {
		mAlertDialog.dismiss();
		mDialogListener.onCancelPassword();
	}
}
