import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xrtb.bidder.RTBServer;
import com.xrtb.exchanges.adx.AdxBidRequest;

public class Junk {

	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		br = new BufferedReader(new FileReader("../../zz"));
		String data = null;
		while((data=br.readLine()) != null) {
			System.out.println("<li>" + data + "</li>");
		}
	}
}
