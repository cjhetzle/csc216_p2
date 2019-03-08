/**
 * 
 */
package edu.ncsu.csc216.tracker.model;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.tracker.requirement.Command;
import edu.ncsu.csc216.tracker.requirement.Requirement;
import edu.ncsu.csc216.tracker.xml.Req;
/**
 * ReqiurementsList Class
 * @author Collin
 * 
 */
public class RequirementsList {

	private ArrayList<Requirement> requirements;
	/**
	 * RequirementsList constructor
	 */
	public RequirementsList() {
		requirements = new ArrayList<Requirement>();
		Requirement.setCounter(0);
	}
	
	/**
	 * addRequirement
	 * @param summary Summary of new requirement
	 * @param test I don't know what the value is
	 * @return int requirement id
	 */
	public int addRequirement(String summary, String test) {
		Requirement req = new Requirement(summary, test);
		requirements.add(req);
		return req.getRequirementId();
	}
	
	/**
	 * addXmlReqs
	 * @param list List<Req> list of requirements from xml file
	 */
	public void addXMLReqs(List<Req> list) {
		for(Req req : list) {
			requirements.add(new Requirement(req));
		}
	}
	
	/**
	 * getRequirements
	 * @return reqs List all requirements
	 */
	public List<Requirement> getRequirements() {
		return requirements;
	}
	
	/**
	 * getRequirementBy
	 * @param id of requirement to get
	 * @return Requirement retrieved
	 */
	public Requirement getRequirementById(int id) {
		for(Requirement req : requirements) {
			if (req.getRequirementId() == id) {
				return req;
			}
		}
		return null;
	}
	
	/**
	 * executeCommand
	 * @param id int id of requirement to use
	 * @param c Command to use
	 */
	public void executeCommand(int id, Command c) {
		if(requirements.size() == 0) {
			return;
		}
		getRequirementById(id).update(c);
	}
	
	/**
	 * deleteRequirementById
	 * @param id Requirement to get
	 */
	public void deleteRequirementById(int id) {
		if(requirements == null) {
			return;
		}
		for(int i = 0; i < requirements.size(); i++) {
			if(requirements.get(i).getRequirementId() == id) {
				requirements.remove(i);
			}
		}
	}

}
