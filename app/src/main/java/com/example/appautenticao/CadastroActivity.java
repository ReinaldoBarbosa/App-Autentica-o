package com.example.appautenticao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appautenticao.databinding.ActivityCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicaToolbar();

        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(v -> validaDados());
    }

    private void inicaToolbar(){
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    private void validaDados() {
        String email = binding.editEmial.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                criarContaFireBase(email, senha);
            } else {
                Toast.makeText(this, "Informe uma Senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu Email.", Toast.LENGTH_SHORT).show();
        }
    }

    private void criarContaFireBase(String email, String senha){
        mAuth.createUserWithEmailAndPassword(
                email,senha
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
            else {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um Erro", Toast.LENGTH_SHORT).show();
            }
        });
    }
}