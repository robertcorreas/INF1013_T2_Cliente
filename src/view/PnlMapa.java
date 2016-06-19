package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ControllerTabuleiro;
import model.Comparator;
import model.Continente;
import model.Jogada;
import model.Territorio;


@SuppressWarnings("serial")
public class PnlMapa extends JPanel implements Observer {

	private static PnlMapa mapa;
	ControllerTabuleiro controller = ControllerTabuleiro.getInstance();
	private HashMap<String, JLabel> soldadosLabels = new HashMap<>();

	private PnlMapa() {
		controller.addObserver(this);
		
		setLayout(null);
		
		for(Continente c: controller.getLstContinentes()) {
			for(Territorio t: c.getLstTerritorios()) {
				JLabel lblNumSoldados = new JLabel();
				lblNumSoldados.setLocation((int)t.getSoldadoPos().getX(), (int)t.getSoldadoPos().getY());
				lblNumSoldados.setSize(23, 23);
				lblNumSoldados.setOpaque(false);
				lblNumSoldados.setForeground(Color.WHITE);
				lblNumSoldados.setText(t.getLstSoldados().size()+"");
				lblNumSoldados.setHorizontalAlignment(SwingConstants.CENTER);
				soldadosLabels.put(t.getNome(), lblNumSoldados);
				add(lblNumSoldados);
			}
		}
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				controller.pnlMapa_click(e.getX(), e.getY(), e.getButton());
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {

			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				
			}
		});
	}
	
	public static PnlMapa getInstance() {
		if (mapa == null) {
			mapa = new PnlMapa();
		}
		return mapa;
	}

	private void desenhaMapa(Graphics g) {
		
		for(Continente c: controller.getLstContinentes()) {
			for(Territorio t: c.getLstTerritorios()){
				soldadosLabels.get(t.getNome()).setText(t.getLstSoldados().size()+"");
			}
		}
		
		Color corBg = null;
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		for(Continente c : controller.getLstContinentes()) {			
			
			corBg = c.getCor();
			
			// Guarda a lista de territorios do continente em lstTerritorios.
			List<Territorio> lstTerritorios = c.getLstTerritorios();
			
			for(Territorio t : lstTerritorios) {
				
				// Pintando os territorios marcados como ativo
				if ((controller.getTerritorioOrigem()!=null && controller.getTerritorioOrigem().equals(t)) ||
						(controller.getTerritorioDestino()!=null && controller.getTerritorioDestino().equals(t))) {
					 corBg = c.getCor().brighter().brighter();
				} else {
					corBg = c.getCor();
				}
				
				// Pintando os territorios marcados como fronteira, se jogada for de ataque
				Jogada jogadaAtual = controller.getJogadaAtual();
				if (jogadaAtual != null && jogadaAtual.getNome().equals("Atacar")) {
					if (
							controller.getTerritorioOrigem()!=null // Se houver territorio de origem
							&& controller.getTerritorioOrigem().getLstFronteiras().contains(t) // & o territorio clicado estiver na lista de territorios de fronteiras do territorio de origem
							&& (Comparator.notEquals(controller.getTerritorioDestino(), t)) // & o territorio clicado nÃ£o for o territorio de destino 
							&& (Comparator.notEquals(controller.getTerritorioOrigem().getLstSoldados().get(0).getExercito(), t.getLstSoldados().get(0).getExercito())) // & O territorio clicado nao pertencer ao jogador atual
					) {
						corBg = c.getCor().brighter();	
					}
				} else if (jogadaAtual != null && jogadaAtual.getNome().equals("Remanejar")) {
					if (
							controller.getTerritorioOrigem()!=null // Se houver territorio de origem
							&& controller.getTerritorioOrigem().getLstFronteiras().contains(t) // & o territorio clicado estiver na lista de territorios de fronteiras do territorio de origem
							&& ((Comparator.notEquals(controller.getTerritorioDestino(), t))) // & o territorio clicado nÃ£o for o territorio de destino 
							&& controller.getTerritorioOrigem().getLstSoldados().get(0).getExercito().equals(t.getLstSoldados().get(0).getExercito()) // & O territorio clicado nao pertencer ao jogador atual
					) {
						corBg = c.getCor().brighter();	
					}
				}
				
					g2d.setPaint(corBg);
					g2d.fill(t.getShape());
					g2d.setPaint(Color.black);
					g2d.draw(t.getShape());
					
					t.desenhaPnlSoldados(g);
				
			}

		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		desenhaMapa(g);
	}

	
	@Override
	public void update(Observable o, Object arg) {
		this.controller = (ControllerTabuleiro)o;
		repaint();		
	}


}
