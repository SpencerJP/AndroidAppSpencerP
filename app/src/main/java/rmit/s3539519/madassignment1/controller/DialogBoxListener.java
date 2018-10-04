package rmit.s3539519.madassignment1.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import rmit.s3539519.madassignment1.model.services.Observer;

public class DialogBoxListener implements DialogInterface.OnClickListener {

    private String type;
    private Context context;
    private int id;
    public DialogBoxListener(Context context, int id, String type) {
        this.id = id;
        this.context = context;
        this.type = type;
    }
    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (type.equals("positive")) {
            Observer.getSingletonInstance(context).removeTracking(id);
            Toast.makeText(context, "The tracking was deleted.", Toast.LENGTH_SHORT).show();
        }
        if (type.equals("negative")) {
            dialog.cancel();
        }

        if (type.equals("positive_suggestion_add")) {
            Observer.getSingletonInstance(context).addTrackingFromSuggestion(id);
            Toast.makeText(context, "The suggestion was deleted.", Toast.LENGTH_SHORT).show();
        }
        if (type.equals("negative_suggestion_add")) {
            dialog.cancel();
        }

        if (type.equals("positive_suggestion_delete")) {
            Observer.getSingletonInstance(context).removeSuggestion(id);
            Toast.makeText(context, "The suggestion was deleted.", Toast.LENGTH_SHORT).show();
        }
        if (type.equals("negative_suggestion_delete")) {
            dialog.cancel();
        }
    }
}
