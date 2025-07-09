package com.cscorner.healsphere;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cscorner.healsphere.Dashboard1;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.api.ApiException;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 100;
    private static final String TAG = "LoginActivity";

    private GoogleSignInClient mGoogleSignInClient;
    private Button btnGoogle, btnLogin;
    private ImageView btnBack;
    private TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.btnGoogle);
        signUp = findViewById(R.id.txtSignUp);
        btnBack = findViewById(R.id.btnBack);

        // Configure Google Sign-In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check if user is already signed in
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            navigateToHome(account);
        }

        // Sign Up Click
        signUp.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Sign Up feature is coming soon!", Toast.LENGTH_SHORT).show()
        );

        // Navigate back to WelcomeActivity
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, Dashboard1.class);
            startActivity(intent);
            finish();
        });

        // Google Sign-In Click
        btnGoogle.setOnClickListener(v -> signIn());

        // Login Button Click (TODO: Implement email-password login)
        btnLogin.setOnClickListener(v -> {
            Toast.makeText(this, "Email-Password Login Not Implemented Yet!", Toast.LENGTH_SHORT).show();
        });
    }

    private void signIn() {
        // Sign out previous session to prevent errors
        mGoogleSignInClient.signOut().addOnCompleteListener(this, task -> {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    navigateToHome(account);
                }
            } catch (ApiException e) {
                Log.e(TAG, "Google Sign-In failed: " + e.getMessage());
                Toast.makeText(this, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToHome(GoogleSignInAccount account) {
        Intent intent = new Intent(LoginActivity.this, Dashboard1.class);
        intent.putExtra("USER_NAME", account.getDisplayName());
        intent.putExtra("USER_EMAIL", account.getEmail());
        if (account.getPhotoUrl() != null) {
            intent.putExtra("USER_PHOTO", account.getPhotoUrl().toString());
        }
        startActivity(intent);
        finish();
    }
}
