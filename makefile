# This is written by Zhiyang Ong to run the telecommunication network simulator, NetSim, for honors thesis

# Make target to compile and run the program
all:
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
	

# Count the number of lines of code
klocs:
	cat ecomp/*.java ecomp/tests/*.java gui/*.java population/*.java population/graph/*.java population/graph/tests/*.java population/tests/*.java scripts/*.m tests/*.m tests/*.java utility/*.java utility/tests/*.java *.m *.sh	| wc -l
	


# Compile the program
compile:
	javac ecomp/*.java
	#javac ecomp/tests/*.java
	javac gui/*.java
	javac population/*.java
	javac population/graph/*.java
	javac population/graph/tests/*.java
	javac population/tests/*.java
	javac tests/*.java
	javac utility/*.java
	javac utility/tests/*.java