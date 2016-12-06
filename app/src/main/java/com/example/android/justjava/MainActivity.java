package com.example.android.justjava;

/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 * <p>
 * package com.example.android.justjava;
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.TextView;

import java.security.KeyStore;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;

import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.checked;
import static android.R.attr.id;
import static android.R.attr.order;
import static android.R.attr.tileModeY;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    int quantity = 1;

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, R.string.warning_top, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    public void submitOrder(View view) {
        CheckBox WhippedCream = (CheckBox) findViewById(R.id.Whipped_cream);
        boolean hasWhippedCream = WhippedCream.isChecked();
        CheckBox Chocolate = (CheckBox) findViewById(R.id.Chocolote);
        boolean hasChocolate = Chocolate.isChecked();
        EditText YourName = (EditText) findViewById(R.id.Name_editText);
        String Name = YourName.getText().toString();
        int price = calculatePrice(quantity, hasChocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, Name);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        //intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, "519467792@qq.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "OrderCoffee");
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this,R.string.warning_bottom, Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);

    }

    public int calculatePrice(int quantity, boolean hasChocolate, boolean hasWhippedCream) {
        int baseprice = 5;
        if (hasChocolate) {
            baseprice += 2;
        }
        if (hasWhippedCream) {
            baseprice += 1;
        }
        return baseprice * quantity;
    }

    public String createOrderSummary(int price, boolean addWhippedCream,
                                     boolean addChocolate, String YourName) {
        String priceMessage = "Name:" + YourName;
        priceMessage += "\nAdd Whipped Cream?: " + addWhippedCream;
        priceMessage += "\nAdd Chocolate?: " + addChocolate;
        priceMessage += "\nQuantity:" + quantity;
        priceMessage += "\nTotal:" + price + "$";
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
        /**
         * This method displays the given text on the screen.
         */}



    public void composeEmail(String[] addresses, String subject) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
}