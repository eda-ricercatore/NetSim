NetSim
======

# Summary
An optimization tool for telecommunication networks. It is optimized via a multi-objective evolutionary algorithm, so that the trade-off between pleiotropy and redundancy can be explored.


# Not-so-Terse Description

It is primarily developed in Java, and has MATLAB scripts to process the experimental data for statistical analysis.

It also uses a Makefile (you can also use the UNIX shell script) to set up and run this program.

Its software architecture is organized into the following directories:  
1) utility: common functions that are used for different modules in the software;  
2) gui: the Graphical User Interface (GUI) for the software;  
3) tests: experimental test results;  
4) scripts: scripts for processing the experimental data in MATLAB;  
5) results: experimental test results;  
6) population: implementation of the graph data structure in the "graph" subdirectory and some graph algorithms used to determine certain properties of the telecommunication networks;  
7) ecomp: implementation of the multi-objective evolutionary algorithm  

There are also other files, such as the README.md file (i.e., this file). Where possible, unit test and module tests are provided in each software module/subdirectory.



## WARNING

This is the latest recent build that I can access. I was trying to modify the multi-objective evolutionary algorithm to seek Pareto optimal trade-offs in an attempt to meet a conference call for papers deadline. Unfortunately, I ended up mutilating the code, and have not worked on project since. Use the makefile to compile and run the project; that is, run: make all. To determine the number of lines of code, including comments, that my teammate and I have written, try: make klocs.







=============================================================

# Details, where are the details???

## Details about this work.

### Conference paper describing this work.

A research paper describing this work is available as a conference paper, which is referenced as follows.

**Z. Ong**, A. H.-W. Lo, M. Berryman, and D. Abbott, "Multi-objective evolutionary algorithm for investigating the trade-off between pleiotropy and redundancy," in Proceedings of SPIE Complex Systems, vol. 6039, Brisbane, Australia, pp. 237-248, 11-14 December, 2005. [DOI](http://dx.doi.org/10.1117/12.638406)


### Further details about this work.

[Further details of a thesis describing this work, including a set of presentation slides for the thesis defense and the research proposal are available](https://eda-ricercatore.github.io/ricerca/research-publications.html).


