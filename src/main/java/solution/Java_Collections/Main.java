package solution.Java_Collections;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<String> wordsList = new ArrayList<>();

		String fileName = "src/main/resources/emptyFile.txt";

		Pattern pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS);

		try (Scanner scanner = new Scanner(new File(fileName)).useDelimiter(pattern)) {
			while (scanner.hasNext()) {
				wordsList.add(scanner.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		double N = wordsList.size();
		System.out.println("Words sorted alphabetically:");
		Collections.sort(wordsList);
		wordsList.forEach(System.out::println);

		System.out.println("\nWords sorted by the number of occurrences in the text:");
		Map<String, Long> wordsFreq = wordsList.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		wordsFreq.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(System.out::println);

		System.out.println("\nThe most frequent word(s):");
		Long maxFreq = 0L;
		Optional<Map.Entry<String, Long>> optional = wordsFreq.entrySet().stream().max(Map.Entry.comparingByValue());
		if (optional.isPresent()){
			maxFreq = optional.get().getValue();
		}
		for(Map.Entry<String, Long> item : wordsFreq.entrySet()){
			if (item.getValue() == maxFreq){
				System.out.println(item.getKey() + " - " + item.getValue()*100/N +  "%");
			}
		}
	}
}
