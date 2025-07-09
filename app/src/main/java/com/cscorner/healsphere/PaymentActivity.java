package com.cscorner.healsphere;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Checkout.preload(getApplicationContext()); // Optional but recommended

        btnContinue = findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(v -> startPayment());
    }

    private void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("YOUR_RAZORPAY_KEY_ID");  // Replace with your Razorpay key

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Healsphere");
            options.put("description", "Doctor Consultation Fee");
            options.put("currency", "INR");
            options.put("amount", "20000"); // ₹200 in paise (multiply by 100)

            JSONObject prefill = new JSONObject();
            prefill.put("email", "test@healsphere.com");
            prefill.put("contact", "9876543210");

            options.put("prefill", prefill);

            checkout.open(PaymentActivity.this, options);

        } catch (Exception e) {
            Toast.makeText(this, "Payment error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Razorpay payment callbacks
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful! ID: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        // You can navigate to success screen here
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_SHORT).show();
        // You can navigate to failure screen or retry
    }
}
