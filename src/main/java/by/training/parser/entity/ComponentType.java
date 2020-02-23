package by.training.parser.entity;

public enum ComponentType {

	TEXT(".+\\r?\\n+\\t*"), PARAGRAPH("[^\\.?!;]+[\\.?!;]+[\r\n\t\\s]+"), SENTENCE("[\\S^\\t]+\\s*"),
	LEXEME("\t?(?<word>\\p{L}+)(?<symbol>[^\\p{L}]*)"), WORD("(\t|\r|\n|.)"), SYMBOL("");

	private String regex;

	private ComponentType(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return regex;
	}
}
