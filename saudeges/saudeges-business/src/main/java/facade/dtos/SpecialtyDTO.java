package facade.dtos;

import java.io.Serializable;

public class SpecialtyDTO implements Serializable {

	
	private static final long serialVersionUID = 3800789398049148722L;
	private int id;
	private String designation;
	private int minDuration;
	private String certification;
	
	public SpecialtyDTO(int id, String designation, int minDuration, String certification) {
		this.id = id;
		this.designation = designation;
		this.minDuration = minDuration;
		this.certification = certification;
	}
	
	public int getId() {
		return id;
	}

	public String getDesignation() {
		return designation;
	}

	public int getMinDuration() {
		return minDuration;
	}
	
	public String getCertification() {
		return certification;
	}

}
