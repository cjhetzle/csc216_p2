/**
 * 
 */
package edu.ncsu.csc216.tracker.model;

import java.util.List;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.xml.RequirementsWriter;
import edu.ncsu.csc216.tracker.xml.RequirementIOException;
import edu.ncsu.csc216.tracker.xml.RequirementsReader;

/**
 * RequirementsTrackerModel Class
 * @author Collin
 *
 */
public class RequirementsTrackerModel {
	private static RequirementsTrackerModel singleton = null;
	private RequirementsList reqList;
	/**
	 * RequirementTrackerModel Constructor
	 */
	private RequirementsTrackerModel() {
		reqList = new RequirementsList();
	}
	
	/**
	 * getRequirementListAsArray
	 * @return Object[][]
	 */
	public Object[][] getRequirementListAsArray() {
		List<Requirement> list = reqList.getRequirements();
		Object[][] output = new Object[list.size()][3];
		for(int i = 0; i < output.length; i++) {
			output[i][0] = list.get(i).getRequirementId();
			output[i][1] = list.get(i).getState().getStateName();
			output[i][2] = list.get(i).getSummary();
		}
		return output;
	}
	
	/**
	 * getInstance
	 * @return singleton instance
	 */
	public static RequirementsTrackerModel getInstance() {
		if (singleton == null) {
			singleton = new RequirementsTrackerModel();
		}
		return singleton;
	}
	
	/**
	 * executeCommand
	 * @param id Requirement ID
	 * @param c Command 
	 */
	public void executeCommand(int id, Command c) {
		reqList.executeCommand(id, c);
	}
	
	/**
	 * getRequirementById
	 * @param id Requirement ID
	 * @return Requirement found with ID
	 */
	public Requirement getRequirementById(int id) {
		return reqList.getRequirementById(id);
	}
	
	/**
	 * addRequirement
	 * @param summary Summary of new Requirement
	 * @param test String the acceptance test required to make a Requirement
	 */
	public void addRequirement(String summary, String test) {
		reqList.addRequirement(summary, test);
	}
	
	/**
	 * deleteRequirementById
	 * @param id ID of requirement
	 */
	public void deleteRequirementById(int id) {
		reqList.deleteRequirementById(id);
	}
	
	/**
	 * saveRequirementToFile
	 * @param fileName Name of file to save to
	 */
	public void saveRequirementsToFile(String fileName) {
		RequirementsWriter writer = new RequirementsWriter(fileName);
		List<Requirement> list = reqList.getRequirements();
		for(Requirement req : list) {
			writer.addReq(req.getXMLReq());
		}
		try {
			writer.marshal();
		} catch (RequirementIOException e) {
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * createNewRequirementsList
	 */
	public void createNewRequirementsList() {
		reqList = new RequirementsList();
	}
	
	/**
	 * loadRequirementsFromFile
	 * @param fileName Name of file to load in
	 */
	public void loadRequirementsFromFile(String fileName) {
		RequirementsReader file;
		try {
			file = new RequirementsReader(fileName);
		} catch (RequirementIOException e) {
			throw new IllegalArgumentException();
		}
		reqList.addXMLReqs(file.getReqs());
		
	}

}
