package com.tadeununes.bollyfilmes;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

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

        new FilmesAsyncTask().execute();

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

    public class FilmesAsyncTask extends AsyncTask<Void, Void, List<ItemFilme>> {

        @Override //é onde se executa a ação na tread de background
        protected List<ItemFilme> doInBackground(Void... params) {

            // https://api.themoviedb.org/3/movie/popular?api_key=3d9b8381a3777be769a59c3081d9227e
            //https://api.themoviedb.org/3/movie/76341?api_key=<<api_key>>&language=pt-BR

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                String urlBase = "https://api.themoviedb.org/3/movie/popular?";
                String apiKey = "api_key";
                String language = "language";

                Uri uriApi = Uri.parse(urlBase).buildUpon()
                        .appendQueryParameter(apiKey, BuildConfig.TMDB_API_KEY)
                        .appendQueryParameter(language, "pt-BR")
                        .build();
                URL url = new URL(uriApi.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream ==null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String linha;
                StringBuffer buffer = new StringBuffer();
                while ((linha=reader.readLine()) != null){
                    buffer.append(linha);
                    buffer.append("\n");
                }

                return JsonUtil.fromJsonToList(buffer.toString());

            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;

        }

        @Override // é onde se executa ações após a finalização da tread de background
        protected void onPostExecute(List<ItemFilme> itemFilmes) {
            adapter.clear();
            adapter.addAll(itemFilmes);
            adapter.notifyDataSetChanged();
        }
    }

    public interface Callback {
        void onItemSelected(ItemFilme itemFilme);
    }

}


