package solution.Java_Java8;

import com.fasterxml.jackson.databind.ObjectMapper;
import solution.Java_Java8.Pojo.Companies;
import solution.Java_Java8.Pojo.Company;
import solution.Java_Java8.Pojo.Security;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);


	public static void main(String[] args) {
		String fileName = "src/main/resources/companies.json";
		Companies obj = parseJson(fileName);
		List<Company> companies;

		if (obj != null){
			companies = obj.getCompanies();

			task1(companies);

			task2(companies);

			task3(companies);

			task4(companies);
		}
	}

	private static void task4(List<Company> companies) {
		System.out.println("\nUsing currency (company id: code): ");
		System.out.println("Enter the currency (rub, eu, usd): ");
		String currency = scanner.nextLine();
		//String currency = "rub";

		if (currency.equalsIgnoreCase("rub")
				|| currency.equalsIgnoreCase("eu")
				|| currency.equalsIgnoreCase("usd")){

			// получаем список ценных бумаг, использующих искомую валюту
			List<Security> securitiesUsingCurrency = companies.stream().map(Company::getSecurities).flatMap(Collection::stream)
					.filter(i -> i.getCurrency().contains(currency.toUpperCase()))
					.distinct()
					.collect(Collectors.toList());

			// первым делом список компаний фильтруется в соответствии с ранее полученным списком securitiesUsingCurrency
			// чтобы в результате были только компании, которые действительно владеют ценными бумагами, использующими искомую валюту
			companies.stream().filter(i -> i.getSecurities().stream().anyMatch(securitiesUsingCurrency::contains))
					.peek(i -> System.out.printf("Company id: %s (%s)%n", i.getId(), i.getName()))
					.map(Company::getSecurities).flatMap(Collection::stream)
					// и выводим информацию только о подходящих ценных бумагах
					.filter(securitiesUsingCurrency::contains)
					.forEach(i -> System.out.printf("%s (%s)%n", i.getCode(), i.getName()));


			// сейчас получается, что поток на фильтрацию по ценным бумагам запускается дважды
			// теоретически, обработать файл можно и за одну итерацию, но в этом случае всегда будут выводиться все компании
			// например, для измененного файла "companies2.json" при поиске "rub" мы получим лишнюю строку "Company id: 2 (Yandex)"
			// хотя для компании с id = 2 удалены все ценные бумаги использующие рубли
//			companies.stream()
//					.peek(i -> System.out.printf("Company id: %s (%s)%n", i.getId(), i.getName()))
//					.map(Company::getSecurities).flatMap(Collection::stream)
//					.filter(i -> i.getCurrency().contains(currency.toUpperCase()))
//					.forEach(i -> System.out.printf("%s (%s)%n", i.getCode(), i.getName()));

			// других идей, как избежать двойного обхода, пока нет =(

		} else {
			System.out.println("Wrong input.");
		}
	}

	private static void task3(List<Company> companies) {
		System.out.println("\nCompanies founded after date: ");
		System.out.println("Enter the date (dd.mm.yy, dd.mm.yyyy, dd/mm/yy, dd/mm/yyyy): ");
		String inputDate = scanner.nextLine();
		//String inputDate = "26.09.97";
		LocalDate date = convertToLocalDate(inputDate);
		if (date != null){
			System.out.printf("You entered: %s%n", date);

			companies.stream().filter(i -> convertToLocalDate(i.getFounded()).isAfter(date))
					.forEach(i -> System.out.printf("%s - %s%n", i.getName(), i.getFounded()));
		} else {
			System.out.println("Wrong input.");
		}
	}

	private static void task2(List<Company> companies) {
		System.out.println("\nExpired securities (code - name - expires):");
		List<Security> expired = companies.stream().map(Company::getSecurities).flatMap(Collection::stream)
				.filter(i -> convertToLocalDate(i.getDate()).isBefore(LocalDate.now()))
				.collect(Collectors.toList());
		expired.forEach(i -> System.out.printf("%s - %s - %s%n", i.getCode(), i.getName(), i.getDate()));

		System.out.printf("Total number of expired securities: %s%n", expired.size());
	}

	private static void task1(List<Company> companies) {
		System.out.println("Companies (name - founded):");
		companies.forEach(i -> System.out.printf("%s - %s%n", i.getName(), formatDate(i.getFounded())));
	}


	private static String formatDate(String inputDate){
		LocalDate date = convertToLocalDate(inputDate);
		if (date != null){
			return date.format(DateTimeFormatter.ofPattern("dd/MM/yy"));
		} else{
			return null;
		}
	}

	public static LocalDate convertToLocalDate(String inputDate){
		Map<String, String> patterns = new HashMap<>();
		patterns.put("\\d{1,2}/\\d{1,2}/\\d{2,4}", "dd/M/");
		patterns.put("\\d{1,2}\\.\\d{1,2}\\.\\d{2,4}", "dd.M.");

		DateTimeFormatter formatter = null;
		LocalDate date = null;
		for(Map.Entry<String, String> pattern : patterns.entrySet()){
			if (inputDate.matches(pattern.getKey())){
				formatter = new DateTimeFormatterBuilder()
						.appendPattern(pattern.getValue())
						.appendValueReduced(ChronoField.YEAR_OF_ERA, 2, 4, LocalDate.now().minusYears(80))
						.toFormatter();
				break;
			}
		}
		if (formatter != null){
			try {
				date = LocalDate.parse(inputDate, formatter);
			}
			catch (DateTimeParseException e) {
				e.printStackTrace();
			}
		}
		return date;
	}

	public static Companies parseJson(String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		Companies companies = null;
		try {
			companies = mapper.readValue(new File(fileName), Companies.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return companies;
	}
}
