package rmit.s3539519.madassignment1.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import rmit.s3539519.madassignment1.model.services.Observer;

public class DialogBoxListener implements DialogInterface.OnClickListener {

    private String type;
    private Context context;
    private int trackingId;
    public DialogBoxListener(Context context, int trackingId, String type) {
        this.trackingId = trackingId;
        this.context = context;
        this.type = type;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (type.equals("positive")) {
            Observer.getSingletonInstance(context).removeTracking(trackingId);
            Toast.makeText(context, "The tracking was deleted.", Toast.LENGTH_SHORT).show();
        }
        if (type.equals("negative")) {
            dialog.cancel();
        }
    }
}
