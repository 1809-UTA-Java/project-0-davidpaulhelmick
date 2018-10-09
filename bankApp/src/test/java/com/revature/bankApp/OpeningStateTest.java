package com.revature.bankApp;

import static org.junit.Assert.*;

import org.junit.Test;

public class OpeningStateTest {

	@Test
	public void testAcceptInputInvalid() {
		State state = new OpeningState();
		state.acceptInput("t");
		assertEquals(new OpeningState(), State.state);
	}
	
	@Test
	public void testAcceptInputLogin() {
		State state = new OpeningState();
		state.acceptInput("l");
		assertEquals(new LoginState(), State.state);
	}
	
	@Test
	public void testAcceptInputRegister() {
		State state = new OpeningState();
		state.acceptInput("r");
		assertEquals(new RegisterState(), State.state);
	}
	
	@Test
	public void testAcceptInputQuit() {
		State state = new OpeningState();
		state.acceptInput("e");
		assertEquals(true, State.quit);
	}
	
	@Test
	public void testAcceptInputUpperCase() {
		State state = new OpeningState();
		state.acceptInput("L");
		assertEquals(new LoginState(), State.state);
		
		state = new OpeningState();
		state.acceptInput("R");
		assertEquals(new RegisterState(), State.state);
		
		state = new OpeningState();
		state.acceptInput("E");
		assertEquals(true, State.quit);
	}

}
