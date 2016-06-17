package cliente;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Continente;
import model.Dado;
import model.Deck;
import model.DeckObjetivos;
import model.Exercito;
import model.Jogada;
import model.Territorio;

public class TabuleiroSerializable implements Serializable {

	public List<model.Exercito> lstJogadores;
	public List<Jogada> lstJogadas;
	public ArrayList<Continente> lstContinentes;
	public model.Exercito iteradorAtualDoJogador;
	public Jogada iteradorAtualJogada;
	public ArrayList<Dado> lstDadosAtaque;
	public ArrayList<Dado> lstDadosDefesa;
	public Exercito jogadorDaVez;
	public Deck deck;
	public Territorio territorioOrigem;
	public Territorio territorioDestino;
	public String mensagem;
	public int qtdTroca;
	public boolean conquistouTerritorio;
	public DeckObjetivos deckObjetivos;
	public Exercito vencedor;

}
