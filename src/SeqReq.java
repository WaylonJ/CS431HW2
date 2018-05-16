
public class SeqReq {
	int startTime;
	int endTime;
	double score;
	int pos, delay;
	
	public SeqReq(int pos) {
		this.pos = pos;
	}
	
	//Determines score and sets end time
	public void finishReq(int time) {
		this.endTime = time;
		
		this.delay = this.endTime - this.startTime;
		this.score = (this.delay * Math.sqrt((double)this.delay));	
	}
	
	public int getDelay() {
		return this.delay;
	}
	public double getScore() {
		return this.score;
	}
	public int getIndex() {
		return this.pos;
	}
	public void start(int time) {
		this.startTime = time;
	}
	
	
}
