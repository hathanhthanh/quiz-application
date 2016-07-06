package MyPack;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Details implements Serializable {
	private static final long serialVersionUID = 1L;
	int qno;
	String question;
	String ans1;
	String ans2;
	String ans3;
	String ans4;
	String correctans;
	public Details(int mqno, String mquestion, String m_ans1, String m_ans2,
			String m_ans3, String m_ans4, String mcorrectans) {
		qno = mqno;
		question = mquestion;
		ans1 = m_ans1;
		ans2 = m_ans2;
		ans3 = m_ans3;
		ans4 = m_ans4;
		correctans = mcorrectans;
	}
	// data
	public static Details[] getInstance() {
		Vector vect = new Vector();
		Details[] data = new Details[] {
				new Details(
						1,
						"Every Saturday afternoon in a parking lot on Cedar Street, collectors come to _______ off their prized vintage cars.",
						"show", "turn", "hold", "watch", "show"),
				new Details(
						2,
						"Everybody _______ has a company-issued cellular phone should e-mail their name and phone number to Cassandra Miller by 5 P.M. tomorrow.",
						"who", "Whom", "Which", "Where", "Who"),
				new Details(
						3,
						"The most _______complaint among the staff is not having enough opportunities for professional development.",
						"common", "commonly", "is common", "is commonly",
						"common"),
				new Details(
						4,
						"Because this month has been unusually _______, my team and I have been able to take the time to clean out file cabinets and archive old project materials.",
						"slow", "busy", "late", "active", "slow"),
				new Details(
						5,
						"The number of people submitting new _______ for jobless aid dropped by 11,000 last week.",
						"claim", "claims", "claimed", "claiming", "claims"),
				new Details(
						6,
						"When we _______ the world's economic development on a timeline we can see periods in which certain business sectors experienced an economic boom.",
						"examine", "examines", "examining", "examination",
						"examine"),
				new Details(
						7,
						"Business was slow overall on Wall Street this spring, but Richardson Holdings still _______ to show strong results.",
						"earned", "spread", "traded", "managed", "managed"),
				new Details(
						8,
						"The _______ retail price for the shoe polish is $3.99, roughly 25 percent lower than other shoe polishes currently on the market.",
						"suggest", "suggested", "suggesting", "suggestion",
						"suggested"),
				new Details(
						9,
						"The board will take until Monday to choose between two competing bids: one from Neptune, and the _______ from an unnamed Russian",
						"ever", "other", "either", "neither", "other"),
				new Details(
						10,
						"Political leaders have reached an _______ on a health bill that will extend health coverage to millions of Americans.",
						"agree", "agreed", "agreeing", "agreement", "agreement")};
		return data;
	}
}
