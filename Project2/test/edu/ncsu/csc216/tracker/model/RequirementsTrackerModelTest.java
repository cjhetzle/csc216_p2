/**
 * 
 */
package edu.ncsu.csc216.tracker.model;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;

/**
 * tests RequirementsTrackerModel
 * @author Collin
 *
 */
public class RequirementsTrackerModelTest {
	private RequirementsTrackerModel model;
	/**
	 * sets up the tests
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		model = RequirementsTrackerModel.getInstance();
	}
	
	/**
	 * Cleans up after tests
	 * @throws Exception
	 */
			
	@After
	public void cleanUp() throws Exception {
		model.createNewRequirementsList();
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#getRequirementListAsArray()}.
	 */
	@Test
	public void testGetRequirementListAsArray() {
		model.addRequirement("Summary1", "test1");
		model.addRequirement("Summary2", "test2");
		Object[][] reqs = model.getRequirementListAsArray();
		assertEquals(reqs[0][0], 0);
		assertEquals(reqs[0][1], "Submitted");
		assertEquals(reqs[0][2], "Summary1");
		assertEquals(reqs[1][0], 1);
		assertEquals(reqs[1][1], "Submitted");
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#getInstance()}.
	 */
	@Test
	public void testGetInstance() {
		model.addRequirement("Summary1", "Test1");
		model = null;
		model = RequirementsTrackerModel.getInstance();
		assertEquals(model.getRequirementListAsArray().length, 1); 
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#executeCommand(int, edu.ncsu.csc216.tracker.requirement.Command)}.
	 */
	@Test
	public void testExecuteCommand() {
		model.addRequirement("Summary1", "test1");
		model.executeCommand(0, new Command(CommandValue.ACCEPT, null, null, 1, "estimate1", null, null));
		Object[][] reqs = model.getRequirementListAsArray();
		assertEquals(reqs[0][0], 0);
		assertEquals(reqs[0][1], "Accepted");
		assertEquals(reqs[0][2], "Summary1");		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#getRequirementById(int)}.
	 */
	@Test
	public void testGetRequirementById() {
		model.addRequirement("Summary1", "Test1");
		Requirement req = model.getRequirementById(0);
		assertEquals(0, req.getRequirementId());
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#addRequirement(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testAddRequirement() {
		model.addRequirement("Summary1", "test1");
		model.executeCommand(0, new Command(CommandValue.ACCEPT, null, null, 1, "estimate1", null, null));
		Object[][] reqs = model.getRequirementListAsArray();
		assertEquals(reqs[0][0], 0);
		assertEquals(reqs[0][1], "Accepted");
		assertEquals(reqs[0][2], "Summary1");	
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#deleteRequirementById(int)}.
	 */
	@Test
	public void testDeleteRequirementById() {
		model.addRequirement("Summary1", "test1");
		model.addRequirement("Summary2", "test2");
		Object[][] reqs = model.getRequirementListAsArray();
		assertEquals(reqs[0][0], 0);
		assertEquals(reqs[0][1], "Submitted");
		assertEquals(reqs[0][2], "Summary1");
		assertEquals(reqs[1][0], 1);
		assertEquals(reqs[1][1], "Submitted");
		model.deleteRequirementById(0);
		reqs = model.getRequirementListAsArray();
		assertEquals(reqs[0][0], 1);
		assertEquals(reqs[0][1], "Submitted");
		assertEquals(reqs[0][2], "Summary2");
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#createNewRequirementsList()}.
	 */
	@Test
	public void testCreateNewRequirementsList() {
		model.addRequirement("Summary1", "Test1");
		model.createNewRequirementsList();
		assertEquals(0, model.getRequirementListAsArray().length);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#loadRequirementsFromFile(java.lang.String)}.
	 */
	@Test
	public void testLoadRequirementsFromFile() {
		model.loadRequirementsFromFile("test-files/req1.xml");
		assertEquals(model.getRequirementById(0).getAcceptanceTestId(), "SubmittedToAccepted");
		assertEquals(model.getRequirementById(1).getAcceptanceTestId(), "SubmittedToAccepted");
		assertEquals(model.getRequirementById(2).getAcceptanceTestId(), "SubmittedToAccepted");
		assertEquals(model.getRequirementById(4).getAcceptanceTestId(), "SubmittedToAccepted");
		assertEquals(model.getRequirementById(23).getAcceptanceTestId(), "SubmittedToAccepted");
		assertEquals(model.getRequirementById(32).getAcceptanceTestId(), "SubmittedToAccepted");
	}


	/**
	 * Test method for {@link edu.ncsu.csc216.tracker.model.RequirementsTrackerModel#saveRequirementsToFile()}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testSaveRequirementsToFile() throws FileNotFoundException {
		model.loadRequirementsFromFile("test-files/req1.xml");
		model.saveRequirementsToFile("test-files/actual.xml");
		Scanner actualFile = new Scanner(new FileInputStream("test-files/actual.xml"));
		Scanner expectedFile = new Scanner(new FileInputStream("test-files/req1.xml"));
		String actual = actualFile.next();
		String expected = expectedFile.next();
		actualFile.close();
		expectedFile.close();
		assertEquals(expected, actual);
		
	}
}
