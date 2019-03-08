/**
 * 
 */
package edu.ncsu.csc216.tracker.requirement;

import static org.junit.Assert.*;

import java.util.List;

import edu.ncsu.csc216.tracker.xml.Req;
import edu.ncsu.csc216.tracker.xml.RequirementsReader;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.requirement.enums.Rejection;

/**
 * tests the requirement class
 * @author Collin
 *
 */
public class RequirementTest {
	private Requirement[] requirements;
	private Req req;
	/**
	 * sets up a test
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		requirements = new Requirement[8];
		requirements[0] = new Requirement("Test 1", "Passes 1");
		requirements[1] = new Requirement("Test 2", "Passes 2");
		RequirementsReader file = new RequirementsReader("test-files/req1.xml");
		List<Req> list = file.getReqs();
		req = list.get(0);
		for(int i = 0; i < 6; i++ ) {
			requirements[i + 2] = new Requirement(list.get(i));
		}
		Requirement.setCounter(0);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#Requirement(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testRequirementStringString() {
		assertEquals("Test 1", requirements[0].getSummary());
		assertEquals("Passes 1", requirements[0].getAcceptanceTestId());
		assertEquals(0, requirements[0].getRequirementId());
		assertEquals("Submitted", requirements[0].getState().getStateName());
		assertEquals(1, requirements[1].getRequirementId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#Requirement(edu.ncsu.csc216.tracker.xml.Req)}.
	 */
	@Test
	public void testRequirementReq() {
		assertEquals("The system shall change a requirement's state from Submitted to Accepted when the user supplies an estimate of the time to implement the requirement and clicks the Accept button.", 
				requirements[2].getSummary());
		assertEquals(0, requirements[2].getRequirementId());
		assertEquals("Submitted", requirements[2].getState().getStateName());
		assertEquals("SubmittedToAccepted", requirements[2].getAcceptanceTestId());
		assertEquals("Accepted", requirements[3].getState().getStateName());
		assertEquals(1, requirements[3].getPriority());
		assertEquals("1 hour", requirements[3].getEstimate());
		assertEquals("An excellent 216 student!", requirements[4].getDeveloper());
		assertEquals("Duplicate", requirements[6].getRejectionReasonString());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getRequirementId()}.
	 */
	@Test
	public void testGetRequirementId() {
		assertEquals(0, requirements[0].getRequirementId());
		assertEquals(1, requirements[1].getRequirementId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getPriority()}.
	 */
	@Test
	public void testGetPriority() {
		assertEquals(1, requirements[3].getPriority());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getEstimate()}.
	 */
	@Test
	public void testGetEstimate() {
		assertEquals("1 hour", requirements[4].getEstimate());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getRejectionReason()}.
	 */
	@Test
	public void testGetRejectionReason() {
		assertEquals(Rejection.DUPLICATE, requirements[6].getRejectionReason());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getRejectionReasonString()}.
	 */
	@Test
	public void testGetRejectionReasonString() {
		assertEquals("Duplicate", requirements[6].getRejectionReasonString());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getDeveloper()}.
	 */
	@Test
	public void testGetDeveloper() {
		assertEquals("An excellent 216 student!", requirements[4].getDeveloper());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getSummary()}.
	 */
	@Test
	public void testGetSummary() {
		assertEquals("The system shall change a requirement's state from Submitted to Accepted when the user supplies an estimate of the time to implement the requirement and clicks the Accept button.", 
				requirements[2].getSummary());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getAcceptanceTestId()}.
	 */
	@Test
	public void testGetAcceptanceTestId() {
		assertEquals("SubmittedToAccepted", requirements[2].getAcceptanceTestId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#setDeveloper(java.lang.String)}.
	 */
	@Test
	public void testSetDeveloper() {
		requirements[0].setDeveloper("Collin");
		assertEquals("Collin", requirements[0].getDeveloper());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#update(edu.ncsu.csc216.tracker.requirement.Command)}.
	 */
	@Test
	public void testUpdate() {
		Command c = new Command(CommandValue.ACCEPT, null, null, 1, "1 day", null, null);
		requirements[0].update(c);
		assertEquals("Accepted", requirements[0].getState().getStateName());
		assertEquals("1 day", requirements[0].getEstimate());
		assertEquals(1, requirements[0].getPriority());
		c = new Command(CommandValue.ASSIGN, null, null, 0, null, "dev", null);
		requirements[0].update(c);
		assertEquals("Working", requirements[0].getState().getStateName());
		assertEquals("dev", requirements[0].getDeveloper());
		c = new Command(CommandValue.COMPLETE, null, null, 0, null, null, null);
		requirements[0].update(c);
		assertEquals("Completed", requirements[0].getState().getStateName());
		c = new Command(CommandValue.PASS, null, null, 0, null, null, null);
		requirements[0].update(c);
		assertEquals("Verified", requirements[0].getState().getStateName());
		c = new Command(CommandValue.REJECT, null, null, 0, null, null, Rejection.DUPLICATE);
		requirements[0].update(c);
		assertEquals(Rejection.DUPLICATE, requirements[0].getRejectionReason());
		for(int i = 2; i < requirements.length; i++) {
			try {
			requirements[i].update(c);
			} catch (UnsupportedOperationException e) {
				if ( i != 6) {
					fail();
				}
			}
			assertEquals(Rejection.DUPLICATE, requirements[i].getRejectionReason());
			assertEquals("Rejected", requirements[i].getState().getStateName());
		}
		c = new Command(CommandValue.REVISE, "Summary", "test", 0, null, null, null);
		requirements[0].update(c);
		assertEquals("Submitted", requirements[0].getState().getStateName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.requirement.Requirement#getXMLReq()}.
	 */
	@Test
	public void testGetXMLReq() {
		Req xml = requirements[2].getXMLReq();
		assertEquals(req.getDeveloper(), xml.getDeveloper());
		assertEquals(req.getEstimate(), xml.getEstimate());
		assertEquals(req.getId(), xml.getId());
		assertEquals(req.getPriority(), xml.getPriority());
		assertEquals(req.getRejection(), xml.getRejection());
		assertEquals(req.getState(), xml.getState());
		assertEquals(req.getSummary(), xml.getSummary());
		assertEquals(req.getTest(), xml.getTest());
	}

}
