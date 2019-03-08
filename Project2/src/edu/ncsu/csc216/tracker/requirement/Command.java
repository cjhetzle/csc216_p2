/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**
 * Command Class
 * @author Collin
 * 
 */
public class Command {
	
	private String summary;
	private String acceptanceTestId;
	private String developerId;
	private String estimate;
	private int priority;
	private CommandValue c;
	private Rejection rejectionReason;
	/**
	 * Command
	 * @param state CammandValue
	 * @param summary String 
	 * @param acceptanceTestId String
	 * @param priority Int
	 * @param developerId String
	 * @param estimate String
	 * @param rejectionReason Rejection
	 */
	public Command(CommandValue state, String summary, String acceptanceTestId, int priority, String estimate, String developerId, Rejection rejectionReason) {
		if (state == null ||
				(state == CommandValue.ACCEPT && (estimate == null || estimate.equals("") || priority < 1 || priority > 3)) ||
				(state == CommandValue.REJECT && rejectionReason == null) ||
				(state.equals(CommandValue.ASSIGN) && (developerId == null || developerId.equals(""))) ||
				(state == CommandValue.REVISE && (summary == null || summary.equals("") || acceptanceTestId == null || acceptanceTestId.equals("")))) {
			throw new IllegalArgumentException();
		}
		this.summary = summary;
		this.acceptanceTestId = acceptanceTestId;
		this.developerId = developerId;
		this.estimate = estimate;
		this.priority = priority;
		this.c = state;
		this.rejectionReason = rejectionReason;
	}
	
	/**
	 * getSummary
	 * @return summary
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * getAcceptanceTestId
	 * @return asseptanceTestId
	 */
	public String getAcceptanceTestId() {
		return acceptanceTestId;
	}
	
	/**
	 * getCommand
	 * @return c Command
	 */
	public CommandValue getCommand() {
		return c;
	}
	
	/**
	 * getEstimate
	 * @return estimate String
	 */
	public String getEstimate() {
		return estimate;
	}
	
	/**
	 * getPriority
	 * @return priority int
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * getDeveloperId
	 * @return developerId String
	 */
	public String getDeveloperId() {
		return developerId;
	}
	
	/**
	 * getRejectionReason
	 * @return resolutionReason Rejction
	 */
	public Rejection getRejectionReason() {
		return rejectionReason;
	}
}
