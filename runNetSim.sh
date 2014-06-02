#!/bin/sh
# UNIX Bourne Shell Script, written by Zhiyang Ong to run NetSim
# @Run NetSim

# Also see	@make
# 			for UNIX command that can do this

# ==============================================================

# Run NetSim
	# Get rid of all class files
	rm ecomp/*.class
	rm gui/*.class
	rm population/*.class
	rm population/graph/*.class
	rm population/graph/tests*.class
	rm population/tests/*.class
	rm utility/*.class
	rm utility/tests/*.class

	# Compile the program
	javac ecomp/*java
	javac gui/*java
	javac population/*java
	javac population/graph/*java
	javac population/graph/tests/*java
	javac population/tests/*java
	javac utility/*java
	javac utility/tests/*java
	javac gui/Optimizer.java

	# Run the program
	#java -Xms8m gui/Optimizer
	java -Xmx80m gui/Optimizer

# End of script



