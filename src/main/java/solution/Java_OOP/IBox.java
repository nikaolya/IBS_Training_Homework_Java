package solution.Java_OOP;

public interface IBox<Sweet>{
	public void addSweet(Sweet sweetName);
	public void removeSweet(SweetName name);
	public double getTotalWeight();
	public int getTotalPrice();
	public void printBoxContent();
	public void optimize(double maxWeight, OptimizeBy option);
}
