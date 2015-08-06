package mango.condor.servlet.util.Task;

import java.text.ParseException;


public class MomentTask {
	
	public static void main(String[] args) throws InterruptedException, ParseException {
		
		TaskManager pool = new TaskManager();
		
		pool.registerRepeat(new MomentTaskBase("Task1", 2, 5, CycleType.SECOND, MomentTaskBase.isRunLater) {
			@Override
			public void run() {
				System.out.println(getTaskName() + "-" + System.currentTimeMillis());
			}
		});
		
		pool.start();
		System.out.println(System.currentTimeMillis());
		
	}
	
	
	
}
