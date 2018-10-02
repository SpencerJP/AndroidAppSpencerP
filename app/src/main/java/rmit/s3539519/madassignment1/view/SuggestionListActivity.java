package rmit.s3539519.madassignment1.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Map;

import rmit.s3539519.madassignment1.R;
import rmit.s3539519.madassignment1.model.Suggestion;
import rmit.s3539519.madassignment1.model.services.Observer;
import rmit.s3539519.madassignment1.view.viewmodels.SuggestionAdapter;

public class SuggestionListActivity extends AppCompatActivity {

    private Map<Integer, Suggestion> suggestions;
    private Observer observer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_list);
        RecyclerView suggestionRecyclerView = findViewById(R.id.suggestionRecyclerView);
        observer = Observer.getSingletonInstance(this);
        suggestions = observer.getSuggestions();


        // TODO REMOVE
        Suggestion s1 = new Suggestion(observer.getTrackableById(1), 300, 500);
        Suggestion s2 = new Suggestion(observer.getTrackableById(2), 800, 600);
        observer.addSuggestion(s1);
        observer.addSuggestion(s2);


        suggestionRecyclerView.setHasFixedSize(true);

        LinearLayoutManager trackableLayoutManager = new LinearLayoutManager(this);
        suggestionRecyclerView.setLayoutManager(trackableLayoutManager);
        suggestionRecyclerView.setItemAnimator(new DefaultItemAnimator());

        SuggestionAdapter suggestionAdapter = new SuggestionAdapter(this, suggestions);

        suggestionRecyclerView.setAdapter(suggestionAdapter);
        Observer.getSingletonInstance(this).setSuggestionAdapter(suggestionAdapter);
    }
}
