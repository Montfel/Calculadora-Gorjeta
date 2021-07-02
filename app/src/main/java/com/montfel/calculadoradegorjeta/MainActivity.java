package com.montfel.calculadoradegorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView tvPorcentagem, tvGorjeta, tvTotal;
    private TextInputEditText etValor;
    private SeekBar sbPorcentagem;
    private DecimalFormat f = new DecimalFormat("#.##");
    private String valorRecuperado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvPorcentagem = findViewById(R.id.tvPorcentagem);
        tvGorjeta = findViewById(R.id.tvGorjeta);
        tvTotal = findViewById(R.id.tvTotal);
        etValor = findViewById(R.id.etValor);
        sbPorcentagem = findViewById(R.id.sbPorcentagem);
        porcentagemListener();
    }

    public void porcentagemListener() {
        sbPorcentagem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvPorcentagem.setText(progress + "%");
                calcular(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                valorRecuperado = etValor.getText().toString();
                if (valorRecuperado.equals("") || valorRecuperado.equals(".")) {
                    Toast.makeText(getApplicationContext(), "Digite um valor primeiro!", Toast.LENGTH_SHORT).show();
                    tvGorjeta.setText(R.string.zerado);
                    tvTotal.setText(R.string.zerado);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calcular(int progress) {
        valorRecuperado = etValor.getText().toString();
        if(!valorRecuperado.equals("") && !valorRecuperado.equals(".")) {
            double valorInput = Double.parseDouble(valorRecuperado);
            double valorGorjeta = valorInput * ((double) progress / 100);
            tvGorjeta.setText("R$ " + f.format(valorGorjeta));
            tvTotal.setText("R$ " + f.format((valorInput + valorGorjeta)));
        }
    }
}
