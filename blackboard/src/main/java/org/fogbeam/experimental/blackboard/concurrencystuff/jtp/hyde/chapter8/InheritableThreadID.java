package org.fogbeam.experimental.blackboard.concurrencystuff.jtp.hyde.chapter8;

public class InheritableThreadID
{
	public static final int UNIQUE = 101;
	public static final int INHERIT = 102;
	public static final int SUFFIX = 103;
	
	private ThreadLocal<String> threadLocal;
	private int nextId;
	
	public InheritableThreadID( int type )
	{
		nextId = 201;
		
		switch( type )
		{
			case UNIQUE:
				threadLocal = new ThreadLocal<String>() 
				{
					@Override
					protected String initialValue()
					{
						print( "in initialValue()" );
						return getNewId();
					}
				};
				break;
				
			case INHERIT:
				threadLocal = new InheritableThreadLocal<String>() 
				{
					@Override
					protected String initialValue()
					{
						print( "in initialValue()" );
						return getNewId();
					}
				};
				break;
				
			case SUFFIX:
				threadLocal = new InheritableThreadLocal<String>() 
				{
					@Override
					protected String initialValue()
					{
						print( "in initialValue()" );
						return getNewId();
					}
					
					@Override
					protected String childValue( String parentValue )
					{
						print( "in childValue() - parentValue = " + parentValue );
						
						return parentValue + "-CH";
					}
				};
				break;
				
			default:
				throw new RuntimeException( "WTF" );
		}
		
	}
	
	private synchronized String getNewId()
	{
		String id = "ID" + nextId;
		nextId++;
		return id;
	}
	
	public String getId()
	{
		return threadLocal.get();
	}
	
	private static void print( String msg )
	{
		String name = Thread.currentThread().getName();
		System.out.println( name + " : " + msg );
	}
	
	public static Runnable createTarget( InheritableThreadID id )
	{
		final InheritableThreadID var = id;
		
		Runnable parentRun = new Runnable() 
		{
			@Override
			public void run()
			{
				print( "var.getId() = " + var.getId() );
				print( "var.getId() = " + var.getId() );
				print( "var.getId() = " + var.getId() );
				
				Runnable childRun = new Runnable() 
				{
					@Override
					public void run()
					{
						print( "var.getId() = " + var.getId() );
						print( "var.getId() = " + var.getId() );
						print( "var.getId() = " + var.getId() );
					}
				};
				
				Thread parentT = Thread.currentThread();
				String parentName = parentT.getName();
				print( "Creating a child Thread of " + parentName );
				
				Thread childT = new Thread( childRun, parentName + "-child" );
				childT.start();
			}
		};
		
		return parentRun;
	}
	
	public static void main( String[] args ) throws InterruptedException
	{
		System.out.println( "====== ThreadLocal ======" );
		
		InheritableThreadID varA = new InheritableThreadID( UNIQUE );
		Runnable targetA = createTarget( varA );
		Thread threadA = new Thread( targetA, "threadA" );
		threadA.start();
		
		Thread.sleep(  2500 );
		
		System.out.println( "====== InheritableThreadLocal ======" );
		
		InheritableThreadID varB = new InheritableThreadID( INHERIT );
		Runnable targetB = createTarget( varB );
		Thread threadB = new Thread( targetB, "threadB" );
		threadB.start();
		
		Thread.sleep(  2500 );
		
		System.out.println( "====== InheritableThreadLocal - custom child value ======" );
		
		InheritableThreadID varC = new InheritableThreadID( SUFFIX );
		Runnable targetC = createTarget( varC );
		Thread threadC = new Thread( targetC, "threadC" );
		threadC.start();
		
		
		System.out.println( "done" );
	}
}
