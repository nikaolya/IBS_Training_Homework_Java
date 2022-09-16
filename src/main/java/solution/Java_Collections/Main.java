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

		String fileName = "src/main/resources/file.txt";

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

		System.out.println("\nWords frequency in descending order:");
		Map<String, Long> wordsFreq = wordsList.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		wordsFreq.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.forEach(i -> System.out.println(i.getKey() + " - " + i.getValue()*100/N +  "%"));

		System.out.println("\nThe most frequent word(s):");
		Long maxFreq = wordsFreq.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
		for(Map.Entry<String, Long> item : wordsFreq.entrySet()){
			if (item.getValue() == maxFreq){
				System.out.println(item.getKey() + " - " + item.getValue()*100/N +  "%");
			}
		}
	}
}
