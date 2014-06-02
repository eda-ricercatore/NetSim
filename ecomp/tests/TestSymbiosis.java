// importing packages
import utility.*;
import population.*;
import population.graph.*;
import ecomp.*;
import java.util.*;

/**
 * Test for symbiosis
 *
 * @author  Zhiyang Ong
 * @version 0.4.2
 */
public class TestSymbiosis {
    // instance variable to store info regarding current test
    private static String testName;
	
	//private static final double PROB_SYMBIOSIS = 0.30;
    
    // ---------------------------------------------------------------------
	 
    // Default constructor
    /**
     * Should not instantate a test class.
     */
    public TestSymbiosis() {
        Debugger.printErr("Don't instantiate a test class: " + 
                            "ModuleTestNetworkGA02");
    }
	
	// --------------------------------------------------------------------
    
    /**
     * Initiates tests for standard constructor, crossover and mutation, and
     * steady-state evolution and classical GA evolutioin.
     */
	public static void main(String[] args) {
        Debugger.pipeResult("SymbiosisNormal.txt",
                            "SymbiosisError.txt");
        Debugger.enableTrace(true);

        Debugger.debug("==================================\n"+
                       "filename: SymbiosisNormal.txt\n" +
                       "==================================");
        Debugger.debug("Test for Symbiosis:\n");
        
        Debugger.printErr("=================================\n"+
                          "filename: SymbiosisError.txt\n" +
                          "=================================");
        Debugger.printErr("Test for Symbiosis:\n");

        // test Symbiosis
		testSymbiosis();
        Debugger.debug("");

        Debugger.debug("========================================");
        Debugger.debug("Module Test for Symbiosis Completed");
        Debugger.debug("========================================");
        Debugger.printErr("========================================");
        Debugger.printErr("Module Test for Symbiosis Completed");
        Debugger.printErr("========================================");
    }
    
// ==========================================================================
    
    /**
     * Testing the basic accessor and mutator methods of "NetworkGAImp"
     */
    private static void testSymbiosis() {
		Debugger.enableTrace(true);
        Debugger.debug("Testing commensalism");
        Debugger.debug("==============================="+
                            "==============================\n");
		NetworkGAImp netwk = new NetworkGAImp();
		String label="client";
		int x=1;
		int y=2;
		int gen=3;
		double[] params={0.0,0.0,0.0,0.0};
		Node n;
		ArrayList nodes = new ArrayList(7);
		for(int i=0;i<7;i++) {
			n = new NodeImp(label+i,x,y,gen,params,true);
			nodes.add(n);
		}
		ArrayList dest = new ArrayList(7);
		Chromosome a = new Chromosome(nodes,2,5);
		Chromosome b = new Chromosome(nodes,2,5);
		dest.add(nodes.get(3));
		dest.add(nodes.get(5));
		//a.remove(0);
		a.setData(0,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(2));
		dest.add(nodes.get(3));
		dest.add(nodes.get(5));
		dest.add(nodes.get(6));
		//a.remove(1);
		a.setData(1,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(6));
	//	a.remove(2);
		a.setData(2,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(0));
		dest.add(nodes.get(1));
		dest.add(nodes.get(4));
		dest.add(nodes.get(5));
		dest.add(nodes.get(6));
		//a.remove(3);
		a.setData(3,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(1));
		dest.add(nodes.get(2));
		//a.remove(4);
		a.setData(4,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(0));
		dest.add(nodes.get(1));
		dest.add(nodes.get(2));
		dest.add(nodes.get(3));
		dest.add(nodes.get(4));
		dest.add(nodes.get(6));
		//a.remove(5);
		a.setData(5,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(3));
		dest.add(nodes.get(6));
		//a.remove(6);
		a.setData(6,dest);
		dest = new ArrayList(7);
		
		dest.add(nodes.get(2));
		dest.add(nodes.get(4));
		dest.add(nodes.get(6));
		b.setData(0,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(2));
		b.setData(1,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(0));
		b.setData(2,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(0));
		dest.add(nodes.get(1));
		dest.add(nodes.get(5));
		dest.add(nodes.get(6));
		b.setData(3,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(1));
		b.setData(4,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(2));
		dest.add(nodes.get(4));
		b.setData(5,dest);
		dest = new ArrayList(7);
		dest.add(nodes.get(2));
		b.setData(6,dest);
		Debugger.debug("a is:"+a.getLength());
		Debugger.debug("b is:"+b.getLength());
		Chromosome[] sym =netwk.symbiosis(a,b);
		a=sym[0];
		b=sym[1];
		for(int r=0;r<7;r++) {
			Debugger.debug("b: Node r:"+r);
			Debugger.debug("connected to:");
			//Debugger.enableTrace(false);
			for(int p=0;p<b.getData(r).size();p++) {
				//Debugger.enableTrace(true);
				Debugger.debug(""+((NodeImp)((ArrayList)b.getData(r)).get(p)).getLabel());
				//Debugger.enableTrace(false);
			}
		}

		for(int r=0;r<7;r++) {
			Debugger.debug("a: Node r:"+r);
			Debugger.debug("connected to:");
			//Debugger.enableTrace(false);
			for(int p=0;p<a.getData(r).size();p++) {
				//Debugger.enableTrace(true);
				Debugger.debug(""+((NodeImp)((ArrayList)a.getData(r)).get(p)).getLabel());
				//Debugger.enableTrace(false);
			}
		}
		
		
        Debugger.debug("");
        Debugger.debug("Testing of Symbiosis Completed");
        Debugger.debug("===================================="+
                            "===================================\n");
    }
}
