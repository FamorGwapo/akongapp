import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.group_shuffler.R;

import java.util.ArrayList;
import java.util.Collections;

/** @noinspection ALL*/
public class MainActivity extends AppCompatActivity {

    EditText playersInput, groupsInput;
    TextView resultText, ratingText;
    Button assignButton, rateButton;

    ArrayList<ArrayList<String>> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playersInput = findViewById(R.id.playersInput);
        groupsInput = findViewById(R.id.groupsInput);
        resultText = findViewById(R.id.resultText);
        ratingText = findViewById(R.id.ratingText);
        assignButton = findViewById(R.id.assignButton);
        rateButton = findViewById(R.id.rateButton);

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignPlayersIntoGroups();
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateGroups();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void assignPlayersIntoGroups() {
        String playersStr = playersInput.getText().toString().trim();
        String groupsStr = groupsInput.getText().toString().trim();

        if (playersStr.isEmpty() || groupsStr.isEmpty()) {
            resultText.setText("Please enter both players and groups.");
            return;
        }

        String[] playersArray = playersStr.split(",");
        int numPlayers = playersArray.length;

        if (numPlayers == 0) {
            resultText.setText("Please enter at least one player.");
            return;
        }

        int numGroups = Integer.parseInt(groupsStr);

        if (numGroups <= 0) {
            resultText.setText("Number of groups must be greater than zero.");
            return;
        }

        ArrayList<String> playersList = new ArrayList<>();
        Collections.addAll(playersList, playersArray);

        Collections.shuffle(playersList);

        int playersPerGroup = numPlayers / numGroups;

        groups = new ArrayList<>();

        StringBuilder resultBuilder = new StringBuilder();

        for (int i = 0; i < numGroups; i++) {
            ArrayList<String> group = new ArrayList<>();
            resultBuilder.append("Group ").append(i + 1).append(": ");
            for (int j = 0; j < playersPerGroup; j++) {
                int playerIndex = i * playersPerGroup + j;
                if (playerIndex < playersList.size()) {
                    group.add(playersList.get(playerIndex));
                    resultBuilder.append(playersList.get(playerIndex)).append(", ");
                }
            }
            resultBuilder.delete(resultBuilder.length() - 2, resultBuilder.length()); // Remove the last comma and space
            resultBuilder.append("\n");
            groups.add(group);
        }

        resultText.setText(resultBuilder.toString());
        ratingText.setText("");
    }

    private void rateGroups() {
        if (groups == null) {
            ratingText.setText("Please assign groups first.");
            return;
        }

        StringBuilder ratingBuilder = new StringBuilder();
        ratingBuilder.append("Group Ratings:\n");

        for (int i = 0; i < groups.size(); i++) {
            ratingBuilder.append("Group ").append(i + 1).append(": ");
            // Simulating random ratings for each group (1-5 stars)
            int rating = (int) (Math.random() * 5) + 1;
            ratingBuilder.append(rating).append(" stars\n");
        }

        ratingText.setText(ratingBuilder.toString());
    }
}
