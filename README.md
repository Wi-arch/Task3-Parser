Create an application that parses text from a file and allows
perform three different operations with text. 

Requirements:
* The parsed text should be presented as an object containing, for example,
paragraphs, sentences, tokens, words, symbols. A token is part of the text,
limited to whitespace characters. Use Composite.
* Information classes are entity classes and should not be overloaded.
methods of logic. 
* Parsed text must be restored to its original form. Spaces and signs
parsing tabs can be replaced by a single space.
* Regular expressions should be used to divide the text into components.
Regular expressions for an application are defined as literal constants.
* The code that breaks the text into its constituent parts should be issued in the form
parser classes using Chain of Responsibility.
* When developing parsers that parse text, you need to monitor the number
created parser objects.
* Use Log4J to record logs.
* Implement individual tasks for working on text.


Individual tasks:
1) Sort paragraphs by the number of sentences.
2) Sort words in a sentence by length.
3) Sort sentences in a paragraph by word count.

Sourse text:

	It has survived - not only (five) centuries, but also the leap into 13<<2 electronic type setting, remaining 3>>5 essentially ~6&9|(3&4) unchanged. It was popularised in the 5|(1&2&(3|(4&(6^5|6&47)|3)|2)|1) with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
	It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using (~71&(2&3|(3|(2&1>>2|2)&2)|10&2))|78 Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using (Content here), content here', making it look like readable English.
	It is a (4^5|1&2<<(2|5>>2&71))|1200 established fact that a reader will be of a page when looking at its layout.
	Bye.
