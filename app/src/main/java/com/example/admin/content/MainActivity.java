package com.example.admin.content;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ContentProviderDemo";

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 20;

    private boolean firstTimeLoaded = false;


    private TextView textView;

    private String[] mColumnProjection = new String[]{
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY


    };


    private String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";

    private String[] mSelectionArguments = new String[]{""};

    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkPermission()) {
            textView = (TextView) findViewById(R.id.textview);

            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                    mColumnProjection,

                   null,

                    null,
                    null);

            if (cursor != null && cursor.getCount() > 0) {

                StringBuilder stringBuilderQueryResult = new StringBuilder("");
                while (cursor.moveToNext()) {
                    stringBuilderQueryResult.append(cursor.getString(0) + "\n");

                }
                textView.setText(stringBuilderQueryResult.toString());
            } else {
                textView.setText("No Contacts in device");
            }




            /*Intent i = new Intent(Intent.ACTION_PICK);
            i.setData(Uri.parse("Mo"));
           startActivity(i);*/

        } else {
            requestPermission();
            {
                Toast.makeText(MainActivity.this, "not given", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private boolean checkPermission() {
        int FIRSTPERMISSIONRESULT = ContextCompat.checkSelfPermission(MainActivity.this, READ_EXTERNAL_STORAGE);
        if (FIRSTPERMISSIONRESULT == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        } else {
            return false;
        }
    }




    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        ) {

                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }






    }
}
