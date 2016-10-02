class DivDPath(val n: Int) extends Module {
	//io here
	val io = new Bundle{
		val x = UInt(INPUT, n); // numerator
		val y = UInt(INPUT, n); // denominator
		val z = UInt(OUTPUT, n); //result
	}
	//initializing some stuff
	val numerator = Reg(init = UInt(0, width = n));
	val denominator = Reg(init = UInt(0, width = n));
	val result = UInt(0);
	val count = n;
	// non repeating algorithm as outlined here http://stackoverflow.com/questions/12133810/non-restoring-division-algorithm
	
	for( count > 0){
		when(result < 0){
			result := result >> UInt(1);
			denominator := denominator >> UInt(1);
			result := result + numerator;
		} .otherwise {
			result := result >> UInt(1);
			denominator := denominator >> UInt(1);
			result := result - numerator;
		}
		
		when(result < 0){
			denominator(n-2,n-1) := UInt(0);
		} .otherwise {
			denominator(n-2,n-1) := UInt(1);
		}
		
		count := count - UInt(1);
	}
	
	when(result < 0){
		result := result + numerator;
	}
	
	io.z := result;
}
