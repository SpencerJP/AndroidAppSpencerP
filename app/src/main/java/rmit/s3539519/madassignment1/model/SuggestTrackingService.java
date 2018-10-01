package rmit.s3539519.madassignment1.model;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;

public class SuggestTrackingService extends JobService {
    Context context;
    @Override
    public boolean onStartJob(JobParameters params) {

        jobFinished(params, false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void suggestTracking() {

    }
}
