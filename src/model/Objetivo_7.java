//Conquistar na totalidade a EUROPA, a AMÉRICA DO SUL e mais um terceiro.

package model;

import java.util.ArrayList;

public class Objetivo_7 extends Objetivo {
	
	public Objetivo_7(){
		this.nome = "Europa, America do Sul e mais um";
		this.descricao = "Conquistar na totalidade Europa, America do Sul e mais um continente a sua escolha";
	}

	@Override
	public boolean Check(ArrayList<Continente> lstContinentes, Exercito e) {
		int oc = 0;
		int af = 0;
		int as = 0;
		int an = 0;
		
		for (Continente c : lstContinentes) {
			if (c.getNome().equals("Oceania")) {
				for (Territorio t : c.getLstTerritorios()) {
					if (t.getLstSoldados().get(0).getExercito().equals(e)) {
						oc ++;
					}
				}
			}
			
			if (c.getNome().equals("Europa")){
				for(Territorio t: c.getLstTerritorios()){
					if (Comparator.notEquals(t.getLstSoldados().get(0).getExercito(), e)){
						return false;
					}
				}
			}
			
			if (c.getNome().equals("África")) {
				for (Territorio t : c.getLstTerritorios()) {
					if (t.getLstSoldados().get(0).getExercito().equals(e)) {
						af ++;
					}
				}
			}
			
			if (c.getNome().equals("América do sul")) {
				for (Territorio t : c.getLstTerritorios()) {
					if (Comparator.notEquals(t.getLstSoldados().get(0).getExercito(), e)) {
						return false;
					}
				}
			}
			
			if (c.getNome().equals("Ásia")) {
				for (Territorio t : c.getLstTerritorios()) {
					if (t.getLstSoldados().get(0).getExercito().equals(e)) {
						as ++;
					}
				}
			}
			
			if (c.getNome().equals("América do norte")) {
				for (Territorio t : c.getLstTerritorios()) {
					if (t.getLstSoldados().get(0).getExercito().equals(e)) {
						an ++;
					}
				}
			}

		}
		
		if (af==6 || as==20 || oc==4 || an==9 ){
			return true;
		}

		return false;
		
	}

}