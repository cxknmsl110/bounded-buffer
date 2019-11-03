package wbl;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.Font;

public class Main {

	private JFrame frame;
	public JTextField txtTimeToBe;
	public JButton btnNewButton;
	wbl mywbl=new wbl(this);
	public boolean food=false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 410, 280);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		btnNewButton = new JButton("Food to be placed here");
		btnNewButton.setFont(new Font("ºÚÌå", Font.PLAIN, 10));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				food=true;
				btnNewButton.setText("food");
			}
		});
		btnNewButton.setBounds(10, 10, 150, 220);
		frame.getContentPane().add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(160, 10, 230, 220);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 41, 230, 180);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JButton button = new JButton("0");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(0);
			}
		});
		button.setBounds(10, 130, 70, 40);
		panel_1.add(button);
		
		JButton button_1 = new JButton("1");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(1);
			}
		});
		button_1.setBounds(10, 10, 70, 40);
		panel_1.add(button_1);
		
		JButton button_2 = new JButton("2");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(2);
			}
		});
		button_2.setBounds(80, 10, 70, 40);
		panel_1.add(button_2);
		
		JButton button_3 = new JButton("3");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(3);
			}
		});
		button_3.setBounds(150, 10, 70, 40);
		panel_1.add(button_3);
		
		JButton button_4 = new JButton("4");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(4);
			}
		});
		button_4.setBounds(10, 50, 70, 40);
		panel_1.add(button_4);
		
		JButton button_5 = new JButton("5");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(5);
			}
		});
		button_5.setBounds(80, 50, 70, 40);
		panel_1.add(button_5);
		
		JButton button_6 = new JButton("6");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(6);
			}
		});
		button_6.setBounds(150, 50, 70, 40);
		panel_1.add(button_6);
		
		JButton button_7 = new JButton("7");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(7);
			}
		});
		button_7.setBounds(10, 90, 70, 40);
		panel_1.add(button_7);
		
		JButton button_8 = new JButton("8");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(8);
			}
		});
		button_8.setBounds(80, 90, 70, 40);
		panel_1.add(button_8);
		
		JButton button_9 = new JButton("9");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				settime(9);
			}
		});
		button_9.setBounds(150, 90, 70, 40);
		panel_1.add(button_9);
		
		JButton button_10 = new JButton("start");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(food) {
					mywbl.is_run=true;
				}
				else {
					btnNewButton.setText("no food!");
				}
				
				
			}
		});
		button_10.setBounds(80, 130, 70, 40);
		panel_1.add(button_10);
		
		JButton button_11 = new JButton("stop");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mywbl.sstop();
			}
		});
		button_11.setBounds(150, 130, 70, 40);
		panel_1.add(button_11);
		
		txtTimeToBe = new JTextField();
		txtTimeToBe.setText("Time to be displayed here");
		txtTimeToBe.setEnabled(false);
		txtTimeToBe.setBounds(10, 10, 210, 21);
		panel.add(txtTimeToBe);
		txtTimeToBe.setColumns(10);
	}
	void settime(int time) {
		this.mywbl.time=this.mywbl.time*10+time;
		this.txtTimeToBe.setText(Integer.toString(this.mywbl.time));
		
	}
}
class wbl extends Thread{
	public int time=0;
	public boolean is_run=false;
	Main main;
	wbl(Main main){
		this.main=main;
		start();
	}
	public void run() {
		while(true) {
			if(is_run) {
				if (time<=0) {
					main.txtTimeToBe.setText("Time to be displayed here");
					main.btnNewButton.setText("Food to be placed here");
					main.food=false;
					is_run=false;
				}
				else {
					time-=1;
					main.txtTimeToBe.setText(Integer.toString(time));
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
		
	void sstop() {
		is_run=false;
	}
}
