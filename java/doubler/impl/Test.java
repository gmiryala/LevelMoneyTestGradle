package doubler.impl;

import com.co.levelmoney.test.domain.Summary;

public class Test {
    public static void main(String args[])
    {

    	Summary summary = new Summary();
    	summary.setSpent(-188880);
    	summary.setIncome(1988888);

    	System.out.println(summary);
    }
}
