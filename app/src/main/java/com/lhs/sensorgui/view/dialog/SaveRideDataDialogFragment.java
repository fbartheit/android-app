package com.lhs.sensorgui.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.lhs.sensorgui.R;
import com.lhs.sensorgui.app.TorqueApp;

/**
 * Created by Dragan on 9/29/2016.
 */
public class SaveRideDataDialogFragment extends DialogFragment {


    public SaveRideDataDialogFragment(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Save ride data?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    app().saveRideDataToCSVFile();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                })
                .setCancelable(false);
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private TorqueApp app(){
        return TorqueApp.getInstance(getContext());
    }
}