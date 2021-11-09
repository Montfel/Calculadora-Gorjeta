package com.montfel.calculadoradegorjeta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import me.abhinay.input.CurrencyEditText;

public class MainActivity extends AppCompatActivity {

    private TextView tvPorcentagem, tvGorjeta, tvTotal;
    private CurrencyEditText etValor;
    private SeekBar sbPorcentagem;
    private final DecimalFormat f = new DecimalFormat("#.##");
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

        sbPorcentagem.setProgress(10);
        tvPorcentagem.setText(R.string.dez_porcento);

        porcentagemListener();
        textoListener();
    }

    private void textoListener() {
        etValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                calcular(sbPorcentagem.getProgress());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    public void porcentagemListener() {
        sbPorcentagem.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String progresso = progress + "%";
                tvPorcentagem.setText(progresso);
                calcular(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                valorRecuperado = etValor.getText().toString();
                if (valorRecuperado.equals("") || valorRecuperado.equals(".")) {
                    Toast.makeText(getApplicationContext(), R.string.digite_um_valor, Toast.LENGTH_SHORT).show();
                    tvGorjeta.setText(R.string.zerado);
                    tvTotal.setText(R.string.zerado);
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void calcular(int progress) {
        valorRecuperado = etValor.getText().toString();

        if(!valorRecuperado.equals("")) {
            String valorFormatado = valorRecuperado
                    .replace(".", "")
                    .replace(",", ".")
                    .substring(1);
            Log.i("valor", "calcular: " + valorFormatado);
            double valorInput = Double.parseDouble(valorFormatado);
            double valorGorjeta = valorInput * ((double) progress / 100);
            String valorGorgetaText = "R$ " + f.format(valorGorjeta);
            String valorInputText = "R$ " + f.format((valorInput + valorGorjeta));
            tvGorjeta.setText(valorGorgetaText);
            tvTotal.setText(valorInputText);
        }
    }
}
