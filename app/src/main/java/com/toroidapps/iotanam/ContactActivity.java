package com.toroidapps.iotanam;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactActivity extends AppCompatActivity {
    EditText txtEmail, txtName, txtMessage;
    Button btnContact;

    private DatabaseReference database;
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_notifications);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtName = (EditText) findViewById(R.id.txtName);
        txtMessage = (EditText) findViewById(R.id.txtMessage);
        btnContact = (Button) findViewById(R.id.btnSend);

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        final String uid = fAuth.getCurrentUser().getUid();

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference namaLengkap = databaseReference.child("users").child(uid).child("nama_lengkap");
        final DatabaseReference email = databaseReference.child("users").child(uid).child("email");
        namaLengkap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtName.setText(value);
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
        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData(new contactAdapter(txtName.getText().toString(),
                        txtEmail.getText().toString(),
                        txtMessage.getText().toString(),
                        "unsolved"
                ));
            }
        });

    }
    private void submitData(contactAdapter data){

        final String uid = fAuth.getCurrentUser().getUid();
        database.child("contact").child(uid).push().setValue(data).addOnSuccessListener(ContactActivity.this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                txtName.setText("");
                txtEmail.setText("");
                txtMessage.setText("");

                //database.child("devices").child(uid).push().child("name").setValue("DHT11_temp");
                //database.child("Devices").child(uid).push().child("value").setValue(0);
                Snackbar.make(findViewById(R.id.btnSend), "Pesan anda berhasil dikirim", Snackbar.LENGTH_LONG).show();
            }
        });
        //finish();
        //database.child("users").child(uid).child(field_nama_rumah.getText().toString()).setValue("awesome");
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_stat);
                    Intent intent = new Intent(ContactActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent1 = new Intent(ContactActivity.this, SettingActivity.class);
                    startActivity(intent1);
                    finish();
                    return true;
                case R.id.navigation_notifications:

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
                startActivity(new Intent(ContactActivity.this, loginActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
            //mAuth.signOut();
            //Toast.makeText(satpam_main.this, "Login error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
