package me.huding.designpattern.builder;

public class T401Builder implements ComputerBuilder {
	
	private T401 computer = new T401();

	@Override
	public ComputerBuilder buildCPU() {
		// TODO Auto-generated method stub
		computer.setCpu("i5-450");
		return this;
	}

	@Override
	public ComputerBuilder buildRAM() {
		// TODO Auto-generated method stub
		computer.setRam("2GB");
		return this;
	}

	@Override
	public ComputerBuilder buildHardDisk() {
		// TODO Auto-generated method stub
		computer.setHardDisk("500GB 7200转");
		return this;
	}

	@Override
	public ComputerBuilder buildOS() {
		computer.setOs("Windows");
		return this;
	}

	@Override
	public ComputerBuilder buildGraphicCard() {
		computer.setGraphicCard("Nvidia NVS 3100M");
		return this;
	}

	@Override
	public ComputerBuilder buildMonitor() {
		// TODO Auto-generated method stub
		computer.setMonitor("14英寸 1280 * 800");
		return this;
	}

	@Override
	public Computer create() {
		// TODO Auto-generated method stub
		return computer;
	}

}
