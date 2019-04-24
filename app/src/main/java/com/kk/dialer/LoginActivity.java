package com.kk.dialer;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
TextView forgot,register;
Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        forgot = findViewById(R.id.forgot);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotDialog();
            }
        });
        openDialog();
    }

    public void openDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);

        final View layout = View.inflate(this, R.layout.dialog, null);
        Button ok =  dialog.findViewById(R.id.ok);
        Button cancel =  dialog.findViewById(R.id.cancel);
        final TextInputEditText url1 =  dialog.findViewById(R.id.url);
        final TextInputEditText port1 =  dialog.findViewById(R.id.port);
        ok.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url=url1.getText().toString();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String port=port1.getText().toString();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void openForgotDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.forgotdialog);
        dialog.setCancelable(false);

        final View layout = View.inflate(this, R.layout.forgotdialog, null);
        Button send =  dialog.findViewById(R.id.send);
        Button cancel =  dialog.findViewById(R.id.cancel);
        final TextInputEditText url1 =  dialog.findViewById(R.id.url);
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url=url1.getText().toString();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
