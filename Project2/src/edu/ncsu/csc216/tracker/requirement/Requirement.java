/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement;

import edu.ncsu.csc216.tracker.requirement.enums.Rejection;
import edu.ncsu.csc216.tracker.xml.Req;

/**
 * Requirement Class
 * @author Collin
 * 
 */
public class Requirement {
	
	private int requirementId;
	private String summary;
	private String acceptanceTestId;
	private int priority;
	private String estimate;
	private String developer;
	private Rejection rejectionReason;
	/**
	 * SUBMITTED_NAME
	 */
	public static final String SUBMITTED_NAME = "Submitted";
	/**
	 * ACCEPTED_NAME
	 */
	public static final String ACCEPTED_NAME = "Accepted";
	/**
	 * REJECTED_NAME
	 */
	public static final String REJECTED_NAME = "Rejected";
	/**
	 * WORKING_NAME
	 */
	public static final String WORKING_NAME = "Working";
	/**
	 * COMPLETED_NAME
	 */
	public static final String COMPLETED_NAME = "Completed";
	/**
	 * VERIFIED_NAME
	 */
	public static final String VERIFIED_NAME = "Verified";
	/**
	 * DUPLICATE_NAME
	 */
	public static final String DUPLICATE_NAME = "Duplicate";
	/**
	 * INFEASIBLE_NAME
	 */
	public static final String INFEASIBLE_NAME = "Infeasible";
	/**
	 * TOO_LARGE_NAME
	 */
	public static final String TOO_LARGE_NAME = "Too Large";
	/**
	 * OUT_OF_SCOPE_NAME
	 */
	public static final String OUT_OF_SCOPE_NAME = "Out of Scope";
	/**
	 * INAPROPRIATE_NAME
	 */
	public static final String INAPPROPRIATE_NAME = "Innappropriate";
	private static int counter = 0;
	
	private final RequirementState 
	verifiedState = new VerifiedState(),
	completedState = new CompletedState(),
	submittedState = new SubmittedState(),
	acceptedState = new AcceptedState(),
	rejectedState = new RejectedState(),
	workingState = new WorkingState();
	private RequirementState state = null;


	/**
	 * Requirement Constructor
	 * @param summary String
	 * @param testId String
	 */
	public Requirement(String summary, String testId) {
		this.summary = summary;
		this.acceptanceTestId = testId;
		this.state = submittedState;
		this.requirementId = counter;
		incrementCounter();
	}
	
	/**
	 * Requirement Constructor
	 * @param req Req
	 */
	public Requirement(Req req) {
		this.acceptanceTestId = req.getTest();
		this.developer = req.getDeveloper();
		this.estimate = req.getEstimate();
		this.requirementId = req.getId();
		this.priority = req.getPriority();
		this.summary = req.getSummary();
		setRejectionReason(req.getRejection());
		setState(req.getState());
	}
	
	/**
	 * incrementCounter
	 */
	private static void incrementCounter() {
		counter++;
	}
	
	/**
	 * getRequirementId
	 * @return requirementId
	 */
	public int getRequirementId() {
		// TODO Auto-generated method stub
		return requirementId;
	}
	
	/**
	 * getState
	 * @return RequirementState
	 */
	public RequirementState getState() {
		// TODO Auto-generated method stub
		return state;
	}
	
	/**
	 * setState
	 * @param state String
	 */
	private void setState(String state) {
		switch (state) {
		case SUBMITTED_NAME:
			this.state = submittedState;
			break;
		case ACCEPTED_NAME:
			this.state = acceptedState;
			break;
		case REJECTED_NAME:
			this.state = rejectedState;
			break;
		case WORKING_NAME:
			this.state = workingState;
			break;
		case COMPLETED_NAME:
			this.state = completedState;
			break;
		case VERIFIED_NAME:
			this.state = verifiedState;
			break;
		default:
			throw new IllegalArgumentException();
	}
		
	}
	
	/**
	 * getPriority
	 * @return String
	 */
	public int getPriority() {
		// TODO Auto-generated method stub
		return priority;
	}
	
	/**
	 * getEstimate
	 * @return String
	 */
	public String getEstimate() {
		// TODO Auto-generated method stub
		return estimate;
	}
	
	/**
	 * getRejectionReason
	 * @return Rejection Rejection Reason
	 */
	public Rejection getRejectionReason() {
		return rejectionReason;
	}
	
	/**
	 * getRejectionReasonString
	 * @return String rejection reason
	 */
	public String getRejectionReasonString() {
		if (rejectionReason == null) {
			return null;
		}
		switch (rejectionReason) {
		case DUPLICATE:
			return DUPLICATE_NAME;

		case INAPPROPRIATE:
			return INAPPROPRIATE_NAME;
			
		case INFEASIBLE:
			return INFEASIBLE_NAME;
			
		case OUT_OF_SCOPE:
			return OUT_OF_SCOPE_NAME;
			
		case TOO_LARGE:
			return TOO_LARGE_NAME;
		
		default:
			return null;
		}
	}
	
	/**
	 * setRejectionReason
	 * @param reason String
	 */
	private void setRejectionReason(String reason) {
		if(reason == null) {
			return;
		}
		switch (reason) {
			case DUPLICATE_NAME:
				this.rejectionReason = Rejection.DUPLICATE;
				break;
			case INFEASIBLE_NAME:
				this.rejectionReason = Rejection.INFEASIBLE;
				break;
			case INAPPROPRIATE_NAME:
				this.rejectionReason = Rejection.INAPPROPRIATE;
				break;
			case TOO_LARGE_NAME:
				this.rejectionReason = Rejection.TOO_LARGE;
				break;
			case OUT_OF_SCOPE_NAME:
				this.rejectionReason = Rejection.OUT_OF_SCOPE;
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	
	/**
	 * getDeveloper
	 * @return String 
	 */
	public String getDeveloper() {
		// TODO Auto-generated method stub
		return developer;
	}
	
	/**
	 * getSummary
	 * @return summary String
	 */
	public String getSummary() {
		// TODO Auto-generated method stub
		return summary;
	}

	/**
	 * getAcceptanceTestId
	 * @return String
	 */
	public String getAcceptanceTestId() {
		// TODO Auto-generated method stub
		return acceptanceTestId;
	}

	/**
	 * setDeveloper
	 * @param dev String
	 */
	public void setDeveloper(String dev) {
		developer = dev;
	}
	
	/**
	 * update
	 * @param c Command
	 */
	public void update(Command c) {
		state.updateState(c);
	}
	
	/**
	 * getXMLReq
	 * @return Reg
	 */
	public Req getXMLReq() {
		Req req = new Req();
		req.setId(requirementId);
		req.setEstimate(estimate);
		req.setDeveloper(developer);
		req.setPriority(priority);
		req.setSummary(summary);
		req.setTest(acceptanceTestId);
		req.setState(state.getStateName());
		req.setRejection(getRejectionReasonString());
		return req;
	}
	
	/**
	 * setCounter
	 * @param count int
	 */
	public static void setCounter(int count) {
		counter = count;
	}
	
	/**
	 * SubmittedState Class
	 * @author Cameron
	 * @author Collin
	 *
	 */
	private class SubmittedState implements RequirementState {

		/**
		 * SubmittedState Constructor
		 */
		private SubmittedState() {
				
		}
			
		/**
		 * updateState
		 * @param c Command
		 */
		public void updateState(Command c) {
			switch (c.getCommand()) {
				case REJECT:
					state = rejectedState;
					rejectionReason = c.getRejectionReason();
					return;
				case ACCEPT:
					state = acceptedState;
					estimate = c.getEstimate();
					priority = c.getPriority();
					return;
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * getStateName
		 * @return String
		 */
		public String getStateName() {
			return SUBMITTED_NAME;
		}
	}
	
	/**
	 * AcceptedState Class
	 * @author Cameron
	 *@author Collin
	 */
	private class AcceptedState implements RequirementState {
		
		/**
		 * AcceptedState Constructor
		 */
		private AcceptedState() {
			
		}
		
		/**
		 * updateState
		 * @param c Command
		 */
		public void updateState(Command c) {
			switch (c.getCommand()) {
				case ASSIGN:
					state = workingState;
					developer = c.getDeveloperId();
					return;
				case REJECT:
					state = rejectedState;
					rejectionReason = c.getRejectionReason();
					priority = c.getPriority();
					estimate = null;
					return;
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * getStateName
		 * @return String
		 */
		public String getStateName() {
			return ACCEPTED_NAME;
		}
	}
	
	/**
	 * RejectedState Class
	 * @author Cameron
	 * @author Collin
	 */
	private class RejectedState implements RequirementState {
		
		/**
		 * RejectedState Constructor
		 */
		private RejectedState() {
			
		}
		
		/**
		 * updateState
		 * @param c Command
		 */
		public void updateState(Command c) {
			switch (c.getCommand()) {
				case REVISE:
					state = submittedState;
					summary = c.getSummary();
					acceptanceTestId = c.getAcceptanceTestId();
					rejectionReason = null;
					return;
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * getStateName
		 * @return String
		 */
		public String getStateName() {
			return REJECTED_NAME;
		}
	}
	
	/**
	 * WorkingState Class
	 * @author Cameron
	 * @author Collin
	 */
	private class WorkingState implements RequirementState {
		
		/**
		 * WorkingState Constructor
		 */
		private WorkingState() {
			
		}
		
		/**
		 * updateState
		 * @param c Command
		 */
		public void updateState(Command c) {
			switch (c.getCommand()) {
				case COMPLETE:	
					state = completedState;
					return;
				case REJECT:
					state = rejectedState;
					rejectionReason = c.getRejectionReason();
					priority = c.getPriority();
					estimate = null;
					developer = null;
					return;
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * getStateName
		 * @return String
		 */
		public String getStateName() {
			return WORKING_NAME;
		}
	}
	
	/**
	 * CompletedState Class
	 * @author Cameron
	 *
	 */
	private class CompletedState implements RequirementState {

		/**
		 * CompletedState Constructor
		 */
		private CompletedState() {
			
		}
		
		/**
		 * updateState
		 * @param c Command
		 */
		public void updateState(Command c) {
			switch (c.getCommand()) {
				case PASS:				
					state = verifiedState;
					return;
				case FAIL:
					state = workingState;
					return;
				case ASSIGN:
					state = workingState;
					developer = c.getDeveloperId();
					return;
				case REJECT:
					state = rejectedState;
					rejectionReason = c.getRejectionReason();
					priority = c.getPriority();
					estimate = null;
					developer = null;
					return;
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * getStateName
		 * @return String
		 */
		public String getStateName() {
			return COMPLETED_NAME;
		}
	}

	/**
	 * Verified State Class
	 * @author Cameron
	 *
	 */
	private class VerifiedState implements RequirementState {
		
		/**
		 * VerifiedState Constructor
		 */
		private VerifiedState() {
			
		}
		
		/**
		 * updateState
		 * @param c Command
		 */
		public void updateState(Command c) {
			switch (c.getCommand()) {
				case ASSIGN:
					state = workingState;
					developer = c.getDeveloperId();
					return;
				case REJECT:
					state = rejectedState;
					rejectionReason = c.getRejectionReason();
					priority = c.getPriority();
					estimate = null;
					developer = null;
					return;
				default:
					throw new UnsupportedOperationException();
			}
		}
		
		/**
		 * getStateName
		 * @return String
		 */
		public String getStateName() {
			return VERIFIED_NAME;
		}
	}
}


