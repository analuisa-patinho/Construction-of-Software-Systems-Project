package facade.dtos;

import java.io.Serializable;

public class RegularDTO implements Serializable {

	private static final long serialVersionUID = -8943045178887268341L;
	
	private int id;
	private String name;
	private int numSessions;
	private int maxParticipants;
	
	public RegularDTO(int id, String name, int numSessions, int maxParticipants) {
		this.id = id;
		this.name = name;
		this.numSessions = numSessions;
		this.maxParticipants = maxParticipants;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getNumSessions() {
		return numSessions;
	}
	
	public int getMaxParticipants() {
		return maxParticipants;
	}
}
