package com.cubes.komentar.pavlovic.ui.comments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cubes.komentar.databinding.ActivityReplyCommentBinding;
import com.cubes.komentar.pavlovic.data.repository.DataRepository;
import com.cubes.komentar.pavlovic.data.response.ResponseCommentSend;

public class ReplyCommentActivity extends AppCompatActivity {

    private ActivityReplyCommentBinding binding;
    private String news;
    private String reply_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReplyCommentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        news = getIntent().getExtras().getString("news");
        reply_id = getIntent().getExtras().getString("reply_id");

        String name = binding.textViewName.getText().toString();
        String email = binding.textMail.getText().toString();
        String content = binding.textViewContent.getText().toString();

        binding.commentSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.textViewName.getText().toString().isEmpty() ||
                        binding.textMail.getText().toString().isEmpty() ||
                        binding.textViewContent.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Popunite sva polja", Toast.LENGTH_LONG).show();
                } else {
                    replyComment(name, email, content);
                }
            }
        });

        binding.imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void replyComment(String name, String email, String content) {

        DataRepository.getInstance().replyComment(String.valueOf(news),String.valueOf(reply_id), name, email, content, new DataRepository.PostResponseListener() {
            @Override
            public void onResponse(ResponseCommentSend.ResponseBody response) {
                binding.textViewName.setText("");
                binding.textMail.setText("");
                binding.textViewContent.setText("");
                Toast.makeText(getApplicationContext(), "RADI ODGOVOR!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}