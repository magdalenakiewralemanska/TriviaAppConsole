package pl.sda.lib.query;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RequestQueryTest {

    @Test
    public void shouldReturnQueryWithOneParameterForOneParameter(){
        String result = RequestQuery
                .builder()
                .addParameter("name", "Karol")
                .query();
        assertEquals("name=Karol", result);
        result = RequestQuery
                .builder()
                .addParameter("amount", 10)
                .query();
        assertEquals("amount=10", result);
    }

    @Test
    public void shouldReturnQueryWithTwoParametersForTwoParameters(){
        String result = RequestQuery
                .builder()
                .addParameter("name", "Karol")
                .addParameter("amount", 10)
                .query();
        Set<String> set = Set.of(result.split("&"));
        assertTrue(set.contains("name=Karol"));
        assertTrue(set.contains("amount=10"));
        assertEquals(2, set.size());
        assertEquals("name=Karol&amount=10".length() ,result.length());
    }


}