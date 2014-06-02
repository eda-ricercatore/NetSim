import java.util.Arrays;
public class Test {
	public Test() {
	}
	
	public static void main(String[] args) {
		int[] a= {35, 12, 68, 59, 4, 95};
		System.out.println("a is: "+getStr(a));
		Arrays.sort(a);
		System.out.println("a is now: "+getStr(a));
	}
	
	private static String getStr(int[] a) {
		String b="";
		for(int i=0;i<a.length;i++) {
			b=b+" "+a[i];
		}
		return b;
	}
}