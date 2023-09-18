package pl.sda.lib.query;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestQuery {

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder {
        private final Map<String, Object> parameters = new HashMap<>();

        //TODO: dodaj kod testujący czy parametrName jest null, czy parametrValue jest null
        //TODO: nie dodawaj parametru którego Name lub Value jest null
        //TODO: nie dodawaj parametru o nazwie pustej ""
        //TODO: dodaj metody testowe dla powyższych przypadków

        public Builder addParameter(String parameterName, Object parameterValue) {
            parameters.putIfAbsent(parameterName, parameterValue);
            return this;
        }

        //jeśli wykonamy
        //RequestQuery rq = new RequestQuery();
        //rq.addParameter("name", "Karol");
        //rq.addParameter("amount", 10);
        //to wywołanie
        //String r = rq.query();
        //w r powinien być poniższy łańcuch
        //name=Karol&amount=10
        public String queryForEach() {
            StringBuilder sb = new StringBuilder();
            for (var entry : parameters.entrySet()) {
                String pName = entry.getKey();
                Object pValue = entry.getValue();
                sb.append(pName).append('=').append(pValue).append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }

        public String query() {
            return parameters.entrySet()
                    .stream()
                    .map(entry -> String.format("%s=%s", entry.getKey(), entry.getValue().toString()))
                    .collect(Collectors.joining("&"));
        }
    }
}
