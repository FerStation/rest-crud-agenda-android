package com.example.etec.agendaapk;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContatoActivity extends AppCompatActivity {

    //atributos
    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtTel;
    private EditText txtCel;
    private Button btnExcluir;

    private Contato contato;

    private int contatoID;

    ContatoService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        service = ServiceGenerator
                .createService(ContatoService.class);

        //recebe o parametro
        Intent intent = getIntent();
        contatoID = intent.getIntExtra("ID",0);

        //vinculo entre o java e o xml
        txtNome = (EditText)findViewById(R.id.editTextNome);
        txtEmail = (EditText)findViewById(R.id.editTextEmail);
        txtTel = (EditText)findViewById(R.id.editTextTel);
        txtCel = (EditText)findViewById(R.id.editTextCel);
        btnExcluir = (Button)findViewById(R.id.btnExcluir);

        //esconder o botão, quando cadastrar um novo contato
        if(contatoID == 0){
            btnExcluir.setVisibility(View.GONE);
        }
        else{
            carregarContato(contatoID);
        }

    }// fim do onCreate

    public void carregarContato(int contatoID){

        ContatoService service = ServiceGenerator
                .createService(ContatoService.class);

        Call<Contato> call = service.carregarContato(contatoID);
        call.enqueue(new Callback<Contato>() {
            @Override
            public void onResponse(Call<Contato> call, Response<Contato> response) {

                txtNome.setText(response.body().getNome());
                txtEmail.setText(response.body().getEmail());
                txtCel.setText(String.valueOf(response.body().getCelular()));
                txtTel.setText(String.valueOf(response.body().getTel()));

            }//fim do onResponse

            @Override
            public void onFailure(Call<Contato> call, Throwable t) {

            }//fim do onFailure
        });

    }//fim do carregar contato

    public void salvarContato(View view){

        //Criação do objeto "Contato" que será
        //enviado por POST para a API
        contato = new Contato();
        contato.setNome(txtNome.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setTel(Long.parseLong(txtTel.getText().toString()));
        contato.setCelular(Long.parseLong(txtCel.getText().toString()));

        //salvar contato
        if(contatoID == 0){
            Call<Contato> call = service.salvarContato(contato);
            call.enqueue(new Callback<Contato>() {
                @Override
                public void onResponse(Call<Contato> call,
                                       Response<Contato> response) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Cadastrado com sucesso!", Toast.LENGTH_LONG);
                    toast.show();

                }// fim do onResponse

                @Override
                public void onFailure(Call<Contato> call, Throwable t) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Erro!", Toast.LENGTH_LONG);
                    toast.show();
                }//fim do onFailure
            });
        }//fim do if
        else{
            //alteração
            contato.setID(contatoID);
            Call<Contato> call = service.alterarContato(contatoID,contato);
            call.enqueue(new Callback<Contato>() {
                @Override
                public void onResponse(Call<Contato> call, Response<Contato> response) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Deu certo!", Toast.LENGTH_LONG);
                    toast.show();

                    //fecha a actvity
                    finish();

                }//fim do onResponse

                @Override
                public void onFailure(Call<Contato> call, Throwable t) {

                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Erro!", Toast.LENGTH_LONG);
                    toast.show();

                }//fim do onFailure
            });

        }//fim do else

    }//fim do salvarContato

    public void excluirContato(View view){

        //exibe o AlertDialog
        new AlertDialog.Builder(this)
                .setTitle("Confirmação")
                .setMessage("Deseja realamente excluir esse contato?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //esse trecho será executado, quando o usuario clicar
                        //em "SIM"
                        Call<Void> call = service.excluirContato(contatoID);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                                //mostrar uma msg
                                Toast.makeText(getApplicationContext(), "Contato excluido com sucesso!",
                                        Toast.LENGTH_SHORT).show();

                                //fechar Actvity
                                finish();

                            }//fim do onResponse

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), "Erro ao excluir!",
                                        Toast.LENGTH_SHORT).show();

                            }//fim do onFailure(
                        });//fim da requisição
                    }})//fim do onClick("Sim")
                .setNegativeButton("Não", null).show();

    }//fim do excluirContato

}//fim da classe








