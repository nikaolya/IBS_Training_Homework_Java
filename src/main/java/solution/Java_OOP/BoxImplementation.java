package solution.Java_OOP;

import java.util.*;
import java.util.stream.Collectors;

public class BoxImplementation implements IBox<Sweet>{
	private List<Sweet> sweets = new ArrayList<>();


	@Override
	public void addSweet(Sweet sweet) {
		sweets.add(sweet);
		System.out.println("Added " + sweet.getName());
	}

	@Override
	public void removeSweet(SweetName name) {
		ListIterator<Sweet> iterator = sweets.listIterator();
		while(iterator.hasNext()){
			if (iterator.next().getName().equals(name)){
				iterator.remove();
				System.out.println("Removed " + name);
				break;
			}
		}
	}

	@Override
	public double getTotalWeight() {
		double totalWeight = sweets.stream().mapToDouble(Sweet::getWeight).sum();
		System.out.println(String.format("Total weight: %s", totalWeight));
		return totalWeight;
	}

	@Override
	public int getTotalPrice() {
		int totalPrice = sweets.stream().mapToInt(Sweet::getPrice).sum();
		System.out.println(String.format("Total price: %s", totalPrice));
		return totalPrice;
	}

	@Override
	public void printBoxContent() {
		Map<SweetName, List<Sweet>> map = sweets.stream().collect(Collectors.groupingBy(Sweet::getName));
		for (Map.Entry<SweetName, List<Sweet>> item : map.entrySet()){
			System.out.println(item.getKey() + " : " + item.getValue().size());
		}
	}

	@Override
	public void optimize(double maxWeight, OptimizeBy option) {
		List<Sweet> sorted = null;
		if (option.equals(OptimizeBy.WEIGHT)){
			sorted = sweets.stream().sorted(Comparator.comparing(Sweet::getWeight)).collect(Collectors.toList());
		} else if (option.equals(OptimizeBy.PRICE)){
			sorted = sweets.stream().sorted(Comparator.comparing(Sweet::getPrice)).collect(Collectors.toList());
		}

		ListIterator<Sweet> iterator = sorted.listIterator();
		while (iterator.hasNext() && getTotalWeight() > maxWeight){
			iterator.next();
			removeSweet(sorted.get(0).getName());
			iterator.remove();
		}
	}
}
