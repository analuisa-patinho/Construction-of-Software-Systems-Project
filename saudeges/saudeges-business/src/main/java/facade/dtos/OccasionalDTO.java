package facade.dtos;

import java.io.Serializable;

public class OccasionalDTO implements Serializable {

	private static final long serialVersionUID = 7970947974669356287L;
	
	private int id;
	private String name;
	private int sessionNr;
	
	public OccasionalDTO(int id, String name, int sessionNr) {
		this.id = id;
		this.name = name;
		this.sessionNr = sessionNr;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public int getSessionNr() {
		return sessionNr;
	}

}
