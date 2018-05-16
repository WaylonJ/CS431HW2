import java.util.ArrayList;

public class SSF {
	int time = 0, index = 0, entries, seeker = 50, nextIndex, dif, taken = 0;
	double aveScore = 0, maxScore = 0, totalScore = 0, aveDelay = 0, maxDelay = 0, totalDelay = 0;
	ArrayList<SeqReq> master;
	ArrayList<SeqReq> workingList = new ArrayList<SeqReq>();
	
	public SSF(ArrayList<SeqReq> newMaster, int entries) {
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
			nextIndex = findNextIndex(workingList, seeker);
			dif = Math.abs(workingList.get(nextIndex).getIndex() - seeker);
			
			//Set seeker to index we're looking for, increment time, finish request.
			seeker = workingList.get(nextIndex).getIndex();
			time += dif;
			workingList.get(nextIndex).finishReq(time);
			totalScore += workingList.get(nextIndex).getScore();
			totalDelay += workingList.get(nextIndex).getDelay();
			
			//Keep track of MaxScore and MaxDelay
			if(workingList.get(0).getScore() > maxScore)
				maxScore = workingList.get(0).getScore();
			
			if(workingList.get(0).getDelay() > maxDelay)
				maxDelay = workingList.get(0).getDelay();
			
			//Increment index, remove entry,
			index++;
			workingList.remove(nextIndex);
			if (index == entries)
				done = true;
			
		}
		aveDelay = totalDelay / entries;
		aveScore = totalScore / entries;
		
	}
	
	private int findNextIndex(ArrayList<SeqReq> working, int seeker) {
		int index, dif, newDif;
		index = 0;
		dif = Math.abs(seeker - working.get(0).getIndex());
		
		for(int i = 1; i < working.size(); i++) {
			newDif = Math.abs(working.get(i).getIndex() - seeker);
			if(newDif < dif) {
				dif = newDif;
				index = i;
			}
		}
		
		return index;
	}
	
	public void printValues() {
		System.out.println("AveDelay: " + aveDelay + "\nMaxDelay: " + maxDelay + "\naveScore: " + aveScore + "\nmaxScore: " + maxScore);
	}
	
}
