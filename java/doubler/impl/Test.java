package doubler.impl;

import com.co.levelmoney.test.domain.MonthSummary;
import com.co.levelmoney.test.domain.Summary;

public class Test {
    public static void main(String args[])
    {
    	MonthSummary ms = new MonthSummary();
    	Summary summary = new Summary();
    	summary.setSpent(-188880);
    	summary.setIncome(1988888);
    	
    	ms.setMonth("2017-01");
    	ms.setSummary(summary);
    	System.out.println(ms);
    }
}
