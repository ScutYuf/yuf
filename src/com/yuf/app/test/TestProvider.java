package com.yuf.app.test;

import com.yuf.app.db.MyProvider;
import com.yuf.app.db.Order;


public class TestProvider extends android.test.ProviderTestCase2<MyProvider> {

	
	 public TestProvider()  
	    {  
	        super(MyProvider.class, "com.yuf.app.db.MyProvider");  
	    }  
	public void testSave()
	{

		Order order=new Order();
		order.writeToDb();
	}

}
