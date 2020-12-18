package soa.eip;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.Suspendable;


public class NProcess implements Processor {
    int max = 1; //Default value

    public NProcess(){}

    @Override
    public void process(Exchange exchange) throws Exception {
        String body = exchange.getIn().getBody(String.class);

        //regex for number of maximum tweets
        String regex = "^.+ max:\\d+$";
        if (body.matches(regex)) {
            String split[] = body.split("max:");
            max = Integer.parseInt(split[1]);

            //Deletes "max:n" from the query
            body = body.replace(" max:" + max,"").trim();
            body += "?count=" + max;
            Message message = exchange.getIn();
            message.setBody(body);
            message.setHeader("count", max);
        }
    }
}
