package edu.ncsu.csc216.tracker.requirement;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.tracker.requirement.enums.CommandValue;
/**
 * test the command class
 * @author Collin
 *
 */
public class CommandTest {

	/**
	 * test the construction of Command
	 */
	@Test
	public void commandConstructTest() {
		try {
			new Command(CommandValue.ACCEPT, null, null, 1,
					"1 day", null, null);
			new Command(CommandValue.ASSIGN, null, null, 0,
					null, "developer", null);
		} catch (IllegalArgumentException e) {
			fail();
		}
	}

}
