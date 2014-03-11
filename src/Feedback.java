import java.util.ArrayList;


class Feedback {
	private String description;
	private boolean exit;
	
	public Feedback(String desc, boolean x) {
		description = desc;
		exit = x;
	}
	
	public Feedback(ArrayList<String> desc, boolean x) {
		String tempDesc = "";
		for (int i = 0; i < desc.size(); i++) {
			tempDesc += (desc.get(i) + "\n");
		}
		
		description = tempDesc;
		exit = x;
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
}
