package com.cubes.komentar.pavlovic.ui.comments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cubes.komentar.databinding.ActivityPostCommentBinding;
import com.cubes.komentar.pavlovic.data.source.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.source.response.ResponseCommentSend;


public class PostComment extends AppCompatActivity {

    private ActivityPostCommentBinding binding;
    private String news;
    private String reply_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPostCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //news = getIntent().getExtras().getString("news");
        //reply_id = getIntent().getExtras().getString("reply_id");

        String name = binding.textViewName.getText().toString();
        String email = binding.textMail.getText().toString();
        String content = binding.content.getText().toString();

        binding.textViewName.post(() -> {
            binding.textViewName.requestFocus();
            InputMethodManager i = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            i.showSoftInput(binding.textViewName, InputMethodManager.SHOW_IMPLICIT);
        });

        binding.content.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEND) {
                postComment(name, email, content);
                hideKeyboard(PostComment.this);
                return true;
            }
            return false;
        });

        binding.commentSend.setOnClickListener(view12 -> {
            if (binding.textViewName.getText().toString().isEmpty() ||
                    binding.textMail.getText().toString().isEmpty() ||
                    binding.content.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Popunite sva polja", Toast.LENGTH_LONG).show();
            } else {
                postComment(name, email, content);
            }
        });

        binding.imageClose.setOnClickListener(view1 -> finish());
    }

    public void postComment(String name, String email, String content) {

        DataRepository.getInstance().postComment(String.valueOf(news), name, email, content, new DataRepository.PostResponseListener() {
            @Override
            public void onResponse(ResponseCommentSend.ResponseBody response) {
                binding.textViewName.setText("");
                binding.textMail.setText("");
                binding.content.setText("");
                Toast.makeText(getApplicationContext(), "RADI POSTAVLJANJE!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), "Doslo je do greske!", Toast.LENGTH_SHORT).show();
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
