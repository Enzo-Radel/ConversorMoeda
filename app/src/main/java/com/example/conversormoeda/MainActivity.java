package com.example.conversormoeda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String moedaOrigem, moedaDestino;
    Double valor;
    String url = "https://economia.awesomeapi.com.br/json/last/";
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.output = findViewById(R.id.textViewResult);
    }

    public void FuncChamadaPorBotao(View view) {
        EditText inputValor = findViewById(R.id.editTextValorDig);
        RadioGroup moedaSrcInput = findViewById(R.id.radioGroup);
        RadioGroup moedaDstInput = findViewById(R.id.radioGroup2);

        int srcSelectedId = moedaSrcInput.getCheckedRadioButtonId();
        RadioButton srcRadioButton = (RadioButton) findViewById(srcSelectedId);

        int dstSelectedId = moedaDstInput.getCheckedRadioButtonId();
        RadioButton dstRadioButton = (RadioButton) findViewById(dstSelectedId);

        this.moedaOrigem = srcRadioButton.getText().toString();
        this.moedaDestino = dstRadioButton.getText().toString();
        this.valor = Double.parseDouble(inputValor.getText().toString());

        ProgressBar bar = findViewById(R.id.progressBar);

        MyTask task =  new MyTask(output, valor, this.moedaOrigem, this.moedaDestino);
        task.execute(this.url);
    }
}