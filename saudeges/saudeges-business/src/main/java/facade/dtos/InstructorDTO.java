package facade.dtos;

import java.io.Serializable;

public class InstructorDTO implements Serializable {

	private static final long serialVersionUID = 6749630927022129869L;
	
	private int id;
	private String name;
	
	public InstructorDTO(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

}
