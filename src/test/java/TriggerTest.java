import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class TriggerTest {

    @Test
    public void whenTrigger() {
        Trigger trigger = new Trigger();
        String exp = "Hello, World!";
        assertThat(exp, is(trigger.hello()));
    }

}