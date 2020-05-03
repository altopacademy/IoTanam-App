package com.toroidapps.iotanam;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {
    CardView suhu;
    TextView txtValCurrentDay, txtHallo, txtTempVal, txtHumidityVal, txtNutriensVal, txtPhVal, txtWaterTank, txtStatusPlant;
    ImageView imgTempStatus, imgHumStatus, imgNutriens, imgPh, imgWaterLevel;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;

    private Integer statusPlantTemp = 0;
    private Integer statusPlantHum = 0;
    private Integer statusPlantNutriens = 0;
    private Integer statusPlantPh = 0;
    private Integer statustankVolume = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        txtValCurrentDay = (TextView) findViewById(R.id.txtValDay);
        txtHallo = (TextView) findViewById(R.id.txtHallo);
        txtTempVal = (TextView) findViewById(R.id.txtTempVal);
        txtHumidityVal = (TextView) findViewById(R.id.txtHumidityVal);
        txtNutriensVal = (TextView) findViewById(R.id.txtNutriensVal);
        txtPhVal = (TextView) findViewById(R.id.txtPhVal);
        txtWaterTank = (TextView) findViewById(R.id.txtVolTankVal);
        txtStatusPlant = (TextView) findViewById(R.id.txtStatusPlant);
        imgTempStatus = (ImageView) findViewById(R.id.imgTempStatus);
        imgHumStatus = (ImageView) findViewById(R.id.imgHumidityStatus);
        imgNutriens = (ImageView) findViewById(R.id.imgNutriensStatus);
        imgPh = (ImageView) findViewById(R.id.imgPhStatus);
        imgWaterLevel = (ImageView) findViewById(R.id.imgVolTankStatus);

        suhu = (CardView) findViewById(R.id.btnCardViewSuhu);
        suhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, SuhuGraph.class);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference hum = databaseReference.child("devices").child(uid).child("DHT_hum");
        final DatabaseReference temp = databaseReference.child("devices").child(uid).child("DHT_temp");
        final DatabaseReference nutriens = databaseReference.child("devices").child(uid).child("nutriens");
        final DatabaseReference ph = databaseReference.child("devices").child(uid).child("ph");
        final DatabaseReference tank_level = databaseReference.child("devices").child(uid).child("tank_level");
        final DatabaseReference username = databaseReference.child("users").child(uid).child("username");
        final DatabaseReference current_day = databaseReference.child("users").child(uid).child("currentDay");

        /**String strTemp = txtTempVal.getText().toString();
        String strHum = txtHumidityVal.getText().toString();
        String strTempFix[] = strTemp.split("\\s+");
        String strHumFix[] = strHum.split("\\s+ ");
        final double doubleTemp = Double.parseDouble(strTempFix[0]);
        final double doublehum = Double.parseDouble(strHumFix[0]);**/

        hum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value = dataSnapshot.getValue(Double.class);
                txtHumidityVal.setText(value + " %");
                if(value>60){
                    imgHumStatus.setImageResource(R.drawable.ic_error_outline_black_24dp);
                    statusPlantHum = 1;
                }else {
                    imgHumStatus.setImageResource(R.drawable.ic_checked);
                    statusPlantHum = 0;
                }
                if(statusPlantTemp == 0 && statusPlantHum == 0 && statusPlantNutriens == 0 && statusPlantPh == 0 && statustankVolume == 0){
                    txtStatusPlant.setText("Your plant are looking good.");
                }else {
                    txtStatusPlant.setText("Your plant are not good. Please check the instrument.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value = dataSnapshot.getValue(Double.class);
                txtTempVal.setText(value + " C");
                if(value>26){
                    imgTempStatus.setImageResource(R.drawable.ic_error_outline_black_24dp);
                    statusPlantTemp = 1;
                }else {
                    imgTempStatus.setImageResource(R.drawable.ic_checked);
                    statusPlantTemp = 0;
                }
                if(statusPlantTemp == 0 && statusPlantHum == 0 && statusPlantNutriens == 0 && statusPlantPh == 0 && statustankVolume == 0){
                    txtStatusPlant.setText("Your plant are looking good.");
                }else {
                    txtStatusPlant.setText("Your plant are not good. Please check the instrument.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        nutriens.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value3 = dataSnapshot.getValue(Double.class);
                txtNutriensVal.setText(value3 + "");
                if(value3<60){
                    imgNutriens.setImageResource(R.drawable.ic_error_outline_black_24dp);
                    statusPlantNutriens = 1;
                }else {
                    imgNutriens.setImageResource(R.drawable.ic_checked);
                    statusPlantNutriens = 0;
                }
                if(statusPlantTemp == 0 && statusPlantHum == 0 && statusPlantNutriens == 0 && statusPlantPh == 0 && statustankVolume == 0){
                    txtStatusPlant.setText("Your plant are looking good.");
                }else {
                    txtStatusPlant.setText("Your plant are not good. Please check the instrument.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ph.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value = dataSnapshot.getValue(Double.class);
                txtPhVal.setText(value + "");
                if(value>7){
                    imgPh.setImageResource(R.drawable.ic_error_outline_black_24dp);
                    statusPlantPh = 1;
                }else {
                    imgPh.setImageResource(R.drawable.ic_checked);
                    statusPlantPh = 0;
                }
                if(statusPlantTemp == 0 && statusPlantHum == 0 && statusPlantNutriens == 0 && statusPlantPh == 0 && statustankVolume == 0){
                    txtStatusPlant.setText("Your plant are looking good.");
                }else {
                    txtStatusPlant.setText("Your plant are not good. Please check the instrument.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        tank_level.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value = dataSnapshot.getValue(Double.class);
                txtWaterTank.setText(value + " %");
                if(value<50){
                    imgWaterLevel.setImageResource(R.drawable.ic_error_outline_black_24dp);
                    statustankVolume = 1;
                }else {
                    imgWaterLevel.setImageResource(R.drawable.ic_checked);
                    statustankVolume = 0;
                }
                if(statusPlantTemp == 0 && statusPlantHum == 0 && statusPlantNutriens == 0 && statusPlantPh == 0 && statustankVolume == 0){
                    txtStatusPlant.setText("Your plant are looking good.");
                }else {
                    txtStatusPlant.setText("Your plant are not good. Please check the instrument.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String value = (String) dataSnapshot.getValue();
                txtHallo.setText("Hi " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        current_day.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Integer value = dataSnapshot.getValue(Integer.class);
                txtValCurrentDay.setText(value + "");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /**if(statusHum == 1 || statusTemp == 1){
            txtStatusPlant.setText("Your plant are not good. Please check the instrument.");
        }else{
            txtStatusPlant.setText("Your plant are looking good.");
        }**/





        /**final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        final String uid = mAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference path_username = databaseReference.child("Users").child(uid).child("username");

        final DatabaseReference hum = databaseReference.child("Users").child(uid).child("hum");
        final DatabaseReference temp = databaseReference.child("Users").child(uid).child("temp");
        final DatabaseReference path_key = databaseReference.child("Devices").child(uid);

        path_username.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = (String) dataSnapshot.getValue();
                txtHallo.setText("Hi "+ value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        hum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value = dataSnapshot.getValue(Double.class);

                if (value != 0){
                    hum_text.setVisibility(TextView.VISIBLE);
                    icon_hum.setVisibility(ImageView.VISIBLE);
                    hum_text.setText(value.toString() + " %");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        temp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Double value = dataSnapshot.getValue(Double.class);

                if (value != 0){
                    temp_text.setVisibility(TextView.VISIBLE);
                    icon_temp.setVisibility(ImageView.VISIBLE);
                    temp_text.setText(value.toString()+ " C");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });**/
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_stat);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                    startActivity(intent);
                    finish();
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_contact);
                    Intent intent1 = new Intent(HomeActivity.this, ContactActivity.class);
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
                startActivity(new Intent(HomeActivity.this, loginActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
            //mAuth.signOut();
            //Toast.makeText(satpam_main.this, "Login error", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
