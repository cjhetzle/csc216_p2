/**
 * 
 */
package edu.ncsu.csc216.tracker.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
import edu.ncsu.csc216.tracker.xml.RequirementIOException;
import edu.ncsu.csc216.tracker.xml.RequirementsReader;

/**
 * tests RequirementsList
 * @author Collin
 *
 */
public class RequirementsListTest {
	private RequirementsList list;
	/**
	 * sets up tests
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		list = new RequirementsList();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#RequirementsList()}.
	 */
	@Test
	public void testRequirementsList() {
		list.deleteRequirementById(0);
		list.executeCommand(0, new Command(CommandValue.ACCEPT, null, null, 1, "1 day", null, null));
		list.getRequirementById(0);
		assertEquals(0, list.getRequirements().size());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#addRequirement(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddRequirement() {
		assertEquals(0, list.addRequirement("Summary", "Test"));
		assertEquals("Summary", list.getRequirementById(0).getSummary());
		assertEquals(1, list.addRequirement("Summary", "Test"));
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#addXMLReqs(java.util.List)}.
	 */
	@Test
	public void testAddXMLReqs() {
		
		try {
			list.addXMLReqs(new RequirementsReader("test-files/req1.xml").getReqs());
		} catch (RequirementIOException e) {
			fail("invalid file");
		}
		assertEquals("SubmittedToAccepted", list.getRequirementById(0).getAcceptanceTestId());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#getRequirements()}.
	 */
	@Test
	public void testGetRequirements() {
		assertEquals(0, list.addRequirement("Summary", "Test"));
		assertEquals(0, list.getRequirements().get(0).getRequirementId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#getRequirementById(int)}.
	 */
	@Test
	public void testGetRequirementById() {
		assertEquals(0, list.addRequirement("Summary", "Test"));
		Requirement req = list.getRequirementById(0);
		assertEquals("Test", req.getAcceptanceTestId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#executeCommand(int, edu.ncsu.csc216.tracker.requirement.Command)}.
	 */
	@Test
	public void testExecuteCommand() {
		assertEquals(0, list.addRequirement("Summary", "Test"));
		list.executeCommand(0, new Command(CommandValue.ACCEPT, null, null, 1, "1 day", null, null));
		Requirement req = list.getRequirementById(0);
		assertEquals("1 day", req.getEstimate());
		assertEquals("Accepted", req.getState().getStateName());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsList#deleteRequirementById(int)}.
	 */
	@Test
	public void testDeleteRequirementById() {
		assertEquals(0, list.addRequirement("Summary", "Test"));
		list.deleteRequirementById(0);
		assertEquals(0, list.getRequirements().size());
	}

}
