package solution.Java_Java8.Pojo;

import java.util.List;
import java.util.Objects;

public class Security {
	private String name;
	private List<String> currency;
	private String code;
	private String date;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCurrency() {
		return currency;
	}

	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Security security = (Security) o;
		return Objects.equals(name, security.name) && Objects.equals(currency, security.currency) && Objects.equals(code, security.code) && Objects.equals(date, security.date);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, currency, code, date);
	}
}
