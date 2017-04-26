package me.huding.designpattern.builder;

public interface ComputerBuilder {
	
	ComputerBuilder buildCPU();
	ComputerBuilder buildRAM();
	ComputerBuilder buildHardDisk();
	ComputerBuilder buildOS();
	ComputerBuilder buildGraphicCard();
	ComputerBuilder buildMonitor();
	
	Computer create();

}
