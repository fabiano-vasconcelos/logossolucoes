package br.com.mws4b.logossolucoes.code.obj;

import java.io.Serializable;

/**
 * Created by Fabiano on 18/07/2016.
 * Representa um filme
 */
public class Filme implements Serializable {
    // ID do filme na api
    private String imdbID;
    // Titulo do filme
    private String titulo;
    // Ano do filme
    private String ano;
    // Avaliação do filme
    private String avaliacao;
    // Data de Lançamento do filme
    private String lancamento;
    // Duração do filme
    private String duracao;
    // Genero do Filme
    private String genero;
    // Diretor do Filme
    private String diretor;
    // Escritor do filme
    private String escritor;
    // Atores do filme
    private String atores;
    // Resenha do filme
    private String resenha;
    // Linguas do filme
    private String linguas;
    // Países do filme
    private String paises;
    // Premios do filme
    private String premios;
    // Poster do filme
    private String poster;
    // Metascore do filme
    private String metascore;
    // Rating do IMDB do filme
    private String imdbRating;
    // Votos no IMDB do filme
    private String imdbVotes;

    // Construtor usado para a busca de filmes
    public Filme(String imdbID, String titulo) {
        this.imdbID = imdbID;
        this.titulo = titulo;
    }

    // Construtor usado para pegar o filme com todos os dados
    public Filme(String imdbID, String titulo, String ano, String avaliacao, String lancamento, String duracao, String genero, String diretor, String escritor, String atores, String resenha, String linguas, String paises, String premios, String poster, String metascore, String imdbRating, String imdbVotes) {
        this.imdbID = imdbID;
        this.titulo = titulo;
        this.ano = ano;
        this.avaliacao = avaliacao;
        this.lancamento = lancamento;
        this.duracao = duracao;
        this.genero = genero;
        this.diretor = diretor;
        this.escritor = escritor;
        this.atores = atores;
        this.resenha = resenha;
        this.linguas = linguas;
        this.paises = paises;
        this.premios = premios;
        this.poster = poster;
        this.metascore = metascore;
        this.imdbRating = imdbRating;
        this.imdbVotes = imdbVotes;
    }

    public String getImdbID() {

        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(String avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getLancamento() {
        return lancamento;
    }

    public void setLancamento(String lancamento) {
        this.lancamento = lancamento;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getEscritor() {
        return escritor;
    }

    public void setEscritor(String escritor) {
        this.escritor = escritor;
    }

    public String getAtores() {
        return atores;
    }

    public void setAtores(String atores) {
        this.atores = atores;
    }

    public String getResenha() {
        return resenha;
    }

    public void setResenha(String resenha) {
        this.resenha = resenha;
    }

    public String getLinguas() {
        return linguas;
    }

    public void setLinguas(String linguas) {
        this.linguas = linguas;
    }

    public String getPaises() {
        return paises;
    }

    public void setPaises(String paises) {
        this.paises = paises;
    }

    public String getPremios() {
        return premios;
    }

    public void setPremios(String premios) {
        this.premios = premios;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String metascore) {
        this.metascore = metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String imdbVotes) {
        this.imdbVotes = imdbVotes;
    }

    @Override
    public String toString() {
        return getTitulo();
    }
}

