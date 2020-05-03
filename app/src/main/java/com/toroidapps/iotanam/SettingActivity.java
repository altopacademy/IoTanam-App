package com.toroidapps.iotanam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    EditText txtUserName, txtNamaLengkap, txtTtl, txtEmail, txtDomisili, txtPasswordSekarang, txtGantiPassword, txtUlangiPassword;
    ImageButton btnTxtUsername, btnTxtNamaLengkap, btnTxtTtl, btnTxtEmail, btnTxtDomisili, btnTxtPasswordSekarang, btnTxtGantiPassword, btnTxtUlangiPassword;
    Button btnSimpan;

    private DatabaseReference database;
    private FirebaseAuth fAuth;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_dashboard);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtNamaLengkap = (EditText) findViewById(R.id.txtNamaLengkap);
        txtTtl = (EditText) findViewById(R.id.txtTtl);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtDomisili = (EditText) findViewById(R.id.txtDomisili);
        txtPasswordSekarang = (EditText) findViewById(R.id.txtPassNow);
        txtGantiPassword = (EditText) findViewById(R.id.txtPassNew);
        txtUlangiPassword = (EditText) findViewById(R.id.txtUlangiPass);

        btnTxtUsername = (ImageButton) findViewById(R.id.btnTxtUserName);
        btnTxtNamaLengkap = (ImageButton) findViewById(R.id.btnTxtNamaLengkap);
        btnTxtEmail = (ImageButton) findViewById(R.id.btnTxtEmail);
        btnTxtTtl = (ImageButton) findViewById(R.id.btnTxtTtl);
        btnTxtDomisili = (ImageButton) findViewById(R.id.btnTxtDomisili);
        btnTxtPasswordSekarang = (ImageButton) findViewById(R.id.btnTxtPasswordSekarang);
        btnTxtGantiPassword = (ImageButton) findViewById(R.id.btnTxtGantiPassword);
        btnTxtUlangiPassword = (ImageButton) findViewById(R.id.btnTxtUlangiPassword);

        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        final String uid = fAuth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference username = databaseReference.child("users").child(uid).child("username");
        final DatabaseReference namaLengkap = databaseReference.child("users").child(uid).child("nama_lengkap");
        final DatabaseReference email = databaseReference.child("users").child(uid).child("email");
        final DatabaseReference ttl = databaseReference.child("users").child(uid).child("ttl");
        final DatabaseReference domisili = databaseReference.child("users").child(uid).child("domisili");

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtUserName.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        namaLengkap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtNamaLengkap.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtEmail.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ttl.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtTtl.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        domisili.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtDomisili.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnTxtUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtUserName.setEnabled(true);
            }
        });
        btnTxtNamaLengkap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtNamaLengkap.setEnabled(true);
            }
        });
        btnTxtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtEmail.setEnabled(true);
            }
        });
        btnTxtTtl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialog();
            }
        });
        btnTxtDomisili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtDomisili.setEnabled(true);
            }
        });
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData(new settingAdapter(txtUserName.getText().toString(),
                        txtNamaLengkap.getText().toString(),
                        txtEmail.getText().toString(),
                        txtTtl.getText().toString(),
                        txtDomisili.getText().toString()
                ));
            }
        });
    }
    private void submitData(settingAdapter data){

        final String uid = fAuth.getCurrentUser().getUid();
        database.child("users").child(uid).setValue(data).addOnSuccessListener(SettingActivity.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                txtUserName.setEnabled(false);
                txtNamaLengkap.setEnabled(false);
                txtEmail.setEnabled(false);
                txtTtl.setEnabled(false);
                txtDomisili.setEnabled(false);

                Snackbar.make(findViewById(R.id.btnSimpan), "Pesan anda berhasil dikirim", Snackbar.LENGTH_LONG).show();
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
                txtTtl.setEnabled(true);
                txtTtl.setText(dateFormatter.format(newDate.getTime()));
                //txtTtl.setText("Tanggal dipilih : "+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_stat);
                    Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:
                    Intent intent1 = new Intent(SettingActivity.this, ContactActivity.class);
                    startActivity(intent1);
                    finish();
                    return true;
            }
            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.sign_out:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(SettingActivity.this, loginActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
            //mAuth.signOut();
            //Toast.makeText(satpam_main.this, "Login error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
