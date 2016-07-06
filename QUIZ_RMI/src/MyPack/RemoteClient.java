package MyPack;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.Naming;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class RemoteClient extends Frame implements ActionListener, ItemListener {
	public static final String URL = "rmi://127.0.0.1:1099/QuizServer";
	// Timer
	public boolean issuppend = false;
	private final JLabel time = new JLabel();
	private final SimpleDateFormat sdf = new SimpleDateFormat("");
	private int currentSecond = 60;
	private Calendar calendar;
	// check BACK button
	boolean checkBack = false;
	String qn, ans1, ans2, ans3, ans4, selectedans, correctans;
	int num = 1, qno, questions, index = 0;
	public int cardinalNumBack = 0;
	public int cardinalNumNext = 0;
	// Current answer
	public String currentCheckBox = "";
	//
	public static Map<String, String> iconTable = new HashMap<String, String>();

	Details[] data1;
	Details[] data2;
	public int numQuestion;
	int score = 0;
	ServerInterface theServer;
	JFrame parent = new JFrame();
	CheckboxGroup cg = new CheckboxGroup();
	Checkbox chkName1 = new Checkbox("", cg, false);
	Checkbox chkName2 = new Checkbox("", cg, false);
	Checkbox chkName3 = new Checkbox("", cg, false);
	Checkbox chkName4 = new Checkbox("", cg, false);
	Button backbtn = new Button("   Back   ");
	Button nextbtn = new Button("   Next   ");
	Button exitbtn = new Button("   Exit   ");
	Panel panel1 = new Panel();
	Panel panel2 = new Panel();
	Label lblName1 = new Label("");
	Label lblName2 = new Label("");
	Label lblName3 = new Label("");
	Label lblName4 = new Label("");

	public RemoteClient(String title) {
		super(title);
		setLayout(new BorderLayout());
		panel1.setLayout(new GridLayout(7, 1));
		lblName1.setFont(new Font("Helvetica", Font.BOLD, 16));

		backbtn.setFont(new Font("Lucida Regular", Font.BOLD, 16));
		nextbtn.setFont(new Font("Lucida Regular", Font.BOLD, 16));
		exitbtn.setFont(new Font("Lucida Regular", Font.BOLD, 16));
		time.setFont(new Font("Lucida Regular", Font.BOLD, 25));
		time.setForeground(Color.RED);
		chkName1.addItemListener(this);
		chkName2.addItemListener(this);
		chkName3.addItemListener(this);
		chkName4.addItemListener(this);
		chkName1.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 14));
		chkName2.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 14));
		chkName3.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 14));
		chkName4.setFont(new Font("Helvetica", Font.BOLD | Font.ITALIC, 14));
		lblName2.setFont(new Font("Lucida", Font.BOLD, 12));
		lblName3.setFont(new Font("Lucida", Font.BOLD, 12));
		lblName3.setForeground(Color.blue);
		// display Timer in Screen
		panel1.add(time);
		//
		panel1.add(lblName2);
		panel1.add(lblName3);
		panel1.add(chkName1);
		panel1.add(chkName2);
		panel1.add(chkName3);
		panel1.add(chkName4);
		panel2.add(backbtn);
		panel2.add(nextbtn);
		panel2.add(exitbtn);
		panel2.setBackground(Color.black);
		panel2.setForeground(Color.blue);
		panel1.setSize(1000, 200);
		panel2.setSize(1000, 300);
		add("Center", panel1);
		add("South", panel2);
		pack();
		backbtn.setEnabled(false);
		backbtn.addActionListener(this);
		nextbtn.addActionListener(this);
		exitbtn.addActionListener(this);
		try {
			theServer = (ServerInterface) Naming.lookup(URL);
		} catch (Exception e) {
		}

		try {
			data2 = theServer.getDetails();
			numQuestion = data2.length;
			questions = data2.length;
			display();
		} catch (Exception ce) {
			System.out.println(ce);
		}
	}
	// Thread timer
	public void start() {
		calendar = Calendar.getInstance();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// Stop timer when tester click button Send
				if (issuppend == true) {
					timer.cancel();
					timer.purge();
					return;
				}
				// Check lost time: timer=0;
				if (currentSecond == 0) {
					// Show Core by message Dialog
					JOptionPane.showMessageDialog(parent,
							"                     TIME TEST=0,THANKS FOR YOUR TEST!YOUR SCORE : "
									+ score + "          ");
					System.exit(0);
				}
				// Calculator timer and show timer in Screen App
				--currentSecond;
				time.setText(String.format("TIME TEST %s%02d",
						sdf.format(calendar.getTime()), currentSecond));
			}
		}, 0, 1000);
	}

	public void actionPerformed(ActionEvent e) {
		// set enabled BACK button
		if (index >= 0) {
			backbtn.setEnabled(true);
		}
		// display in Screen when click BACK button
		if (e.getSource().equals(backbtn)) {
			index -= 1;
			cardinalNumNext -= 1;
			displayBack();

			System.out.println("index: " + index);
			System.out.println("cardinalNumBack: " + cardinalNumBack);
			System.out.println("cardinalNumNext: " + cardinalNumNext);
			System.out.println("cardinalNumBack: " + cardinalNumBack);
			if (cardinalNumNext == 0) {
				backbtn.setEnabled(false);
			}
			checkBack = true;
		}
		// display in Screen when click NEXT button
		else if (e.getSource().equals(nextbtn)) {
			// we have two case
			//case 1: if index == number of question before back|| NO BACK
			// not reset CheckBox
			if (cardinalNumNext == cardinalNumBack && checkBack == false) {
				cardinalNumBack += 1;
				cardinalNumNext += 1;
				index += 1;
				System.out.println("index of next: " + index);
				System.out.println("cardinalNumBack: " + cardinalNumBack);
				System.out.println("cardinalNumNext: " + cardinalNumNext);
				cg.setSelectedCheckbox(null);
				display();
			} 
			////case 2: if index < number of question before BACK
			// RESET ALL CheckBox
			else {
				cardinalNumNext += 1;
				index += 1;
				System.out.println("index of next: " + index);
				System.out.println("cardinalNumBack: " + cardinalNumBack);
				System.out.println("cardinalNumNext: " + cardinalNumNext);
				cg.setSelectedCheckbox(null);
				displayBack();
			}
		} else {

			// complete test and or exit App, then show Score
			if (e.getSource().equals(exitbtn)) {
				issuppend = true;
				JOptionPane.showMessageDialog(parent,
						"     THANKS FOR YOUR TEST! YOUR SCORE : " + score
								+ "          ");
				System.exit(0);
			}
		}
		// Reset Index question when click back button or click next button
		if (index == 9) {
			nextbtn.setEnabled(false);
			exitbtn.setLabel(" Send ");
		} else {
			nextbtn.setEnabled(true);
		}
	}

	public void itemStateChanged(ItemEvent e) {
		// Check answer
		if (e.getSource().equals(chkName1)) {
			currentCheckBox = "1";
		}
		if (e.getSource().equals(chkName2)) {
			currentCheckBox = "2";
		}
		if (e.getSource().equals(chkName3)) {
			currentCheckBox = "3";
		}
		if (e.getSource().equals(chkName4)) {
			currentCheckBox = "4";
		}
		// Add answer to Map data
		iconTable.put(data2[index].qno + "", currentCheckBox);

		int k = iconTable.size();

		System.out.println("size map: " + k);

		System.out.println("currentCheckBox: " + currentCheckBox);
		String answer = ((Checkbox) e.getSource()).getLabel();
		if (answer.trim().equals(data2[index].correctans.trim()))
			score += 25;

	}

	public static void main(String args[]) {
		RemoteClient quizclient = new RemoteClient("Online Quiz");
		// start thread timer
		quizclient.start();
		quizclient.setSize(1000, 400);
		quizclient.setVisible(true);
	}

	public void display() {
		qno = data2[index].qno;
		qn = data2[index].question;
		ans1 = data2[index].ans1;
		ans2 = data2[index].ans2;
		ans3 = data2[index].ans3;
		ans4 = data2[index].ans4;
		correctans = data2[index].correctans;
		lblName2.setText("Question " + qno);
		lblName3.setText(qn);
		chkName1.setLabel(ans1);
		chkName2.setLabel(ans2);
		chkName3.setLabel(ans3);
		chkName4.setLabel(ans4);
		chkName1.setState(false);
		chkName2.setState(false);
		chkName3.setState(false);
		chkName4.setState(false);
	}

	// display question when click back button or next< current question before
	// click the nearest question back
	public void displayBack() {
		qno = data2[index].qno;
		qn = data2[index].question;
		ans1 = data2[index].ans1;
		ans2 = data2[index].ans2;
		ans3 = data2[index].ans3;
		ans4 = data2[index].ans4;
		correctans = data2[index].correctans;
		lblName2.setText("Question " + qno);
		lblName3.setText(qn);
		chkName1.setLabel(ans1);
		chkName2.setLabel(ans2);
		chkName3.setLabel(ans3);
		chkName4.setLabel(ans4);
		int temp = 0;
		try {
			temp = Integer.parseInt(iconTable.get(qno + ""));
			if (temp == 0) {
				chkName1.setState(false);
				chkName2.setState(false);
				chkName3.setState(false);
				chkName4.setState(false);
			}

			switch (temp) {
			case 1:
				chkName1.setState(true);
				chkName2.setState(false);
				chkName3.setState(false);
				chkName4.setState(false);
				break;
			case 2:
				chkName1.setState(false);
				chkName2.setState(true);
				chkName3.setState(false);
				chkName4.setState(false);
				break;
			case 3:
				chkName1.setState(false);
				chkName2.setState(false);
				chkName3.setState(true);
				chkName4.setState(false);
				break;
			case 4:
				chkName1.setState(false);
				chkName2.setState(false);
				chkName3.setState(false);
				chkName4.setState(true);
				break;
			default:
				break;
			}
		} catch (Exception ex) {
			chkName1.setState(false);
			chkName2.setState(false);
			chkName3.setState(false);
			chkName4.setState(false);
		}
	}
}
