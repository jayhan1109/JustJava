package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(this, "You cannot order more then 100 cups of coffee", Toast.LENGTH_LONG).show();
            return;
        }

        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this, "You cannot order less then 1 cup of coffee", Toast.LENGTH_LONG).show();
            return;
        }

        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    public void submitOrder(View view) {

        boolean hasWhippedCream = ((CheckBox) findViewById(R.id.whipped_cream_checkbox)).isChecked();
        boolean hasChocolate = ((CheckBox) findViewById(R.id.chocolate_checkbox)).isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String name = ((EditText) findViewById(R.id.name_field)).getText().toString();

        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate, name);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;

        if (hasWhippedCream) {
            basePrice += 1;
        }

        if (hasChocolate) {
            basePrice += 2;
        }

        return basePrice * quantity;
    }

    /**
     * Create summary of the order.
     *
     * @param price           of the order
     * @param hasWhippedCream or not
     * @return text summary
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String name) {
        String order = getString(R.string.order_summary_name,name);
        order += "\n"+getString(R.string.add_whipped_cream,hasWhippedCream);
        order += "\n"+getString(R.string.add_chocolate,hasChocolate);
        order += "\n"+getString(R.string.order_summary_count,quantity);
        order += "\n"+getString(R.string.order_summary_price,price);
        order += "\n"+getString(R.string.thank_you);
        return order;
    }

}

//strings.xml(ko)
//    <?xml version="1.0" encoding="utf-8"?>
//<resources xmlns:xliff="urn:oasis:names:tc:xliff:document:1.2">
//<string name="app_name">그냥 자바</string>
//<string name="toppings">토핑</string>
//<string name="whipped_cream">휘핑크림</string>
//<string name="chocolate">초콜릿</string>
//<string name="quantity">수량</string>
//<string name="order">주문</string>
//<string name="order_summary_name">이름 : <xliff:g id="name">%s </xliff:g> </string>
//<string name="add_whipped_cream">휘핑크림 추가? <xliff:g id="hasWhippedCream">%b</xliff:g></string>
//<string name="add_chocolate">초콜릿 추가? <xliff:g id="hasChocolate">%b</xliff:g></string>
//<string name="order_summary_count">전체 수량 : <xliff:g id="quantity">%d</xliff:g> 커피</string>
//<string name="order_summary_price">가격 : $<xliff:g id="price">%d</xliff:g></string>
//<string name="thank_you">감사합니다 !</string>
//</resources>