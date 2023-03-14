import java.util.Stack;

/*
 * All Combinations Shown as Follow:
 * 12-3-4+5-6+7+89
 * 123-4-5-6-7+8-9
 * 123-45-67+89
 * 123+4-5+67-89
 * 123+45-67+8-9
 * 12+3-4+5+67+8+9
 * 12+3+4+5-6-7+89
 * 1+23-4+56+7+8+9
 * 1+23-4+5+6+78-9
 * 1+2+3-4+5+6+78+9
 * 1+2+34-5+67-8+9
 * -1+2-3+4+5+6+78+9
 * Totally 12 Combinations
 */



public class BSHH {
	
	public static void possibleSum(int N) {
        /*
         * 3 kinds of operator:
         * op[i] = 0 means "-"
         * op[i] = 1 means "merge"
         * op[i] = 2 means "+"
         */
        int count = 0; // Number of Combinations
        int op[] = new int[9]; // 9 operator-placed space before the number
        // only 2 kinds of operator at first operator-placed space, others 3
        for (op[0] = 0; op[0] < 2; op[0]++)
            for (op[1] = 0; op[1] < 3; op[1]++)
                for (op[2] = 0; op[2] < 3; op[2]++)
                    for (op[3] = 0; op[3] < 3; op[3]++)
                        for (op[4] = 0; op[4] < 3; op[4]++)
                            for (op[5] = 0; op[5] < 3; op[5]++)
                                for (op[6] = 0; op[6] < 3; op[6]++)
                                    for (op[7] = 0; op[7] < 3; op[7]++)
                                        for (op[8] = 0; op[8] < 3; op[8]++) {
                                            if (AlgebraExpress(op) == N) {
                                            count++;
                                        }
                                    }
        System.out.println("Totally " + count + " Combinations");
    }

	
	public static int AlgebraExpress(int[] op) {
        /*
         * Represent an expression as a string:
         * 1 - Append the first operator
         * 2 - Append "first number then operator" one by one
         * 3 - Append the last number 
         */
        StringBuffer express = new StringBuffer() ;
        String[] a = {"1","2","3","4","5","6","7","8","9"};
        // 1 - Append the first operator
        if(op[0] == 1){
            express.append('-');
        }
        // 2 - Append "first number then operator" one by one
        for(int i = 0, j = 1; j < 9; j++, i++) {
            express.append(a[i]);
            if(op[j] == 0) {
                express.append('-');
            }
            else if(op[j] == 2) {
                express.append('+');
            }
            else
                continue;
        }
        // 3 - Append the last number 
        express.append(a[8]);
        
        // Calculate sum
        Integer re1 = expressSum(express);
        // print the expression which satisfy sum is 100
        if (re1 == 100) {
        	System.out.println(express.reverse());
        }
        return re1;
 
        
    }


	private static Integer expressSum(StringBuffer express) {
		// Use Stack to Calculate one String expressed expression
		// opStack save operator in expression
        Stack<Character> opStack = new Stack<Character>();
        // numStack save number in expression
        Stack<Integer> numStack = new Stack<Integer>();
        // scale - Auxiliary calculation of higher digits
        int scale = -1; 
        int sum = 0;
        String revExpress = express.reverse().toString();
        for(int i = 0; i < revExpress.length(); i++) {
        	if (!isOperator(revExpress.charAt(i))) {
        		scale += 1;
        		// If scale is not equal to zero, it means there are multiple letters merged
        		// We should operate by digit, for example 123 = 1*10^2 + 2*10^1 + 3
        		if (scale != 0) {
        			int last = numStack.pop();
        			numStack.push(last + (int)(Integer.parseInt(String.valueOf(revExpress.charAt(i))) * Math.pow(10, scale)));
        		} else {
        			numStack.push(Integer.parseInt(String.valueOf(revExpress.charAt(i))));
        		}
        		
        	} else {
        		scale = -1;
        		opStack.push(revExpress.charAt(i));
        	}
        }
        // push one '+' if there is no '-' before 1 for convenient
        if (numStack.size() != opStack.size()) {
        	opStack.push('+');
        }
        // Calculate the sum - first operator then number
        while (!numStack.isEmpty()&& !opStack.isEmpty()) {
        	if(opStack.pop() == '+') {
        		sum += numStack.pop();
        	} else {
        		sum -= numStack.pop();
        	}
        }
        
        return sum;
    }
	
	
	// Judge whether it is Operator
	public static boolean isOperator(char c) {
		if (c == '+' || c == '-') {
			return true;
		}
		return false;
	}
	

	public static void main(String[] args) {
		// Specify target sum
		possibleSum(100);

	}

}
