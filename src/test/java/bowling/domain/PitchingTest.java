package bowling.domain;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

public class PitchingTest {
    @ParameterizedTest
    @CsvSource(value = {
            "0:GUTTER",
            "1:ONE_PIN",
            "2:TWO_PINS",
            "3:THREE_PINS",
            "4:FOUR_PINS",
            "5:FIVE_PINS",
            "6:SIX_PINS",
            "7:SEVEN_PINS",
            "8:EIGHT_PINS",
            "9:NINE_PINS",
            "10:STRIKE"},
            delimiter = ':')
    public void getPitchingTest(int knockDownPins, Pitching pitching) {
        assertThat(Pitching.getPitching(knockDownPins)).isEqualTo(pitching);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 11})
    @DisplayName("유효한 knockDownPins가 아닌 경우 throw Exception")
    public void getPitchingTest_failureCase(int invalidKnockDownPins) {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Pitching.getPitching(invalidKnockDownPins))
                .withMessage(Pitching.INVALID_KNOCK_DOWN_PINS_ARGUMENT);
    }

    @Test
    public void getSpareTest() {
        Pitching previousPitching = Pitching.ONE_PIN;
        int knockDownPins = 9;

        assertThat(Pitching.getPitching(knockDownPins, previousPitching)).isEqualTo(Pitching.SPARE);
    }

    @Test
    @DisplayName("스트라이크가 아닌 이전 투구와 현재 투구의 쓰러트린 핀의 갯수가 10을 초과하면 throw Exception")
    public void invalidTotalPins() {
        Pitching previousPitching = Pitching.NINE_PINS;
        int knockDownPins = 2;

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> Pitching.getPitching(knockDownPins, previousPitching));
    }
}