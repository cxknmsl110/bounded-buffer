import java.awt.EventQueue;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JPanel;

public class Ui_Main {

	private JFrame frame;
	Buffer Buffer=new Buffer();
	HashMap<JButton, Producer> Producer = new HashMap<>(); 
	HashMap<JButton, Consumer> Consumer = new HashMap<>(); 
	JLabel[] buffer_obj=new JLabel[3];
	JLabel[] block_obj=new JLabel[2];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ui_Main window = new Ui_Main();
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
	public Ui_Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 480, 340);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnNewButton = new JButton("\u751F\u4EA7\u80051");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pro(btnNewButton);
			}
		});
		btnNewButton.setBounds(10, 10, 80, 40);
		frame.getContentPane().add(btnNewButton);
		this.Producer.put(btnNewButton, new Producer(btnNewButton,this.Buffer));
		
		JButton button = new JButton("\u751F\u4EA7\u80052");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pro(button);
			}
		});
		button.setBounds(100, 10, 80, 40);
		frame.getContentPane().add(button);
		this.Producer.put(button, new Producer(button,this.Buffer));
		
		JButton button_1 = new JButton("\u751F\u4EA7\u80053");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pro(button_1);
			}
		});
		button_1.setBounds(190, 10, 80, 40);
		frame.getContentPane().add(button_1);
		this.Producer.put(button_1, new Producer(button_1,this.Buffer));
		//
		JButton button_2 = new JButton("\u751F\u4EA7\u80054");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pro(button_2);
			}
		});
		button_2.setBounds(280, 10, 80, 40);
		frame.getContentPane().add(button_2);
		this.Producer.put(button_2, new Producer(button_2,this.Buffer));
		//
		JButton button_3 = new JButton("\u751F\u4EA7\u80055");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pro(button_3);
			}
		});
		button_3.setBounds(370, 10, 80, 40);
		frame.getContentPane().add(button_3);
		this.Producer.put(button_3, new Producer(button_3,this.Buffer));
		//
		JButton button_4 = new JButton("\u6D88\u8D39\u80051");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con(button_4);
			}
		});
		button_4.setBounds(10, 250, 80, 40);
		frame.getContentPane().add(button_4);
		this.Consumer.put(button_4, new Consumer(button_4,this.Buffer));
		//
		JButton button_5 = new JButton("\u6D88\u8D39\u80052");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con(button_5);
			}
		});
		button_5.setBounds(100, 250, 80, 40);
		frame.getContentPane().add(button_5);
		this.Consumer.put(button_5, new Consumer(button_5,this.Buffer));
		//
		JButton button_6 = new JButton("\u6D88\u8D39\u80053");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con(button_6);
			}
		});
		button_6.setBounds(190, 250, 80, 40);
		frame.getContentPane().add(button_6);
		this.Consumer.put(button_6, new Consumer(button_6,this.Buffer));
		//
		JButton button_7 = new JButton("\u6D88\u8D39\u80054");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con(button_7);
			}
		});
		button_7.setBounds(280, 250, 80, 40);
		frame.getContentPane().add(button_7);
		this.Consumer.put(button_7, new Consumer(button_7,this.Buffer));
		//
		JButton button_8 = new JButton("\u6D88\u8D39\u80055");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				con(button_8);
			}
		});
		button_8.setBounds(370, 250, 80, 40);
		frame.getContentPane().add(button_8);
		this.Consumer.put(button_8, new Consumer(button_8,this.Buffer));
		//
		//
		JPanel panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.setBounds(10, 60, 440, 40);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u751F\u4EA7\u8005\u963B\u585E\u961F\u5217");
		lblNewLabel.setBounds(10, 10, 420, 20);
		panel.add(lblNewLabel);
		lblNewLabel.setBackground(Color.WHITE);
		this.block_obj[0]=lblNewLabel;
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBackground(Color.GRAY);
		panel_1.setBounds(10, 200, 440, 40);
		frame.getContentPane().add(panel_1);
		
		JLabel label = new JLabel("\u6D88\u8D39\u8005\u963B\u585E\u961F\u5217");
		label.setBackground(Color.WHITE);
		label.setBounds(10, 10, 420, 20);
		panel_1.add(label);
		this.block_obj[1]=label;
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.YELLOW);
		panel_2.setBounds(10, 110, 140, 80);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("\u7F13\u51B2\u533A");
		lblNewLabel_1.setBounds(10, 10, 120, 60);
		panel_2.add(lblNewLabel_1);
		this.buffer_obj[0]=lblNewLabel_1;
		//
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.YELLOW);
		panel_3.setBounds(160, 110, 140, 80);
		frame.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		JLabel label_1 = new JLabel("\u7F13\u51B2\u533A");
		label_1.setBounds(10, 10, 120, 60);
		panel_3.add(label_1);
		this.buffer_obj[1]=label_1;
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.YELLOW);
		panel_4.setBounds(310, 110, 140, 80);
		frame.getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		JLabel label_2 = new JLabel("\u7F13\u51B2\u533A");
		label_2.setBounds(10, 10, 120, 60);
		panel_4.add(label_2);
		this.buffer_obj[2]=label_2;
		//
	}
	void pro(JButton sender) {
		//System.out.print("test");
		Producer Producer=this.Producer.get(sender);
		Producer.main();
		brush();
		
	}
	void con(JButton sender) {
		Consumer Consumer=this.Consumer.get(sender);
		Consumer.main();
		brush();
	}
	void brush() {
		int i;
		for(i=0;i<3;i++) {
			buffer_obj[i].setText("缓冲区");
		}
		i=0;
		for(String q : Buffer.buffer){
			buffer_obj[i].setText(q);
			i++;
        }
		i=0;
		String text = "";
		for(Producer q : Buffer.block_producer){
			text+=q.button.getText()+" ";
			i++;
        }
		if(i==0) {
			text="生产者阻塞队列";
		}
		//////////
		this.block_obj[0].setText(text);
		i=0;
		text = "";
		for(Consumer q : Buffer.block_consumer){
			text+=q.button.getText()+" ";
			i++;
        }
		if(i==0) {
			text="消费者阻塞队列";
		}
		this.block_obj[1].setText(text);
		
	}
}
class Buffer{
	int empty=3,full=0;
	Queue<String> buffer = new LinkedList<String>();
	Queue<Producer> block_producer=new LinkedList<Producer>();
	Queue<Consumer> block_consumer=new LinkedList<Consumer>();
	void put(String data) {
		buffer.add(data);
	}
	String get() {
		return buffer.poll();
	}
	boolean Pempty() {
		empty-=1;
		//System.out.print(empty);
		if(empty<0) {
			return false;
		}
		else {
			return true;
		}
	}
	boolean Pfull() {
		full-=1;
		if(full<0) {
			return false;
		}else {
			return true;
		}
	}
	void Vempty() {
		empty+=1;
		if (empty<1) {
			 Producer temp=block_producer.poll();
			 temp.wakeup();
		}
	}
	void Vfull() {
		full+=1;
		if(full<1) {
			Consumer temp=block_consumer.poll();
			temp.wakeup();
		}
	}
}
class Producer{
	JButton button;
	Buffer buffer;
	String data;
	Producer(JButton button,Buffer buffer){
		this.button=button;
		this.buffer=buffer;
	}
	void wakeup() {
		button.setEnabled(true);
		dowork();
	}
	void block() {
		button.setEnabled(false);
		buffer.block_producer.add(this);
	}
	void main() {
		data=button.getText()+"的数据";
		boolean temp=buffer.Pempty();
		System.out.print(temp);
		if(temp) {
			System.out.print("1");
			dowork();
		}else {
			System.out.print("2");
			block();
		}
		
	}
	void dowork() {
		buffer.put(data);
		buffer.Vfull();
	}
}
class Consumer{
	JButton button;
	Buffer buffer;
	String data;
	Consumer(JButton button,Buffer buffer){
		this.button=button;
		this.buffer=buffer;
	}
	void wakeup() {
		button.setEnabled(true);
		dowork();
	}
	void block() {
		button.setEnabled(false);
		buffer.block_consumer.add(this);
	}
	void main() {
		data=button.getText()+"的数据";
		if(buffer.Pfull()) {
			dowork();
		}else {
			block();
		}
		
	}
	void dowork() {
		buffer.get();
		buffer.Vempty();
	}

}