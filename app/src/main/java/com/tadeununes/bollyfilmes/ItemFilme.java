package com.tadeununes.bollyfilmes;

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/*{

"popularity": 278.89,
"vote_count": 1084,
"video": false,
"poster_path": "/cjr4NWURcVN3gW5FlHeabgBHLrY.jpg",
"id": 547016,
"adult": false,
"backdrop_path": "/m0ObOaJBerZ3Unc74l471ar8Iiy.jpg",
"original_language": "en",
"original_title": "The Old Guard",
"genre_ids": [
28,
14
],
"title": "The Old Guard",
"vote_average": 7.4,
"overview": "Four undying warriors who've secretly protected humanity for centuries become targeted for their mysterious powers just as they discover a new immortal.",
"release_date": "2020-07-10"

 }*/

public class ItemFilme implements Serializable {

    private long id; //id
    private String titulo; //title
    private String descricao; //overview
    private String dataLancamento; //release_date
    private String posterPath; //poster_path
    private String capaPath; //backdrop_path
    private float avaliacao; //vote_average

    public ItemFilme(long id, String titulo, String descricao, String dataLancamento, String posterPath, String capaPath, float avaliacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataLancamento = dataLancamento;
        this.posterPath = posterPath;
        this.capaPath = capaPath;
        this.avaliacao = avaliacao;
    }

    public ItemFilme(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getLong("id");
        this.titulo = jsonObject.getString("title");
        this.descricao = jsonObject.getString("overview");
        this.dataLancamento = jsonObject.getString("release_date");
        this.posterPath = jsonObject.getString("poster_path");
        this.capaPath = jsonObject.getString("backdrop_path");
        this.avaliacao = (float) jsonObject.getDouble("vote_average");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCapaPath() {
        return capaPath;
    }

    public void setCapaPath(String capaPath) {
        this.capaPath = capaPath;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }
}