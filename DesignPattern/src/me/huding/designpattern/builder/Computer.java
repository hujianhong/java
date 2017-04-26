package me.huding.designpattern.builder;

public abstract class Computer {
	
	private String type;
	
	private String cpu;
	
	private String ram;
	
	private String hardDisk;
	
	private String monitor;
	
	private String os;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getHardDisk() {
		return hardDisk;
	}

	public void setHardDisk(String hardDisk) {
		this.hardDisk = hardDisk;
	}

	public String getMonitor() {
		return monitor;
	}

	public void setMonitor(String monitor) {
		this.monitor = monitor;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}
	
	
	

}
