import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		ArrayList<SeqReq> master = new ArrayList<SeqReq>();
		int entries;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("How many entries would you like to work with?");
		entries = scan.nextInt();
		
		generateList(master, entries);
		
//		for(int i = 0; i < master.size(); i++) {
//			System.out.println(master.get(i).getIndex());
//		}
		
		FCFS fcfs = new FCFS(master, entries);
		fcfs.work();
		fcfs.printValues();
		
		System.out.println("SSF:");
		SSF ssf = new SSF(master, entries);
		ssf.work();
		ssf.printValues();
		
		System.out.println("Elevator");
		Elevator elevator = new Elevator(master, entries);
		elevator.work();
		elevator.printValues();
		
		System.out.println("Custom");
		Custom custom = new Custom(master, entries);
		custom.work();
		custom.printValues();
	}
	
	public static void generateList(ArrayList<SeqReq> master, int entries) {
		Random rand = new Random();
		for(int i = 0; i < entries; i++) {
			SeqReq myReq = new SeqReq(rand.nextInt(100) + 1);
			master.add(myReq);
		}
	}

}
