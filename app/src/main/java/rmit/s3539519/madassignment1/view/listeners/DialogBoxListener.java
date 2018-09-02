package rmit.s3539519.madassignment1.view.listeners;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.Observer;

public class DialogBoxListener implements DialogInterface.OnClickListener {

    private String type;
    private Context context;
    private int trackingId;
    public DialogBoxListener(Context context, int trackingId, String type) {
        this.type = type;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (type.equals("positive")) {
            Observer.getSingletonInstance(context).removeTracking(trackingId);
            Toast.makeText(context, R.string.tracking_deleted,
                    Toast.LENGTH_SHORT).show();
        }
        if (type.equals("negative")) {
            dialog.cancel();
        }
    }
}