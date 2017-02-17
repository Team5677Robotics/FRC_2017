package org.usfirst.frc.team5677.robot.loops;

import edu.wpi.first.wpilibj.Notifier;
import org.usfirst.frc.team5677.robot.loops.Loop;

public class MultiLooper{

    private Notifier[] ns;
    
    public MultiLooper(){
    }

    public void start(Loop[] loops){
	Notifier[] n = new Notifier[loops.length];
	for(int i=0; i<loops.length; i++){
	    Loop l = loops[i];
	    n[i] = new Notifier(l);
	    n[i].startPeriodic(0.01);
	}
	ns = n;
    }

    public void stop(){
	for(int i=0; i<ns.length; i++){
	    ns[i].stop();
	}
    }
}
