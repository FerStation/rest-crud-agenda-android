package com.example.etec.agendaapk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                //abrir uma nova activity
                Intent intent = new Intent(MainActivity.this,ContatoActivity.class);
                startActivity(intent);

            }
        });

        //ligação entre o java e o XML
        listView =(ListView)findViewById(R.id.listView);

        //carregarTodos();

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarTodos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void carregarTodos(){

        //cria objeto da classe ServiceGenerator
        //esse objeto será responsavel por criar o serviço de
        //requisições nesta classe.
        ContatoService service = ServiceGenerator
                .createService(ContatoService.class);

        //salvar a resposta da requisição
        Call<List<Contato>> call = service.listContatos();

        //Executa a requisição
        call.enqueue(new Callback<List<Contato>>() {
            @Override
            public void onResponse(Call<List<Contato>> call, Response<List<Contato>> response) {

                //Faz a relação entre "chave" e valor que serão
                //adicionados na lista
                ArrayList<HashMap<String, String>> contatoList =
                        new ArrayList<HashMap<String, String>>();

                for(Contato contato : response.body()){

                    HashMap<String, String> infoContato = new HashMap<String, String>();

                    infoContato.put("ID",String.valueOf(contato.getID()));
                    infoContato.put("nome", contato.getNome());
                    infoContato.put("cel",String.valueOf(contato.getCelular()));

                    //adiciona o contato a lista de contatos, que será
                    //utilizada para compor a "listView"
                    contatoList.add(infoContato);

                }//fim do for

                ListAdapter adapter = new SimpleAdapter(
                        MainActivity.this,contatoList,
                        R.layout.view_lista_contato,
                        new String[] { "ID","nome","cel"},
                        new int[] {R.id.textViewID, R.id.textViewNome, R.id.textViewCelular});

                listView.setAdapter(adapter);

                //captura o click de cada item da lista
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        TextView textViewID = (TextView)view.findViewById(R.id.textViewID);

                        //passa o ID por parametro
                        Intent intent = new Intent(getApplicationContext(),ContatoActivity.class);
                        intent.putExtra("ID", Integer.parseInt(textViewID.getText().toString()));
                        startActivity(intent);

                    }//fim do onItemClick

                });//fim do setOnItemClickListener

            }//fim do onResponse

            @Override
            public void onFailure(Call<List<Contato>> call, Throwable t) {

                Log.e("ERRO", ""+t);
            }
        });

    }//fim do carregarTodos

}//fim da classe
