package messages;

import java.io.Serializable;
import java.util.ArrayList;

import base.Exam;
import base.StudentComplete;
import messages.constants.Answer;

public class S2CMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4076258183198827566L;
	private short type;
	private StudentComplete student;
	private ArrayList<Exam> list;
	private String message;
	
	private S2CMessage(short type, StudentComplete student, ArrayList<Exam> list, String message) {
		this.type = type;
		this.student = student;
		this.list = list;
		this.message = message;
	}
	
	public static S2CMessage createInitMessage(StudentComplete student) {
		return new S2CMessage(Answer.INIT, student, null, null);
	}
	
	public static S2CMessage createEsamiDisponibiliMessage(ArrayList<Exam> list) {
		return new S2CMessage(Answer.ESAMI_DISPONIBILI, null, list, null);
	}
	
	public static S2CMessage createEsamiCancellabiliMessage(ArrayList<Exam> list) {
		return new S2CMessage(Answer.ESAMI_CANCELLABILI, null, list, null);
	}
	
	public static S2CMessage createOkMessage() {
		return new S2CMessage(Answer.OK, null, null, null);
	}
	
	public static S2CMessage createFailMessage(String message) {
		return new S2CMessage(Answer.FAIL, null, null, message);
	}
	
	public short getType() {
		return type;
	}
	
	public StudentComplete getStudentComplete() {
		return student;
	}
	
	public ArrayList<Exam> getList() {
		return list;
	}
	
	public String getMessage() {
		return message;
	}

}
