import java.util.ArrayList;

public class FCFS {
	int time = 0, index = 0, entries, seeker = 50, nextIndex, dif, taken = 0;
	double aveScore = 0, maxScore = 0, totalScore = 0, aveDelay = 0, maxDelay = 0, totalDelay = 0;
	ArrayList<SeqReq> master;
	ArrayList<SeqReq> workingList = new ArrayList<SeqReq>();
	
	public FCFS(ArrayList<SeqReq> newMaster, int entries) {
		master = newMaster;
		this.entries = entries;
	}
	
	public void work() {
		boolean done = false;
		while(!done) {
			
			//If < 5 entries left, add 10 more from master
			if(workingList.size() < 5 && taken < entries) {
				taken += 10; //Ensures we dont pull out of
				for(int i = taken - 10; i < taken; i++) {
					master.get(i).start(time);
					workingList.add(master.get(i));
				}
			}
			
			//Get nextIndex and difference between that and seeker
			nextIndex = master.get(index).getIndex();
			dif = Math.abs(nextIndex - seeker);
			
			//Set seeker to index we're looking for, increment time, finish request.
			seeker = nextIndex;
			time += dif;
			workingList.get(0).finishReq(time);
			totalScore += workingList.get(0).getScore();
			totalDelay += workingList.get(0).getDelay();
			
			//Keep track of MaxScore and MaxDelay
			if(workingList.get(0).getScore() > maxScore)
				maxScore = workingList.get(0).getScore();
			
			if(workingList.get(0).getDelay() > maxDelay)
				maxDelay = workingList.get(0).getDelay();
			
			//Increment index, remove entry,
			index++;
			workingList.remove(0);
			if (index == entries)
				done = true;
			
		}
		aveDelay = totalDelay / entries;
		aveScore = totalScore / entries;
		
	}
	
	public void printValues() {
		System.out.println("AveDelay: " + aveDelay + "\nMaxDelay: " + maxDelay + "\naveScore: " + aveScore + "\nmaxScore: " + maxScore);
	}
	
}
