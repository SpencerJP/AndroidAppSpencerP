package rmit.s3539519.madassignment1.view;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.controller.EditTracking;

public class EditTrackingButtonListener implements View.OnClickListener {

    private Activity context;
    private EditTrackable editView;
    public EditTrackingButtonListener(Context context, EditTrackable editView) {
        this.context = (Activity) context;
        this.editView = editView;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.edit_tracking_cancel_button) {
            context.finish();
        }
        if (v.getId() == R.id.edit_tracking_save_button) {

        }
    }
}
