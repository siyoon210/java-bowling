package bowling;

import bowling.domain.state.*;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class StateTest {

    @Test
    void playFromReady() {
        assertThat(new Ready().play(10)).isInstanceOf(Strike.class);
        assertThat(new Ready().play(5)).isInstanceOf(Hit.class);
        assertThat(new Ready().play(0)).isInstanceOf(Gutter.class);
    }

    @Test
    void playFromHit() {
        assertThat(new Hit(9).play(1)).isInstanceOf(Spare.class);
        assertThat(new Hit(9).play(0)).isInstanceOf(Miss.class);
    }

    @Test
    void playFromFirstGutter() {
        assertThat(new Gutter().play(0)).isInstanceOf(Miss.class);
        assertThat(new Gutter().play(10)).isInstanceOf(Spare.class);
        assertThat(new Gutter().play(1)).isInstanceOf(Miss.class);
        assertThat(new Gutter().play(9)).isInstanceOf(Miss.class);
    }

    @Test
    void playFromEndedState() {
        assertThatIllegalStateException().isThrownBy(() -> new Miss(3, 4).play(0));
        assertThatIllegalStateException().isThrownBy(() -> new Strike().play(0));
        assertThatIllegalStateException().isThrownBy(() -> new Spare(4).play(0));
    }

    @Test
    void hitCountWhenIsInvalid() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Hit(0));
    }

    @Test
    void hitCountWhenIsOverSumOfCount() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Hit(5).play(6));
    }
}