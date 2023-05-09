package sg.edu.rp.c346.id22016916.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    TextView amt;
    EditText amtInput;

    TextView pax;
    EditText paxInput;

    ToggleButton nosvs;
    ToggleButton gst;

    TextView disc;
    EditText discInput;

    RadioGroup payment;

    Button split;
    Button reset;

    TextView bill;
    TextView eachPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amt = findViewById(R.id.textViewAmount);
        amtInput = findViewById(R.id.editTextAmount);

        pax = findViewById(R.id.textViewPax);
        paxInput = findViewById(R.id.editTextPax);

        nosvs = findViewById(R.id.toggleButtonSVS);
        gst = findViewById(R.id.toggleButtonGST);

        disc = findViewById(R.id.textViewDisc);
        discInput = findViewById(R.id.editTextPax);

        payment = findViewById(R.id.rgPayment);

        split = findViewById(R.id.buttonSplit);
        reset = findViewById(R.id.buttonReset);

        nosvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amtInput.getText().toString().trim().length() != 0 &&
                    paxInput.getText().toString().trim().length() != 0) {
                    double total = 0.0;
                    if (!nosvs.isChecked() && !gst.isChecked()) {
                        total = Double.parseDouble(amtInput.getText().toString());
                    } else if (nosvs.isChecked() && !gst.isChecked()) {
                        total = Double.parseDouble(amtInput.getText().toString()) * 1.1;
                    } else if (!nosvs.isChecked() && gst.isChecked()) {
                        total = Double.parseDouble(amtInput.getText().toString()) * 1.07;
                    } else {
                        total = Double.parseDouble(amtInput.getText().toString()) * 1.17;
                    }

                    if (discInput.getText().toString().trim().length() != 0) {
                        total *= 1 - Double.parseDouble(discInput.getText().toString()) / 100;
                    }

                    bill.setText("Total Bill: $" + String.format("%.2f", total));
                    int numPax = Integer.parseInt(paxInput.getText().toString());
                    int radioID = payment.getCheckedRadioButtonId();

                    if (numPax != 1 && radioID == R.id.radioButtonCash) {
                        eachPay.setText("Each Pays: $" + String.format("%.2f", total / numPax));
                    } else if ((numPax != 1 && radioID == R.id.radioButtonPayNow)) {
                        eachPay.setText("Each Pays: $" + String.format("%.2f %s", total / numPax, "via Paynow to 91234567"));
                    } else if ((numPax == 1 && radioID == R.id.radioButtonPayNow)) {
                        eachPay.setText("Each Pays: $" + String.format("%.2f %s", total, "via Paynow to 91234567"));
                    } else {
                        eachPay.setText("Each Pays: $" + total);
                    }
                }
                reset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        amtInput.setText("");
                        paxInput.setText("");
                        nosvs.setChecked(false);
                        gst.setChecked(false);
                        discInput.setText("");
                    }
                });

            }
        });
    }
}