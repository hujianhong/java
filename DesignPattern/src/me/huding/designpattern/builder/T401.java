package me.huding.designpattern.builder;

public class T401 extends Computer {
	
	private String graphicCard;
	
	public T401(){
		this.setType("ThinkPad T410i");
	}

	public String getGraphicCard() {
		return graphicCard;
	}

	public void setGraphicCard(String graphicCard) {
		this.graphicCard = graphicCard;
	}

	@Override
	public String toString() {
		return "T401 [graphicCard=" + graphicCard + ", getType()=" + getType() + ", getCpu()=" + getCpu()
				+ ", getRam()=" + getRam() + ", getHardDisk()=" + getHardDisk() + ", getMonitor()=" + getMonitor()
				+ ", getOs()=" + getOs() + "]";
	}
	
	
	
	

}
