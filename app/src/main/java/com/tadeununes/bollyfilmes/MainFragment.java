package com.tadeununes.bollyfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private int posicaoItem = ListView.INVALID_POSITION;
    private static final String KEY_POSICAO = "SELECIONADO";
    FilmesAdapter adapter;
    private ListView list;
    private boolean useFilmeDestaque = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        list = view.findViewById(R.id.list_filmes);

        final ArrayList<ItemFilme> arrayList = new ArrayList<>();
        arrayList.add(new ItemFilme("Homem Aranha", "Filme de heroi picado por uma aranha", "09/04/2016", 4));
        arrayList.add(new ItemFilme("Homem Cobra", "Filme de heroi picado por uma Cobra", "01/06/2017", 2));
        arrayList.add(new ItemFilme("Homem Javali", "Filme de heroi mordido por um Javali", "22/07/2018", 4));
        arrayList.add(new ItemFilme("Homem Passaro", "Filme de heroi bicado por um Passaro", "18/09/2019", 5));
        arrayList.add(new ItemFilme("Homem Cachorro", "Filme de heroi mordido por um Cachorro", "12/01/2020", 3.5f));
        arrayList.add(new ItemFilme("Homem Gato", "Filme de heroi arranhado por um Gato", "30/04/2015", 1.8f));

        adapter = new FilmesAdapter(getContext(), arrayList);
        adapter.setUseFilmeDestaque(useFilmeDestaque);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFilme itemFilme = arrayList.get(position);
                Callback callback = (Callback) getActivity();
                callback.onItemSelected(itemFilme);
                posicaoItem = position;
            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_POSICAO)){
            posicaoItem = savedInstanceState.getInt(KEY_POSICAO);
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

        if(posicaoItem != ListView.INVALID_POSITION){
            outState.putInt(KEY_POSICAO, posicaoItem);
        }

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {

        if(posicaoItem != ListView.INVALID_POSITION && list != null){
            list.smoothScrollToPosition(posicaoItem);
        }

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_atualizar:
                Toast.makeText(getContext(), "Atualizando os filmes...", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;

        if(adapter != null){
            adapter.setUseFilmeDestaque(useFilmeDestaque);
        }

    }

    public interface Callback {
        void onItemSelected(ItemFilme itemFilme);
    }

}


