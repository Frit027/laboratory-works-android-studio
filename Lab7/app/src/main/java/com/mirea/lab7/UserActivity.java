package com.mirea.lab7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    private EditText nameBox;
    private EditText priceBox;
    private EditText countBox;

    private Intent intent;

    private long id = 0;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameBox = findViewById(R.id.name);
        priceBox = findViewById(R.id.price);
        countBox = findViewById(R.id.count);

        databaseHelper = new DatabaseHelper(this);
        intent = new Intent(this, BackendActivity.class);

        setDataItem();
    }

    private void setDataItem() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getLong(MyConstants.ID_KEY);
            pos = extras.getInt(MyConstants.POSITION_KEY);
        }

        if (id > 0) {
            nameBox.setText(databaseHelper.getDataByID(id).get(0));
            priceBox.setText(databaseHelper.getDataByID(id).get(1));
            countBox.setText(databaseHelper.getDataByID(id).get(2));
        } else {
            Button delButton = findViewById(R.id.deleteButton);
            delButton.setVisibility(View.GONE);
        }
    }

    public void onSaveClick(View view) {
        view.startAnimation(MainActivity.animAlpha);
        String name = nameBox.getText().toString();
        String price = priceBox.getText().toString();
        String count = countBox.getText().toString();

        if (name.length() != 0 && price.length() != 0 && count.length() != 0) {
            if (CheckData.checkPrice(price) && CheckData.checkCount(count)) {
                goBack(name, price, count);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Цена содержит только целые и дробные числа, кол-во - только целые.",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Заполните все поля.", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void onDeleteClick(View view) {
        view.startAnimation(MainActivity.animAlpha);
        databaseHelper.delete(id);
        intent.putExtra(MyConstants.DELETE, true);
        goBack(nameBox.getText().toString(), priceBox.getText().toString(),
                countBox.getText().toString());
    }

    @Override
    public void onBackPressed() {
        intent.putExtra(MyConstants.POSITION_KEY, pos);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void goBack(String name, String price, String count) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra(MyConstants.ID_KEY, id);
        intent.putExtra(MyConstants.POSITION_KEY, pos);
        intent.putExtra(MyConstants.NAME_KEY, name);
        intent.putExtra(MyConstants.PRICE_KEY, price);
        intent.putExtra(MyConstants.COUNT_KEY, count);

        setResult(RESULT_OK, intent);
        finish();
    }
}
