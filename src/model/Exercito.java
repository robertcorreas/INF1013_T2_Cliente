package model;

import java.io.Serializable;
import java.util.ArrayList;
import controller.ControllerTabuleiro;

public class Exercito implements Serializable {

	private String nome;
	private Object cor;
	private boolean ativo = false;
	private ArrayList<Soldado> lstSoldados = new ArrayList<Soldado>();
	private ArrayList<Carta> lstCarta = new ArrayList<Carta>();
	private Objetivo objetivo;
	private boolean foradojogo;

	// Construtor
	public Exercito(String nome, Object cor) {
		this.nome = nome;
		this.cor = cor;
	}

	// Getters e Setters
	public ArrayList<Carta> getLstCartas() {
		return lstCarta;
	}

	public void addCarta(Carta c) {
		lstCarta.add(c);
	}

	public void removeCarta(Carta c) {
		lstCarta.remove(c);
	}

	public String getNome() {
		return this.nome;
	}

	public void setAtivo() {
		if (!ativo) {
			ativo = true;
		} else {
			ativo = false;
		}
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void addSoldado(Soldado s) {
		lstSoldados.add(s);
	}
	
	public void removeSoldado() {
		lstSoldados.remove(0);
	}

	public Object getCor() {
		return this.cor;
	}
	
	public ArrayList<Soldado> getLstSoldados() {
		return lstSoldados;
	}

	public Objetivo getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(Objetivo objetivo) {
		this.objetivo = objetivo;
	}
	
	public boolean isJogadorFora(){
		return foradojogo;
	}
	
	public void setJogadorFora(boolean b) {
		foradojogo = b;
		
		// Se o jogador estÃ¡ fora do jogo
		if (isJogadorFora()) {
			
			// Para cada jogador da lista de jogadores
			for (Exercito j : ControllerTabuleiro.getInstance().getLstJogadores()) {
				
				// Se o jogador nÃ£o Ã© o jogador da vez
				if (Comparator.notEquals(j, ControllerTabuleiro.getInstance().getJogadorDaVez())) {
					
					// Se o objetivo do jogador Ã© destruir este exÃ©rcito
					if (j.getObjetivo().getExercitoAlvo().equals(this)) {

						// Seta o objetivo de 24 territorios para o jogador
						j.setObjetivo(new Objetivo_1());

					}
				}
			}
		}

	}
	
}
