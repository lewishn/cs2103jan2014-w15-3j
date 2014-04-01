import java.util.ArrayList;


class Feedback {
	private String description;
	private boolean exit;
	
	public Feedback(String desc) {
		description = desc;
		exit = false;
	}
	
	public Feedback(ArrayList<String> desc) {
		String tempDesc = "";
		for (int i = 0; i < desc.size(); i++) {
			tempDesc += (desc.get(i) + "\n");
		}
		
		description = tempDesc;
		exit = false;
	}
	
	public Feedback(String desc, boolean x) {
		description = desc;
		exit = x;
	}
	
	public String getDesc() {
		return this.description;
	}
	
	public boolean getExitFlag() {
		return exit;
	}
	
	public void setDescription(String desc) {
		description = desc;
	}
	
	@Override
	public String toString(){
		return description;
	}
	
	public boolean equals(String string){
		return string.equals(description);
	}
}
