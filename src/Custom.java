import java.util.ArrayList;

public class Custom {
	int time = 0, index = 0, entries, seeker = 50, nextIndex, dif, taken = 0, direction = 1;
	double aveScore = 0, maxScore = 0, totalScore = 0, aveDelay = 0, maxDelay = 0, totalDelay = 0;
	ArrayList<SeqReq> master;
	ArrayList<SeqReq> workingList = new ArrayList<SeqReq>();
	
	public Custom(ArrayList<SeqReq> newMaster, int entries) {
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
			
			//Check direction, then get nextIndex and difference between that and seeker
			direction = checkDirection(workingList, seeker, direction);
			nextIndex = findNextIndex(workingList, seeker, direction);
			dif = Math.abs(workingList.get(nextIndex).getIndex() - seeker);
			
			//Set seeker to index we're looking for, increment time, finish request.
			seeker = workingList.get(nextIndex).getIndex();
			time += dif;
			workingList.get(nextIndex).finishReq(time);
//			System.out.println(workingList.get(nextIndex).getIndex() + "  indeX: " + nextIndex);
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
	
	private int checkDirection(ArrayList<SeqReq> working, int seeker, int direction) {
		int sumAbove = 0, sumBelow = 0, maxAbove = 0, maxBelow = 100, dif, numAbove = 0, numBelow = 0;
		
		
		for(int i = 0; i < working.size(); i++) {
			dif = working.get(i).getIndex() - seeker;
			if(dif < 0) { //Below
				if(working.get(i).getIndex() < maxBelow)
					maxBelow = working.get(i).getIndex();
				sumBelow += Math.abs(dif);
				numBelow++;
			}
			else {
				if(working.get(i).getIndex() > maxAbove)
					maxAbove = working.get(i).getIndex();
				sumAbove += dif;
				numAbove++;
			}
		}
		
		if(numBelow == 0)
			return 1;
		if(numAbove == 0)
			return 0;
		
//		System.out.print("Switching directions. Was: " + direction);
		//Nothing found in current direction, switch directions.
		if(sumBelow / numBelow < sumAbove / numAbove) {
//			System.out.println(", now 0");
			return 0;
		}
			
		else {
//			System.out.println(", now 1");
			return 1;
		}
	}
	
	private int findNextIndex(ArrayList<SeqReq> working, int seeker, int direction) {
		int index, dif, newDif;
		index = 0;
		if(direction == 0)
			dif = seeker - working.get(0).getIndex();
		else
			dif = working.get(0).getIndex() - seeker;
		
		if(direction == 0) {
			for(int i = 1; i < working.size(); i++) {
				newDif = seeker - working.get(i).getIndex();
				if(newDif >= 0 && dif < 0) {//In case we start with a value in the wrong direction
					dif = newDif;
					index = i;
				}
				if(newDif < dif && newDif >= 0) {
					dif = newDif;
					index = i;
				}
			}
		}
		else {
			for(int i = 1; i < working.size(); i++) {
				newDif = working.get(i).getIndex() - seeker;
//				System.out.println("dif: " + dif + "  New Dif: " + newDif + " vs seeker: " + seeker + "  The index value: " + working.get(i).getIndex() + " index: " + i + "  saved: " + index);
				if(newDif >= 0 && dif < 0) {//In case we start with a value in the wrong direction
					dif = newDif;
					index = i;
				}
				if(newDif < dif && newDif >= 0) {
					dif = newDif;
					index = i;
				}
			}
		}
		
//		System.out.println("Returning this: " + index);
		return index;
	}
	
	public void printValues() {
		System.out.println("AveDelay: " + aveDelay + "\nMaxDelay: " + maxDelay + "\naveScore: " + aveScore + "\nmaxScore: " + maxScore);
	}
	
}

