#!/bin/sh
rm -f NetworkGAImpError*.txt NetworkGAImpNormal*.txt 
rm -f ModuleTestNetworkGAImp*.class

javac -classpath ../..:. ModuleTestNetworkGA01.java 
java -classpath ../..:. ModuleTestNetworkGA01
less NetworkGAImpError01.txt 
less NetworkGAImpNormal01.txt 

javac -classpath ../..:. ModuleTestNetworkGA02.java 
java -classpath ../..:. ModuleTestNetworkGA02
less NetworkGAImpError02.txt 
less NetworkGAImpNormal02.txt 
