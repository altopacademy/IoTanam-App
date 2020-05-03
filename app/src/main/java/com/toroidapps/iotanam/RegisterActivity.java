package com.toroidapps.iotanam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;

public class RegisterActivity extends AppCompatActivity {
    EditText txtEmail, txtPass, txtRePass,txtUsername, txtNamaLengkap, txtTtl, txtDomisili;
    Button btnRegister, btnLogin;
    ImageButton ingbtnDatePicker;

    private FirebaseAuth fAuth;
    private FirebaseAuth.AuthStateListener fStateListener;
    private DatabaseReference database;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    private static final String TAG = RegisterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtRePass = (EditText) findViewById(R.id.txtRePass);
        txtUsername = (EditText) findViewById(R.id.txtUser);
        txtTtl = (EditText) findViewById(R.id.txtTtl);
        txtNamaLengkap = (EditText) findViewById(R.id.txtNamaLengkap);
        txtDomisili = (EditText) findViewById(R.id.txtDomisili);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        ingbtnDatePicker = (ImageButton) findViewById(R.id.btnTtl);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();

        //check whether user already login or not
        fStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    //Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                }else {
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isEmpty(txtUsername.getText().toString()) || isEmpty(txtEmail.getText().toString()) || isEmpty(txtPass.getText().toString()) || isEmpty(txtRePass.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Form tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }else if(!txtPass.getText().toString().equals(txtRePass.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Password tidak sama", Toast.LENGTH_SHORT).show();
                    txtPass.setText("");
                    txtRePass.setText("");
                }else{
                    signUp(txtEmail.getText().toString(), txtPass.getText().toString());
                    //finish();
                }
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        ingbtnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });


    }
    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                txtTtl.setText(dateFormatter.format(newDate.getTime()));
                //txtTtl.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    private void signUp(final String email, String password){
        fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                String formattedDate = df.format(c);

                if(!task.isSuccessful()){
                    task.getException().printStackTrace();
                    Toast.makeText(getApplicationContext(), "Proses Pendaftaran Gagal",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getApplicationContext(), "Proses Pendaftaran Berhasil",Toast.LENGTH_SHORT).show();
                    submitData(new registerAdapter(txtUsername.getText().toString(),
                            txtEmail.getText().toString(),
                            txtPass.getText().toString(),
                            txtNamaLengkap.getText().toString(),
                            txtTtl.getText().toString(),
                            txtDomisili.getText().toString(),
                            formattedDate,
                            0
                    ));
                    final String uid = fAuth.getCurrentUser().getUid();
                    database.child("devices").child(uid).child("DHT_temp").setValue(0);
                    database.child("devices").child(uid).child("DHT_hum").setValue(0);
                    database.child("devices").child(uid).child("nutriens").setValue(0);
                    database.child("devices").child(uid).child("ph").setValue(0);
                    database.child("devices").child(uid).child("tank_level").setValue(0);
                    //submitDataAuxilliary(new registerAdapterAuxilliary(0.0,"DHT11_temp"));
                    //submitDataAuxilliary(new registerAdapterAuxilliary(0.0,"DHT11_hum"));
                    //submitDataAuxilliary(new registerAdapterAuxilliary(0.0,"nutriens"));
                    //submitDataAuxilliary(new registerAdapterAuxilliary(0.0,"ph"));
                    //submitDataAuxilliary(new registerAdapterAuxilliary(0.0,"tank_level"));
                }
            }
        });
    }
    private void submitData(registerAdapter data){

        final String uid = fAuth.getCurrentUser().getUid();
        database.child("users").child(uid).setValue(data).addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                txtUsername.setText("");
                txtEmail.setText("");
                txtPass.setText("");
                txtRePass.setText("");
                txtNamaLengkap.setText("");
                txtDomisili.setText("");
                txtTtl.setText("");

                //database.child("devices").child(uid).push().child("name").setValue("DHT11_temp");
                //database.child("Devices").child(uid).push().child("value").setValue(0);
                Snackbar.make(findViewById(R.id.btnRegister), "Proses pendaftaran berhasil", Snackbar.LENGTH_LONG).show();
            }
        });
        //finish();
        //database.child("users").child(uid).child(field_nama_rumah.getText().toString()).setValue("awesome");
    }

    private void submitDataAuxilliary(registerAdapterAuxilliary data){

        String uid = fAuth.getCurrentUser().getUid();
        database.child("devices").child(uid).push().setValue(data).addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Snackbar.make(findViewById(R.id.btnRegister), "data initial berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
        //finish();
        //database.child("users").child(uid).child(field_nama_rumah.getText().toString()).setValue("awesome");
    }
    @Override
    protected void onStart(){
        super.onStart();
        fAuth.addAuthStateListener(fStateListener);
    }
    @Override
    protected void onStop(){
        super.onStop();
        if(fStateListener != null){
            fAuth.removeAuthStateListener(fStateListener);
        }
    }
}
