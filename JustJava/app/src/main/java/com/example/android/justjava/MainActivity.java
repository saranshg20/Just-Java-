package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {

        CheckBox hasWhippedCream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean whippedCream = hasWhippedCream.isChecked();
        CheckBox hasChocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean chocolate = hasChocolate.isChecked();
        EditText nameOfCustomer = (EditText) findViewById(R.id.name_of_customer);
        String name = nameOfCustomer.getText().toString();
        display(quantity);
//        createOrderSummary(quantity, whippedCream, chocolate, name);
/**
 * Below code for starting emailing activity
 */
//        Intent intent = new Intent(Intent.ACTION_SEND);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+"'"+name+"'");
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"saranshg20@iitk.ac.in"});
        email.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + name);
        email.putExtra(Intent.EXTRA_TEXT, createOrderSummary(quantity, whippedCream, chocolate, name));
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Send Mail Using :"));

    }

    /**
     * Creating summary of the order
     */
    private String createOrderSummary(int quantity, boolean whippedCream, boolean chocolate, String name) {
        String stringMessage = "Name: " + name + "\nAdd  Whipped Cream?  " + whippedCream + "\nAdd Chocolate? " + chocolate + "\nQuantity: " + quantity + "\nTotal: \u20B9" + calculatePrice(whippedCream, chocolate) + "\nThank You!";
//        displayMessage(stringMessage);
        return stringMessage;
    }


    /**
     * Calculates the price of the order.
     * <p>
     * quantity param is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = quantity * 5;

        if (chocolate && whippedCream) price += quantity * 3;
        else if (whippedCream) price += quantity * 1;
        else if (chocolate) price += quantity * 2;

        return price;
    }

    private void display(int i) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + i);
    }

    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 cups of coffees :(", Toast.LENGTH_SHORT).show();
        }
        if (quantity < 100) quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 100 cups of coffees :)", Toast.LENGTH_SHORT).show();
        }
        if (quantity > 1) quantity = quantity - 1;
        display(quantity);
    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }


}