package be.twofold.tinyparse.lexer;

import org.junit.*;

import java.util.*;
import java.util.stream.*;

import static org.assertj.core.api.Assertions.*;

public class LexerTest {

    private final TokenType word = TokenType.tokenize("word", "\\w+");
    private final TokenType whitespace = TokenType.ignore("whitespace", "\\s+", true);
    private final Lexer lexer = Lexer.builder().add(word).add(whitespace).build();

    @Test
    public void throwsWhenNoTokenTypes() {
        assertThatExceptionOfType(NullPointerException.class)
            .isThrownBy(() -> new Lexer(null));

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Lexer(Collections.emptyList()));
    }

    @Test
    public void canLexSimpleSentence() {
        List<Token> tokens = Stream.of("this", "is", "a", "sentence")
            .map(s -> new Token(word, s))
            .collect(Collectors.toList());

        assertThat(lexer.tokenize("this is a sentence"))
            .isEqualTo(new Tokens(tokens));
    }

}
