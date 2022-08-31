package com.cubes.komentar.pavlovic.ui.comments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivityPostCommentBinding;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.RequestComment;
import com.cubes.komentar.pavlovic.di.AppContainer;
import com.cubes.komentar.pavlovic.di.MyApplication;

public class PostCommentActivity extends AppCompatActivity {

    private ActivityPostCommentBinding binding;
    private String news;
    private String reply_id;
    private DataRepository dataRepository;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPostCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        news = getIntent().getExtras().getString("news");
        reply_id = getIntent().getExtras().getString("reply_id");

        String name = binding.name.getText().toString();
        String email = binding.mail.getText().toString();
        String content = binding.content.getText().toString();

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        dataRepository = appContainer.dataRepository;

        if (reply_id == null) {
            binding.commentSend.setText("Postavi komentar");
        } else {
            binding.commentSend.setText("Odgovori na komentar");
        }

        binding.content.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEND) {
                if (reply_id == null) {
                    postComment(name, email, content);
                    hideKeyboard(PostCommentActivity.this);
                    Log.d("COMPOST", "RADIPOST");
                } else {
                    replyComment(name, email, content);
                    hideKeyboard(PostCommentActivity.this);
                    Log.d("COMPOST", "RADIREPLY");
                }
            }
            return false;
        });

        binding.commentSend.setOnClickListener(view12 -> {
            if (binding.name.getText().toString().isEmpty() ||
                    binding.mail.getText().toString().isEmpty() ||
                    binding.content.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Popunite sva polja", Toast.LENGTH_LONG).show();
            } else {

                if (reply_id == null) {
                    postComment(name, email, content);
                    Log.d("COMPOST", "RADIPOST");
                } else {
                    replyComment(name, email, content);
                    Log.d("COMPOST", "RADIREPLY");
                }

            }
        });

        binding.imageClose.setOnClickListener(view1 -> finish());
    }

    public void replyComment(String name, String email, String content) {

        dataRepository.replyComment(news, reply_id, name, email, content, new DataRepository.PostRequestListener() {
            @Override
            public void onResponse(RequestComment.RequestBody response) {
                binding.name.setText("");
                binding.mail.setText("");
                binding.content.setText("");
                Toast.makeText(getApplicationContext(), "RADI ODGOVOR!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Došlo je do greške!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void postComment(String name, String email, String content) {

        dataRepository.postComment(news, name, email, content, new DataRepository.PostRequestListener() {
            @Override
            public void onResponse(RequestComment.RequestBody response) {
                binding.name.setText("");
                binding.mail.setText("");
                binding.content.setText("");
                Toast.makeText(getApplicationContext(), "RADI POSTAVLJANJE!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Došlo je do greške!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}